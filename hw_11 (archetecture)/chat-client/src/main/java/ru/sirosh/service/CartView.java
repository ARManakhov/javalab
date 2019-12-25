package ru.sirosh.service;

import ru.sirosh.State;
import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.DtoGetList;
import ru.sirosh.dto.DtoProduct;
import ru.sirosh.dto.DtoProductListBuilder;
import ru.sirosh.dto.DtoProducts;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.List;
import java.util.Scanner;

public class CartView implements ViewComponent {
    private StateHolder currentState;
    Scanner sc = new Scanner(System.in);
    boolean firstTime = true;
    int offset = 0;

    public void setCurrentState(StateHolder currentState) {
        this.currentState = currentState;
    }

    User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public Request execute(Response response) {
        while (true) {
            if (firstTime) {
                firstTime = false;
                System.out.println("to return to menu type /m");
                System.out.println("to make order type /o");
                System.out.println("to delete product from cart type /r");
                offset = 0;
                return new Request("get_cart", new DtoGetList(offset, 5, user.getToken()));
            } else {
                List<DtoProduct> products = ((DtoProducts) response.getData()).getProducts();
                int i = 0;
                for (DtoProduct product : products) {

                    System.out.println(++i + ")" + product.getName() + " \t| " + product.getCost() + " \t| " + product.getDescription());
                }
                String line = sc.nextLine();
                if (line.equals("/o")) {
                    firstTime = true;
                    currentState.setState(State.ORDER_ADD);
                    return null;
                } else if (line.startsWith("/r")) {
                    int productNum=0;
                    try{
                        productNum = Integer.parseInt(line.split(" ")[1]);
                        DtoProduct delProduct = products.get(--productNum);
                        if (delProduct != null) {
                            return new Request("del_product_cart", DtoProductListBuilder
                                    .aDtoProductList()
                                    .withId(delProduct.getId())
                                    .withToken(user.getToken())
                                    .withNum(5)
                                    .withOffset(offset)
                                    .build());
                        } else {
                            System.out.println("wrong command");
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("wrong command");
                    }
                } else if (line.equals("/m")) {
                    firstTime = true;
                    currentState.setState(ru.sirosh.State.MENU);
                    return null;
                } else {
                    System.out.println("wrong command");
                }
            }
        }
    }
}
/*
* if (firstTime) {
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
* */