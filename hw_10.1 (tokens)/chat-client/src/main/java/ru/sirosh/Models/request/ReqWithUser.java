
package ru.sirosh.Models.request;


import ru.sirosh.Models.User;

public class ReqWithUser extends DummyRequest {

    private ReqWithUser(){}

    public ReqWithUser(String header, User payload) {
        super(header);
        this.payload = payload;
    }

    public User payload;


}