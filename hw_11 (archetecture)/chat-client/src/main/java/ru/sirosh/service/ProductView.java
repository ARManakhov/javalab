package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.*;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.List;
import java.util.Scanner;

public class ProductView implements ViewComponent {
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
                System.out.println("to see next page type >, prev page <");
                System.out.println("type product number to add to cart");
                if (user.getRole().equals("admin")) {
                    System.out.println("to add post type /a");
                    System.out.println("to remove post type /r");
                }
                return new Request("getProducts", new DtoGetList(offset, 5, user.getToken()));
            } else {
                System.out.println("page number " + ((offset / 5) + 1) + " of " + (1 + ((DtoProducts) response.getData()).getTotalCount() / 5));
                List<DtoProduct> products = ((DtoProducts) response.getData()).getProducts();
                int i = 0;
                for (DtoProduct product : products) {

                    System.out.println(++i + ")" + product.getName() + " \t| " + product.getCost() + " \t| " + product.getDescription());
                }
                String line = sc.nextLine();
                if (line.equals("/a") && user.getRole().equals("admin")) {
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
                    return new Request("add_product", DtoProductListBuilder
                            .aDtoProductList()
                            .withName(name)
                            .withDescription(description)
                            .withCost(cost)
                            .withToken(user.getToken())
                            .withNum(5)
                            .withOffset(offset)
                            .build());
                } else if (line.startsWith("/r") && user.getRole().equals("admin")) {
                    int productNum = Integer.parseInt(line.split(" ")[1]);
                    DtoProduct delProduct = products.get(--productNum);
                    if (delProduct != null) {
                        return new Request("del_product", DtoProductListBuilder
                                .aDtoProductList()
                                .withId(delProduct.getId())
                                .withToken(user.getToken())
                                .withNum(5)
                                .withOffset(offset)
                                .build());
                    } else {
                        System.out.println("wrong command");
                    }
                } else if (line.equals("/m")) {
                    firstTime = true;
                    currentState.setState(ru.sirosh.State.MENU);
                    return null;
                } else if (line.equals(">")) {
                    if (!(offset / 5 >= ((DtoProducts) response.getData()).getTotalCount() / 5))
                        offset += 5;
                    return new Request("getProducts", new DtoGetList(offset, 5, user.getToken()));
                } else if (line.equals("<")) {
                    if (offset != 0) {
                        offset -= 5;
                    }
                    return new Request("getProducts", new DtoGetList(offset, 5, user.getToken()));
                } else {
                    int productNum = Integer.parseInt(line);
                    DtoProduct cartProduct = products.get(--productNum);
                    return new Request("addToCart", cartProduct);
                }
            }
        }
    }
}
