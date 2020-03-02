package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.*;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.List;
import java.util.Scanner;

public class AddressView implements ViewComponent {
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
                System.out.println("to add address type /a");
                System.out.println("to remove address type /r");
                return new Request("get_addresses", new DtoGetList(offset, 5, user.getToken()));
            } else {
                List<DtoAddress> dtoAddressList = ((DtoAddresses) response.getData()).getAddresses();
                int i = 0;
                for (DtoAddress address : dtoAddressList) {
                    System.out.println(++i + ")" + address.getDescription());
                }
                String line = sc.nextLine();
                if (line.equals("/a")) {
                    System.out.println("type new address:");
                    String description = sc.nextLine();
                    return new Request("add_address", DtoAddressBuilder.aDtoAddress()
                            .withDescription(description)
                            .withToken(user.getToken())
                            .build());
                } else if (line.startsWith("/r")) {
                    int productNum = Integer.parseInt(line.split(" ")[1]);
                    DtoAddress address = dtoAddressList.get(--productNum);
                    if (address != null) {
                        address.setToken(user.getToken());
                        return new Request("del_address", address);
                    } else {
                        System.out.println("wrong command");
                    }
                } else if (line.equals("/m")) {
                    firstTime = true;
                    currentState.setState(ru.sirosh.State.MENU);
                    return null;
                }
            }
        }
    }
}
