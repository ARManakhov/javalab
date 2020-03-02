package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.*;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.List;
import java.util.Scanner;

public class OrderView  implements ViewComponent {
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
                System.out.println("to view order details type number ");
                return new Request("get_orders", new DtoGetList(offset, 5, user.getToken()));

            }
            System.out.println("orders :");
            DtoOrders dtoOrders = (DtoOrders) response.getData();
            List<DtoOrder> orders = dtoOrders.getOrders();
            int i = 0;
            for (DtoOrder order : orders) {
                System.out.println((++i) + ") id: " + order.getId() + " | " + order.getStatus());
            }
            String line = sc.nextLine();
            if (line.equals("/m")) {
                firstTime = true;
                currentState.setState(ru.sirosh.State.MENU);
                return null;
            } else {
                int productNum = Integer.parseInt(line);
                DtoOrder viewOrder = orders.get(--productNum);
                System.out.println("order id : " + viewOrder.getId());
                System.out.println("order status : " + viewOrder.getStatus());
                System.out.println("order address : " + viewOrder.getDtoAddress().getDescription());
                System.out.println("order products : ");
                int j = 0;
                for (DtoProduct product : viewOrder.getDtoProductList()) {
                    if (product != null) {
                        System.out.println(++j + ")" + product.getName() + " \t| " + product.getCost() + " \t| " + product.getDescription());
                    }
                }
            }
        }
    }
}
