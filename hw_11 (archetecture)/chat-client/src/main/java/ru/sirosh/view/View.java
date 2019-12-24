package ru.sirosh.view;

import ru.sirosh.State.*;
import ru.sirosh.dto.Dto;
import ru.sirosh.dto.DtoMessage;
import ru.sirosh.models.User;
import ru.sirosh.models.UserBuilder;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.service.*;

import java.text.SimpleDateFormat;

public class View {
    User user = UserBuilder.anUser().build();
    private StateHolder currentState;

    BeforeLoginSelectorView beforeLoginSelectorView = new BeforeLoginSelectorView();
    LoginView loginView = new LoginView();
    RegisterView registerView = new RegisterView();
    MenuView menuView = new MenuView();
    ChatView chatView = new ChatView();
    ProductView productView = new ProductView();

    public View(StateHolder currentState) {
        beforeLoginSelectorView.setCurrentState(currentState);
        loginView.setCurrentState(currentState);
        loginView.setUser(user);
        registerView.setCurrentState(currentState);
        registerView.setUser(user);
        chatView.setCurrentState(currentState);
        chatView.setUser(user);
        productView.setCurrentState(currentState);
        productView.setUser(user);
        menuView.setCurrentState(currentState);
        this.currentState = currentState;
    }

    public Request render(Response response) {
        Request request = null;

        if (currentState.getState().equals(ru.sirosh.State.NOT_AUTH)) {
            beforeLoginSelectorView.execute(response);
        }
        if (currentState.getState().equals(ru.sirosh.State.NOT_AUTH_LOG)) {
            request = loginView.execute(response);
        }
        if (currentState.getState().equals(ru.sirosh.State.NOT_AUTH_REG)) {
            request = registerView.execute(response);
        }

        while (request == null) {
            if (currentState.getState().equals(ru.sirosh.State.MENU)) {
                menuView.execute(response);
            }
            if (currentState.getState().equals(ru.sirosh.State.CHAT)) {
                request = chatView.execute(response);
                if (currentState.getState().equals(ru.sirosh.State.MENU))
                    continue;
                else
                    break;
            }
            if (currentState.getState().equals(ru.sirosh.State.PRODUCTS) && response != null) {
                request = productView.execute(response);
                if (currentState.getState().equals(ru.sirosh.State.MENU))
                    continue;
                else
                    break;
            }
        }

        return request;
    }
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");
    public void render(Request request) {
        if (currentState.getState().equals(ru.sirosh.State.CHAT) && request.getCommand().equals("new_message")) {
            DtoMessage dtoMessage = (DtoMessage) request.getData();
            System.out.println("(" + sdf.format(dtoMessage.getTimestamp()) + ") " + dtoMessage.getUsername() + ": " + dtoMessage.getText());
        }
    }
}
