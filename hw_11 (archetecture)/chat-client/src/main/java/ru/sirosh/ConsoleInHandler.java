package ru.sirosh;
/*
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Models.*;
import ru.sirosh.Models.request.*;
import ru.sirosh.view.StateHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleInHandler extends Thread {

    private StateHolder currentState;
    private PrintWriter writer;
    private BufferedReader reader;
    private ConsoleOutHandler t2;
    private boolean firstTime = true;
    String userToken = null;

    public ConsoleInHandler(PrintWriter writer, BufferedReader reader, StateHolder currentState) {
        this.writer = writer;
        this.reader = reader;
        this.currentState = currentState;
        t2 = new ConsoleOutHandler(writer, reader, currentState);
    }

    ObjectMapper mapper = new ObjectMapper();
    long offset = 0;


    public void run() {
        Scanner sc = new Scanner(System.in);
        try {

            while (true) {


                if (currentState.getState().equals(ru.sirosh.State.NOT_AUTH)) {
                    notAuthScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.NOT_AUTH_LOG)) {
                    loginScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.NOT_AUTH_REG)) {
                    registerScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.MENU)) {
                    menuScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.CHAT)) {
                    chatScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.PRODUCTS)) {
                    productsScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.PRODUCT_NEW)) {
                    addProduct(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.CART)) {
                    cartScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.ORDERS_LIST)) {
                    orderListScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.ADDRESSES_LIST)) {
                    addressesScreen(sc);
                } else if (currentState.getState().equals(ru.sirosh.State.CHAT_HISTORY)) {
                    chatHistoryScreen(sc);
                }


            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void orderListScreen(Scanner sc) throws IOException {
        if (firstTime) {
            firstTime = false;
            System.out.println("to return to menu type /m");
            System.out.println("to view order details type number ");

        }
        System.out.println("orders :");
        ReqWithListGetCom req = new ReqWithListGetCom("getOrders", new ListGetComPayload(offset, 5));
        writer.println(mapper.writeValueAsString(req));
        ReqWithOrders resp = mapper.readValue(reader.readLine(), ReqWithOrders.class);
        List<Order> orders = resp.orders;
        int i = 0;
        for (Order order : orders) {
            System.out.println((++i) + ") id: " + order.id + " | " + order.status);
        }
        String line = sc.nextLine();
        if (line.equals("/m")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.MENU);
        } else {
            int productNum = Integer.parseInt(line);
            Order viewOrder = orders.get(--productNum);
            System.out.println("order id : " + viewOrder.id);
            System.out.println("order status : " + viewOrder.status);
            System.out.println("order address : " + viewOrder.address.description);
            System.out.println("order products : ");
            int j = 0;
            for (Product product : viewOrder.productList) {
                if (product != null) {
                    System.out.println(++j + ")" + product.name + " \t| " + product.cost + " \t| " + product.description);
                }
            }
        }

    }

    private void addressesScreen(Scanner sc) throws IOException {
        if (firstTime) {
            firstTime = false;
            System.out.println("to return to menu type /m");
            System.out.println("to make new address type /n");
            System.out.println("to delete address  type /r");
            offset = 0;
        }
        ReqWithString req = new ReqWithString("getAddresses", userToken);
        writer.println(mapper.writeValueAsString(req));
        ReqWithAddresses resp = mapper.readValue(reader.readLine(), ReqWithAddresses.class);
        List<Address> addresses = resp.address;
        int i = 0;
        for (Address address : addresses) {
            System.out.println((++i) + ") " + address.description);
        }
        String line = sc.nextLine();
        if (line.startsWith("/r")) {
            int addNum = Integer.parseInt(line.split(" ")[1]);
            Address delAddress = addresses.get(--addNum);

            if (delAddress != null) {
                ReqWithAddressToken delReq = new ReqWithAddressToken("delAddress", delAddress, userToken);
                writer.println(mapper.writeValueAsString(delReq));
                ReqWithString delResp = mapper.readValue(reader.readLine(), ReqWithString.class);
                System.out.println(delResp.payload);
            }

        } else if (line.equals("/m")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.MENU);
        } else if (line.equals("/n")) {
            System.out.println("enter address");
            line = sc.nextLine();
            ReqWithAddressToken reqWithAddress = new ReqWithAddressToken("newAddress", new Address(line), userToken);
            writer.println(mapper.writeValueAsString(reqWithAddress));
            ReqWithString respSave = mapper.readValue(reader.readLine(), ReqWithString.class);
            System.out.println(respSave.payload);

        }
    }

    private void cartScreen(Scanner sc) throws IOException {
        if (firstTime) {
            firstTime = false;
            System.out.println("to return to menu type /m");
            System.out.println("to make order type /o");
            System.out.println("to delete product from cart type /r");
            offset = 0;
        }
        ReqWithString req = new ReqWithString("cartView", userToken);
        writer.println(mapper.writeValueAsString(req));
        ReqWithProducts resp = mapper.readValue(reader.readLine(), ReqWithProducts.class);
        int i = 0;
        List<Product> products = resp.products;
        for (Product product : products) {
            if (product != null) {
                System.out.println(++i + ")" + product.name + " \t| " + product.cost + " \t| " + product.description);

            }
        }
        String line = sc.nextLine();
        if (line.startsWith("/r")) {
            int productNum = Integer.parseInt(line.split(" ")[1]);
            Product delProduct = products.get(--productNum);

            if (delProduct != null) {
                ReqWithProductToken delReq = new ReqWithProductToken("delFromCart", delProduct, userToken);
                writer.println(mapper.writeValueAsString(delReq));
                ReqWithString delResp = mapper.readValue(reader.readLine(), ReqWithString.class);
                System.out.println(delResp.payload);
            }
        } else if (line.equals("/m")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.MENU);
        } else if (line.equals("/o")) {
            System.out.println("chose address");
            firstTime = true;
            ReqWithString reqAddresses = new ReqWithString("getAddresses", userToken);
            writer.println(mapper.writeValueAsString(reqAddresses));
            ReqWithAddresses respAddresses = mapper.readValue(reader.readLine(), ReqWithAddresses.class);
            List<Address> addresses = respAddresses.address;
            int j = 0;
            for (Address address : addresses) {
                System.out.println((++j) + ") " + address.description);
            }
            String addNumLine = sc.nextLine();
            int addNum = Integer.parseInt(addNumLine);
            ReqWithAddressToken reqWithAddress = new ReqWithAddressToken("makeOrder", addresses.get(--addNum), userToken);
            writer.println(mapper.writeValueAsString(reqWithAddress));
            ReqWithString delResp = mapper.readValue(reader.readLine(), ReqWithString.class);
            System.out.println(delResp.payload);
            currentState.setState(ru.sirosh.State.MENU);
            firstTime = true;
        }

    }

    private void addProduct(Scanner sc) throws IOException {
        System.out.println("type new product name:");
        String name = sc.nextLine();
        System.out.println("type new product description:");
        String description = sc.nextLine();
        Double cost = null;
        while (true) {
            try {
                System.out.println("type new product cost:");
                cost = Double.valueOf(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("wrong line, try again");
            }
        }

        ReqWithProductToken req = new ReqWithProductToken("makeProduct", new Product(name, description, cost), userToken);
        writer.println(mapper.writeValueAsString(req));
        ReqWithString resp = mapper.readValue(reader.readLine(), ReqWithString.class);
        System.out.println(resp.payload);
        currentState.setState(ru.sirosh.State.PRODUCTS);
    }

    private void chatScreen(Scanner sc) throws JsonProcessingException {
        if (firstTime) {
            firstTime = false;
            System.out.println("to return to menu type /m");
            if (!t2.isAlive()) {
                t2.start();
            }
        }
        String line = sc.nextLine();
        if (line.equals("/m")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.MENU);
        } else {
            writer.println(mapper.writeValueAsString(new ReqWithStringToken("send", line, userToken)));

        }

    }


    private void productsScreen(Scanner sc) throws IOException {
        ReqWithString reqRole = new ReqWithString("role", userToken);
        writer.println(mapper.writeValueAsString(reqRole));
        ReqWithString respRole = mapper.readValue(reader.readLine(), ReqWithString.class);

        if (firstTime) {
            firstTime = false;
            System.out.println("to return to menu type /m");
            System.out.println("to see next page type >, prev page <");
            System.out.println("type product number to add to cart");
            if (respRole.payload.equals("admin")) {
                System.out.println("to add post type /a");
                System.out.println("to remove post type /r");
            }
            offset = 0;
        }
        ReqWithListGetCom req = new ReqWithListGetCom("products", new ListGetComPayload(offset, 5));
        writer.println(mapper.writeValueAsString(req));
        ReqWithProducts resp = mapper.readValue(reader.readLine(), ReqWithProducts.class);
        System.out.println("page number " + ((offset / 5) + 1) + " of " + (1 + resp.count / 5));
        List<Product> products = resp.products;
        int i = 0;
        for (Product product : products) {

            System.out.println(++i + ")" + product.name + " \t| " + product.cost + " \t| " + product.description);
        }
        String line = sc.nextLine();
        if (line.equals("/a") && respRole.payload.equals("admin")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.PRODUCT_NEW);
        } else if (line.startsWith("/r") && respRole.payload.equals("admin")) {
            int productNum = Integer.parseInt(line.split(" ")[1]);
            Product delProduct = products.get(--productNum);

            if (delProduct != null) {
                ReqWithProductToken delReq = new ReqWithProductToken("deleteProduct", delProduct,userToken);
                writer.println(mapper.writeValueAsString(delReq));
                ReqWithString delResp = mapper.readValue(reader.readLine(), ReqWithString.class);
                System.out.println(delResp.payload);
            }
        } else if (line.equals("/m")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.MENU);
        } else if (line.equals(">")) {
            if (!(offset / 5 >= resp.count / 5))
                offset += 5;
        } else if (line.equals("<")) {
            if (offset != 0) {
                offset -= 5;
            }
        } else {
            int productNum = Integer.parseInt(line);
            Product cartProduct = products.get(--productNum);
            ReqWithProductToken reqCart = new ReqWithProductToken("addToCart", cartProduct, userToken);
            writer.println(mapper.writeValueAsString(reqCart));
            ReqWithString delResp = mapper.readValue(reader.readLine(), ReqWithString.class);
            System.out.println(delResp.payload);
        }
    }

    private void chatHistoryScreen(Scanner sc) throws IOException {
        if (firstTime) {
            firstTime = false;
            System.out.println("to return to menu type /m");
            System.out.println("to see next page type >, prev page <");
            offset = 0;
        }
        ReqWithListGetCom req = new ReqWithListGetCom("msgs", new ListGetComPayload(offset, 5));
        writer.println(mapper.writeValueAsString(req));
        ReqWitmMsgs resp = mapper.readValue(reader.readLine(), ReqWitmMsgs.class);
        System.out.println("page number " + ((offset / 5) + 1) + " of " + (1 + resp.count / 5));
        List<Message> msgs = resp.messages;
        List<User> users = resp.users;
        for (Message msg : msgs) {
            User msgUser = users.stream().filter(user -> user.getId().equals(msg.senderId)).findFirst().get();
            System.out.println("(" + msg.timestamp.toLocalDateTime() + ") " + msgUser.getUsername() + ":" + msg.text);
        }
        String line = sc.nextLine();
        if (line.equals("/m")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.MENU);
        } else if (line.equals(">")) {
            if (!(offset / 5 >= resp.count / 5))
                offset += 5;
        } else if (line.equals("<")) {
            if (offset != 0) {
                offset -= 5;
            }
        }
    }

    private void menuScreen(Scanner sc) {

        if (firstTime) {
            firstTime = false;
            System.out.println("chose one option :");
            System.out.println("1) chat");
            System.out.println("2) products");
            System.out.println("3) cart");
            System.out.println("4) orders");
            System.out.println("5) addresses");
            System.out.println("6) chat log");

        }
        String line = sc.nextLine();
        if (line.equals("1")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.CHAT);
        } else if (line.equals("2")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.PRODUCTS);
        } else if (line.equals("3")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.CART);
        } else if (line.equals("4")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.ORDERS_LIST);
        } else if (line.equals("5")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.ADDRESSES_LIST);
        } else if (line.equals("6")) {
            firstTime = true;
            currentState.setState(ru.sirosh.State.CHAT_HISTORY);
        } else System.out.println("wrong option");
    }

    private void registerScreen(Scanner sc) throws IOException {
        System.out.println("username:");
        String name = sc.nextLine();
        System.out.println("password:");
        String pass = sc.nextLine();
        User user = new User(name, pass);
        ReqWithUser req = new ReqWithUser("register", user);
        writer.println(mapper.writeValueAsString(req));
        ReqWithString resp = mapper.readValue(reader.readLine(), ReqWithString.class);
        if (resp.payload != null) {
            userToken = resp.payload;
            currentState.setState(ru.sirosh.State.MENU);
        } else {
            System.out.println("try again");
        }
    }

    private void loginScreen(Scanner sc) throws IOException {
        System.out.println("username:");
        String name = sc.nextLine();
        System.out.println("password:");
        String pass = sc.nextLine();
        User user = new User(name, pass);
        ReqWithUser req = new ReqWithUser("login", user);
        writer.println(mapper.writeValueAsString(req));
        ReqWithString resp = mapper.readValue(reader.readLine(), ReqWithString.class);
        if (resp.payload != null) {
            userToken = resp.payload;
            currentState.setState(ru.sirosh.State.MENU);
        } else {
            System.out.println("try again");
        }
    }

    private void notAuthScreen(Scanner sc) {//got
        System.out.println("type l to login or r to register");
        String line = sc.nextLine();
        if (line.equals("l")) {
            currentState.setState(ru.sirosh.State.NOT_AUTH_LOG);
        } else if (line.equals("r")) {
            currentState.setState(ru.sirosh.State.NOT_AUTH_REG);
        } else {
            System.out.println("wrong command\ntype l to login or r to register");
        }
    }
}
*/