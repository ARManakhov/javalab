package ru.sirosh.dispatcher;

import ru.sirosh.services.*;

public final class RequestsDispatcherBuilder {
    private LogInService logInService;
    private RegSevice regSevice;
    private SendService sendService;
    private GetProductsService getProductsService;
    private AddProductService addProductService;

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

    public RequestsDispatcherBuilder withAddProductService(AddProductService addProductService) {
        this.addProductService = addProductService;
        return this;
    }

    public RequestsDispatcher build() {
        return new RequestsDispatcher(logInService, regSevice, sendService, getProductsService, addProductService);
    }
}
