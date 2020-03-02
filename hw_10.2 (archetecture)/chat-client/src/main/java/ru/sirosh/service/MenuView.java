package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.Scanner;

public class MenuView implements ViewComponent {

    private StateHolder currentState;
    Scanner sc = new Scanner(System.in);
    boolean firstTime = true;
    public void setCurrentState(StateHolder currentState) {
        this.currentState = currentState;
    }

    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public Request execute(Response response) {
        while (true){
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
                return null;
            } else if (line.equals("2")) {
                firstTime = true;
                currentState.setState(ru.sirosh.State.PRODUCTS);
                return null;
            } else if (line.equals("3")) {
                firstTime = true;
                currentState.setState(ru.sirosh.State.CART);
                return null;
            } else if (line.equals("4")) {
                firstTime = true;
                currentState.setState(ru.sirosh.State.ORDERS_LIST);
                return null;
            } else if (line.equals("5")) {
                firstTime = true;
                currentState.setState(ru.sirosh.State.ADDRESSES_LIST);
                return null;
            } else if (line.equals("6")) {
                firstTime = true;
                currentState.setState(ru.sirosh.State.CHAT_HISTORY);
                return null;
            } else System.out.println("wrong option");
        }

    }
}
