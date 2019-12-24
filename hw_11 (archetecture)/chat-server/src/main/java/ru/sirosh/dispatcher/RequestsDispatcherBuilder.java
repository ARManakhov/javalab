package ru.sirosh.dispatcher;

import ru.sirosh.services.GetProductsService;
import ru.sirosh.services.LogInService;
import ru.sirosh.services.RegSevice;
import ru.sirosh.services.SendService;

public final class RequestsDispatcherBuilder {
    private LogInService logInService;
    private RegSevice regSevice;
    private SendService sendService;
    private GetProductsService getProductsService;

    private RequestsDispatcherBuilder() {
    }

    public static RequestsDispatcherBuilder aRequestsDispatcher() {
        return new RequestsDispatcherBuilder();
    }

    public RequestsDispatcherBuilder withLogInService(LogInService logInService) {
        this.logInService = logInService;
        return this;
    }

    public RequestsDispatcherBuilder withRegSevice(RegSevice regSevice) {
        this.regSevice = regSevice;
        return this;
    }

    public RequestsDispatcherBuilder withSendService(SendService sendService) {
        this.sendService = sendService;
        return this;
    }

    public RequestsDispatcherBuilder withGetProductsService(GetProductsService getProductsService) {
        this.getProductsService = getProductsService;
        return this;
    }

    public RequestsDispatcher build() {
        return new RequestsDispatcher(logInService, regSevice, sendService, getProductsService);
    }
}
