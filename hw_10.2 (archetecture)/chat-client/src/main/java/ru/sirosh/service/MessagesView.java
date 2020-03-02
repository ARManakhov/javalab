package ru.sirosh.service;

import ru.sirosh.context.ViewComponent;
import ru.sirosh.dto.*;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;
import ru.sirosh.view.StateHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class MessagesView implements ViewComponent {
    private StateHolder currentState;
    Scanner sc = new Scanner(System.in);
    boolean firstTime = true;
    int offset = 0;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");

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
                return new Request("get_messages", new DtoGetList(offset, 5, user.getToken()));
            } else {
                System.out.println("page number " + ((offset / 5) + 1) + " of " + (1 + ((DtoMessages) response.getData()).getTotalCount() / 5));
                List<DtoMessage> dtoMessages = ((DtoMessages) response.getData()).getMessages();
                for (DtoMessage message : dtoMessages) {
                    System.out.println("( " + sdf.format(message.getTimestamp()) + " ) " + message.getUsername() + ": " + message.getText());
                }
                String line = sc.nextLine();
                if (line.equals("/m")) {
                    firstTime = true;
                    currentState.setState(ru.sirosh.State.MENU);
                    return null;
                } else if (line.equals(">")) {
                    if (!(offset / 5 >= ((DtoMessages) response.getData()).getTotalCount() / 5))
                        offset += 5;
                    return new Request("get_messages", new DtoGetList(offset, 5, user.getToken()));
                } else if (line.equals("<")) {
                    if (offset != 0) {
                        offset -= 5;
                    }
                    return new Request("get_messages", new DtoGetList(offset, 5, user.getToken()));
                }
            }
        }
    }
}
