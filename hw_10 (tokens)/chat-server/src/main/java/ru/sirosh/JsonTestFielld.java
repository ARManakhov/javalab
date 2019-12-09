package ru.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sirosh.Models.request.ReqWithUser;
import ru.sirosh.Models.User;

import java.io.IOException;

public class JsonTestFielld {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        User user = new User("sirosh", "pass");
        ReqWithUser requestWithString = new ReqWithUser("login",user);
        System.out.println(mapper.writeValueAsString(requestWithString));
        String line = "{\"header\":\"login\",\"payload\":{\"username\":\"sirosh\",\"password\":\"pass\",\"id\":-1}}";
        ReqWithUser reqWithUser = mapper.readValue(line, ReqWithUser.class);
        System.out.println(reqWithUser);

    }
}
