package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.Scanner;

public class BeforeLoginSelectorView implements ViewComponent {
    private StateHolder currentState;
    Scanner sc = new Scanner(System.in);
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
            System.out.println("type l to login or r to register");
            String line = sc.nextLine();
            if (line.equals("l")) {
                currentState.setState(ru.sirosh.State.NOT_AUTH_LOG);
                break;
            } else if (line.equals("r")) {
                currentState.setState(ru.sirosh.State.NOT_AUTH_REG);
                break;
            } else {
                System.out.println("wrong command\ntype l to login or r to register");
            }
        }
        return null;
    }
}
