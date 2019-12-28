
package dev.sirosh.models.request;


import dev.sirosh.models.User;

public class ReqWithUser extends DummyRequest {

    private ReqWithUser(){}

    public ReqWithUser(String header, User payload) {
        super(header);
        this.payload = payload;
    }

    public User payload;


}