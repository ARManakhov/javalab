package ru.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Models.Request;
import ru.sirosh.Models.RequestWithUser;
import ru.sirosh.Models.User;

import java.io.IOException;

public class JsonTestFielld {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        User user = new User("sirosh", "pass");
        Request request = new Request("login",user);
        System.out.println(mapper.writeValueAsString(request));
        String line = "{\"header\":\"login\",\"payload\":{\"username\":\"sirosh\",\"password\":\"pass\",\"id\":-1}}";
        RequestWithUser requestWithUser = mapper.readValue(line, RequestWithUser.class);
        System.out.println(requestWithUser);

    }
}
