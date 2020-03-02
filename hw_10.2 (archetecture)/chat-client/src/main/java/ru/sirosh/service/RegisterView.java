package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.DtoUser;
import ru.sirosh.dto.DtoUserBuilder;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.Scanner;

public class RegisterView implements ViewComponent {
    private StateHolder currentState;
    Scanner sc = new Scanner(System.in);
    User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setCurrentState(StateHolder currentState) {
        this.currentState = currentState;
    }

    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public Request execute(Response response) {
        if (response == null) {
            System.out.println("username:");
            String name = sc.nextLine();
            System.out.println("password:");
            String pass = sc.nextLine();
            DtoUser user = DtoUserBuilder.aDtoUser()
                    .withUsername(name)
                    .withPassword(pass)
                    .build();
            return new Request("Register", user);
        } else {
            DtoUser user = (DtoUser) response.getData();
            if (user.getToken() != null) {
                this.user.setToken(user.getToken());
                this.user.setRole(user.getRole());
                currentState.setState(ru.sirosh.State.MENU);
            } else {
                System.out.println("try again");
            }
            return null;
        }
    }
}
