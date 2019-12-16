package ru.sirosh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sirosh.Models.*;
import ru.sirosh.Models.request.*;
import ru.sirosh.Repositories.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class ClientSocket extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private List<ClientSocket> clients;
    private User user = null;
    private Connection connection;
    private PrintWriter writer = null;
    private ObjectMapper mapper = new ObjectMapper();
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    private boolean logMode = false;

    public ClientSocket(Socket socket, List<ClientSocket> clients, Connection connection) {
        this.connection = connection;
        this.socket = socket;
        this.clients = clients;
        clients.add(this);
    }

    @Override
    public void run() {
        System.out.println("in run");
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 1);
            String line;
            writer = new PrintWriter(this.socket.getOutputStream(), true);
            while ((line = reader.readLine()) != null) {
                requestHandler(line);
            }
            writer.close();
            writer = null;
            reader.close();
            socket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestHandler(String line) throws IOException {

        System.out.println("new request : " + line);
        DummyRequest request = mapper.readValue(line, DummyRequest.class);

        if (user == null && request.header.equals("login")) {
            loginReqHandler(line);
        }
        if (user == null && request.header.equals("register")) {
            regReqHandler(line);
        }
        if (user != null && request.header.equals("send")) {
            sendReqHandler(line);
        }
        if (user != null && request.header.equals("msgs")) {
            msgsReqHandler(line);
        }
        if (user != null && request.header.equals("products")) {
            productsReqHandler(line);
        }
        if (user != null && request.header.equals("addToCart")) {
            addToCartReqHandler(line);
        }
        if (user != null && request.header.equals("delFromCart")) {
            delFromCartReqHandler(line);
        }
        if (user != null && request.header.equals("cartView")) {
            cartViewReqHandler(line);
        }
        if (user != null && request.header.equals("makeOrder")) {
            makeOrderReqHandler(line);
        }
        if (user != null && request.header.equals("getOrders")) {
            getOrdersReqHandler(line);
        }
        if (user != null && request.header.equals("getOrder")) {
            getOrderReqHandler(line);
        }
        if (user != null && request.header.equals("makeProduct")) {
            makeProductReqHandler(line);
        }
        if (user != null && request.header.equals("deleteProduct")) {
            deleteProductReqHandler(line);
        }
        if (user != null && request.header.equals("getAddresses")) {
            getAddresses(line);
        }
        if (user != null && request.header.equals("newAddress")) {
            newAddress(line);
        }
        if (user != null && request.header.equals("delAddress")) {
            delAddress(line);
        }
    }

    private void getAddresses(String line) throws IOException {
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
        String resp = mapper.writeValueAsString(new ReqWithAddresses("getAddresses", arji.findByUserId(user.getId())));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void newAddress(String line) throws IOException {
        ReqWithAddress reqFull = mapper.readValue(line,ReqWithAddress.class);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
        Address address = reqFull.address;
        address.userId = user.getId();
        arji.save(address);
        String resp = mapper.writeValueAsString(new ReqWithString("newAddress","success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void delAddress(String line) throws IOException {
        ReqWithAddress reqFull = mapper.readValue(line,ReqWithAddress.class);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
        Address address = reqFull.address;
        address.userId = user.getId();
        arji.delete(reqFull.address);
        String resp = mapper.writeValueAsString(new ReqWithString("delAddress","success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }


    private void deleteProductReqHandler(String line) throws IOException {
        ReqWithProduct reqFull = mapper.readValue(line, ReqWithProduct.class);
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
        reqFull.product.isDeleted = true;
        prji.delete(reqFull.product);
        String resp = mapper.writeValueAsString(new ReqWithString("deleteProduct", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);

    }

    private void makeProductReqHandler(String line) throws IOException {
        ReqWithProduct reqFull = mapper.readValue(line, ReqWithProduct.class);
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
        prji.save(reqFull.product);
        String resp = mapper.writeValueAsString(new ReqWithString("makeProduct", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);

    }

    private void getOrderReqHandler(String line) throws IOException {
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(connection);
        ReqWithOrder reqFull = mapper.readValue(line, ReqWithOrder.class);
        Order order = orji.getOrderById(reqFull.order.id);
        String resp = mapper.writeValueAsString(new ReqWithOrder("getOrder", order));
        writer.println(resp);
        System.out.println("returning : " + resp);

    }


    private void getOrdersReqHandler(String line) throws IOException {
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(connection);
        List<Order> orders = orji.getOrderByUserId(user.getId());
        String resp = mapper.writeValueAsString(new ReqWithOrders("getOrders", orders));
        writer.println(resp);
        System.out.println("returning : " + resp);

    }

    private void makeOrderReqHandler(String line) throws IOException {
        ReqWithAddress reqFull = mapper.readValue(line, ReqWithAddress.class);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(connection);
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(connection);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
        Cart cart = crji.getCartByUserId(user.getId());
        Address address = arji.find((int) reqFull.address.id).get();
        Order order = new Order(user.getId(), address.id, cart.productList, address);
        orji.save(order);
        crji.delAllProducts(cart);
        String resp = mapper.writeValueAsString(new ReqWithString("makeOrder", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void delFromCartReqHandler(String line) throws IOException {
        ReqWithProduct reqFull = mapper.readValue(line, ReqWithProduct.class);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(connection);
        crji.delProduct(crji.getCartByUserId(user.getId()), reqFull.product);
        String resp = mapper.writeValueAsString(new ReqWithString("delFromCart", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void cartViewReqHandler(String line) throws IOException {
        ReqWithListGetCom reqFull = mapper.readValue(line, ReqWithListGetCom.class);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(connection);
        List<Product> list = crji.getCartByUserId(user.getId()).productList;
        String resp = mapper.writeValueAsString(new ReqWithProducts("cartView", list, (long) list.size()));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void addToCartReqHandler(String line) throws IOException {
        ReqWithProduct reqFull = mapper.readValue(line, ReqWithProduct.class);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(connection);
        crji.addProduct(crji.getCartByUserId(user.getId()), reqFull.product);
        String resp = mapper.writeValueAsString(new ReqWithString("addToCart", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void productsReqHandler(String line) throws IOException {
        ReqWithListGetCom reqFull = mapper.readValue(line, ReqWithListGetCom.class);
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
        List<Product> products = prji.getList(reqFull.payload.number, reqFull.payload.number);
        String resp = mapper.writeValueAsString(new ReqWithProducts("products", products, prji.getCount()));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void msgsReqHandler(String line) throws IOException {
        ReqWithListGetCom reqFull = mapper.readValue(line, ReqWithListGetCom.class);
        MessageRepositoryJdbcImpl mrji = new MessageRepositoryJdbcImpl(connection);
        List<Message> messages = mrji.findLastMessageWithOffset(reqFull.payload.number, reqFull.payload.offset);
        Set<User> users = new HashSet<>();
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(connection);
        for (Message message : messages) {
            User msgUser = urji.findOneById(message.senderId).get();
            msgUser.setPassword(null);
            users.add(msgUser);
        }

        String resp = mapper.writeValueAsString(new ReqWitmMsgs("msgs", messages, new ArrayList<>(users), mrji.getCount()));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void sendReqHandler(String line) throws IOException {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        ReqWithString fullReq = mapper.readValue(line, ReqWithString.class);
        System.out.println("sending:" + fullReq.payload);
        new MessageRepositoryJdbcImpl(connection).save(new Message(user.getId(), (String) fullReq.payload, currentTime));
        for (ClientSocket client : clients) {
            if (client.user != null) {
                PrintWriter clientWriter = client.writer;
                if (clientWriter != null) {
                    clientWriter.println(mapper.writeValueAsString(new ReqWithString("newMsg", this.user.getUsername() + " : " + fullReq.payload)));
                }
            }
        }

        String resp = mapper.writeValueAsString(new ReqWithString("send", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void regReqHandler(String line) throws IOException {
        ReqWithUser reqWithUser = mapper.readValue(line, ReqWithUser.class);
        User userFromJson = reqWithUser.payload;
        userFromJson.setPassword(encoder.encode(userFromJson.getPassword()));
        String resp = null;
        try {
            new UsersRepositoryJdbcImpl(connection).findOneByUsername(userFromJson.getUsername()).get();
            resp = mapper.writeValueAsString(new ReqWithUser("register", null));
        } catch (NoSuchElementException e) {
            new UsersRepositoryJdbcImpl(connection).save(userFromJson);
            user = userFromJson;
            user.setPassword(null);
            resp = mapper.writeValueAsString(new ReqWithUser("login", user));
        }
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void loginReqHandler(String line) throws IOException {
        ReqWithUser reqWithUser = mapper.readValue(line, ReqWithUser.class);
        User userFromJson = reqWithUser.payload;
        String resp = null;
        try {
            User userFromDb = new UsersRepositoryJdbcImpl(connection).findOneByUsername(userFromJson.getUsername()).get();
            if (encoder.matches(userFromJson.getPassword(), userFromDb.getPassword())) {
                user = userFromDb;
                user.setPassword(null);
                resp = mapper.writeValueAsString(new ReqWithUser("login", user));
            } else {
                resp = mapper.writeValueAsString(new ReqWithUser("login", null));
            }
        } catch (NoSuchElementException e) {
            resp = mapper.writeValueAsString(new ReqWithUser("login", null));
        }
        writer.println(resp);
        System.out.println("returning : " + resp);
    }


}

