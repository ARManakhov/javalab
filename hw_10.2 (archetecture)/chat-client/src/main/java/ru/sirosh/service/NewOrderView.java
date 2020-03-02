package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.*;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.util.List;
import java.util.Scanner;

public class NewOrderView implements ViewComponent {
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
        while (true){
            if (firstTime){
                firstTime=false;
                System.out.println("chose one address");
                offset = 0;
                return new Request("get_addresses", new DtoGetList(offset, 5, user.getToken()));
            }else {
                List<DtoAddress> dtoAddressList =((DtoAddresses) response.getData()).getAddresses();
                int i = 0;
                for (DtoAddress address : dtoAddressList) {
                    System.out.println(++i + ")" + address.getDescription());
                }
                String line = sc.nextLine();
                int num = Integer.parseInt(line);
                DtoAddress reqAddress = dtoAddressList.get(--num);
                reqAddress.setToken(user.getToken());
                currentState.setState(ru.sirosh.State.MENU);
                return new Request("new_order", reqAddress);
            }
        }
    }
}
