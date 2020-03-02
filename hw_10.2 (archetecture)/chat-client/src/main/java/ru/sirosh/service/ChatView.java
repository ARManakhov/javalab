package ru.sirosh.service;

import ru.sirosh.State;
import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.DtoCommand;
import ru.sirosh.dto.DtoMessageBuilder;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.io.IOException;
import java.util.Scanner;

public class ChatView implements ViewComponent {
    private User user;
    private StateHolder currentState;
    Scanner sc = new Scanner(System.in);
    boolean firstTime = true;

    public void setCurrentState(StateHolder currentState) {
        this.currentState = currentState;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public Request execute(Response response) {
        if (firstTime) {
            firstTime = false;
            System.out.println("to return to menu type /m");
        }

        String line = sc.nextLine();
        if (line.equals("/m")) {
            firstTime = true;
            currentState.setState(State.MENU);
            return null;
        } else {
            return new Request("message", DtoMessageBuilder.aDtoMessage()
                    .withToken(user.getToken())
                    .withText(line)
                    .build());
        }
    }
}
