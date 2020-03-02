
package ru.sirosh.models.request;


import ru.sirosh.models.User;

public class ReqWithUser extends DummyRequest {

    private ReqWithUser(){}

    public ReqWithUser(String header, User payload) {
        super(header);
        this.payload = payload;
    }

    public User payload;


}