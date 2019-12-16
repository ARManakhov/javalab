package ru.sirosh;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sirosh.Models.*;
import ru.sirosh.Models.request.*;
import ru.sirosh.Repositories.*;

import javax.crypto.spec.SecretKeySpec;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class ClientSocket extends Thread {
    private final String SECRET_KEY = "secret";
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
        if (user != null && request.header.equals("role")) {
            roleReqHandler(line);
        }
    }

    private void getAddresses(String line) throws IOException {
        ReqWithString req = mapper.readValue(line, ReqWithString.class);
        User user = decodeJwt(req.payload);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
        String resp = mapper.writeValueAsString(new ReqWithAddresses("getAddresses", arji.findByUserId(user.getId())));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void newAddress(String line) throws IOException {
        ReqWithAddressToken reqFull = mapper.readValue(line, ReqWithAddressToken.class);
        User user = decodeJwt(reqFull.token);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
        Address address = reqFull.address;
        address.userId = user.getId();
        arji.save(address);
        String resp = mapper.writeValueAsString(new ReqWithString("newAddress", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void delAddress(String line) throws IOException {
        ReqWithAddressToken reqFull = mapper.readValue(line, ReqWithAddressToken.class);
        User user = decodeJwt(reqFull.token);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
        Address address = reqFull.address;
        address.userId = user.getId();
        arji.delete(reqFull.address);
        String resp = mapper.writeValueAsString(new ReqWithString("delAddress", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }


    private void deleteProductReqHandler(String line) throws IOException {
        ReqWithProductToken reqFull = mapper.readValue(line, ReqWithProductToken.class);
        User user = decodeJwt(reqFull.token);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(connection);
        User userFromDb = urji.findOneById(user.getId()).get();
        String resp;
        if (userFromDb.getRole().equals("admin")) {
            ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
            reqFull.product.isDeleted = true;
            prji.delete(reqFull.product);
            resp = mapper.writeValueAsString(new ReqWithString("deleteProduct", "success"));
        } else {
            resp = mapper.writeValueAsString(new ReqWithString("deleteProduct", "fail"));
        }
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void makeProductReqHandler(String line) throws IOException {
        ReqWithProductToken reqFull = mapper.readValue(line, ReqWithProductToken.class);
        User user = decodeJwt(reqFull.token);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(connection);
        User userFromDb = urji.findOneById(user.getId()).get();
        String resp;
        if (userFromDb.getRole().equals("admin")) {
            ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
            prji.save(reqFull.product);
            resp = mapper.writeValueAsString(new ReqWithString("makeProduct", "success"));
        } else {
            resp = mapper.writeValueAsString(new ReqWithString("makeProduct", "fail"));
        }
        writer.println(resp);
        System.out.println("returning : " + resp);

    }

    private void getOrderReqHandler(String line) throws IOException {
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(connection);
        ReqWithOrderToken reqFull = mapper.readValue(line, ReqWithOrderToken.class);
        Order order = orji.getOrderById(reqFull.order.id);
        User user = decodeJwt(reqFull.token);
        String resp = null;
        if (user.getId().equals(order.userId)) {
            resp = mapper.writeValueAsString(new ReqWithOrder("getOrder", order));
        } else {
            resp = mapper.writeValueAsString(new ReqWithString("getOrder", "fail"));
        }
        writer.println(resp);
        System.out.println("returning : " + resp);

    }


    private void getOrdersReqHandler(String line) throws IOException {
        ReqWithString req = mapper.readValue(line, ReqWithString.class);
        User user = decodeJwt(req.payload);
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(connection);
        List<Order> orders = orji.getOrderByUserId(user.getId());
        String resp = mapper.writeValueAsString(new ReqWithOrders("getOrders", orders));
        writer.println(resp);
        System.out.println("returning : " + resp);

    }

    private void makeOrderReqHandler(String line) throws IOException {
        ReqWithAddressToken reqFull = mapper.readValue(line, ReqWithAddressToken.class);
        User user = decodeJwt(reqFull.token);
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
        ReqWithProductToken reqFull = mapper.readValue(line, ReqWithProductToken.class);
        User user = decodeJwt(reqFull.token);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(connection);
        crji.delProduct(crji.getCartByUserId(user.getId()), reqFull.product);
        String resp = mapper.writeValueAsString(new ReqWithString("delFromCart", "success"));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void cartViewReqHandler(String line) throws IOException {
        ReqWithString reqFull = mapper.readValue(line, ReqWithString.class);
        User user = decodeJwt(reqFull.payload);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(connection);
        User userFromDb = urji.findOneById(user.getId()).get();
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(connection);
        Cart cart = crji.getCartByUserId(user.getId());
        List<Product> list = cart.productList;
        String resp;
        if (user.getId().equals(cart.userId)) {
            resp = mapper.writeValueAsString(new ReqWithProducts("cartView", list, (long) list.size()));
        } else {
            resp = mapper.writeValueAsString(new ReqWithString("getOrder", "fail"));
        }
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void addToCartReqHandler(String line) throws IOException {
        ReqWithProductToken reqFull = mapper.readValue(line, ReqWithProductToken.class);
        User user = decodeJwt(reqFull.token);
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
        ReqWithStringToken fullReq = mapper.readValue(line, ReqWithStringToken.class);
        User user = decodeJwt(fullReq.token);
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
                resp = mapper.writeValueAsString(new ReqWithString("login", createJWT(user)));
            } else {
                resp = mapper.writeValueAsString(new ReqWithString("login", null));
            }
        } catch (NoSuchElementException e) {
            resp = mapper.writeValueAsString(new ReqWithString("login", null));
        }
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    private void roleReqHandler(String line) throws IOException {
        ReqWithString fullReq = mapper.readValue(line, ReqWithString.class);
        User user = decodeJwt(fullReq.payload);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(connection);
        User userFromDb = urji.findOneById(user.getId()).get();
        String resp = mapper.writeValueAsString(new ReqWithString("role", userFromDb.getRole()));
        writer.println(resp);
        System.out.println("returning : " + resp);
    }

    public String createJWT(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String token = JWT.create()
                    .withClaim("id", user.getId())
                    .withClaim("username", user.getUsername())
                    .withClaim("role", user.getRole())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public User decodeJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            User user = new User(claims.get("username").asString(), claims.get("id").asLong(), claims.get("role").asString());
            return user;
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}

