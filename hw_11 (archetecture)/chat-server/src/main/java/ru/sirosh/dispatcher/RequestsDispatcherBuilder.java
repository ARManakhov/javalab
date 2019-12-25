package ru.sirosh.dispatcher;

import ru.sirosh.services.*;

public final class RequestsDispatcherBuilder {
    private LogInService logInService;
    private RegSevice regSevice;
    private SendService sendService;
    private GetProductsService getProductsService;
    private AddProductService addProductService;
    private DeleteProductService deleteProductService;
    private AddProductCartService addProductCartService;
    private GetCartService getCartService;
    private DelProductCartService delProductCartService;
    private GetAddressesService getAddressesService;
    private DelAddressService delAddressService;
    private AddAddressService addAddressService;
    private NewOrderService newOrderService;
    private GetMessagesService getMessagesService;
    private GetOrdersService getOrdersService;

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

    public RequestsDispatcherBuilder withDeleteProductService(DeleteProductService deleteProductService) {
        this.deleteProductService = deleteProductService;
        return this;
    }

    public RequestsDispatcherBuilder withAddProductCartService(AddProductCartService addProductCartService) {
        this.addProductCartService = addProductCartService;
        return this;
    }

    public RequestsDispatcherBuilder withGetCartService(GetCartService getCartService) {
        this.getCartService = getCartService;
        return this;
    }

    public RequestsDispatcherBuilder withDelProductCartService(DelProductCartService delProductCartService) {
        this.delProductCartService = delProductCartService;
        return this;
    }

    public RequestsDispatcherBuilder withGetAddressesService(GetAddressesService getAddressesService) {
        this.getAddressesService = getAddressesService;
        return this;
    }

    public RequestsDispatcherBuilder withDelAddressService(DelAddressService delAddressService) {
        this.delAddressService = delAddressService;
        return this;
    }

    public RequestsDispatcherBuilder withAddAddressService(AddAddressService addAddressService) {
        this.addAddressService = addAddressService;
        return this;
    }

    public RequestsDispatcherBuilder withNewOrderService(NewOrderService newOrderService) {
        this.newOrderService = newOrderService;
        return this;
    }

    public RequestsDispatcherBuilder withGetMessagesService(GetMessagesService getMessagesService) {
        this.getMessagesService = getMessagesService;
        return this;
    }

    public RequestsDispatcherBuilder withGetOrdersService(GetOrdersService getOrdersService) {
        this.getOrdersService = getOrdersService;
        return this;
    }

    public RequestsDispatcher build() {
        RequestsDispatcher requestsDispatcher = new RequestsDispatcher();
        requestsDispatcher.setLogInService(logInService);
        requestsDispatcher.setRegSevice(regSevice);
        requestsDispatcher.setSendService(sendService);
        requestsDispatcher.setGetProductsService(getProductsService);
        requestsDispatcher.setAddProductService(addProductService);
        requestsDispatcher.setDeleteProductService(deleteProductService);
        requestsDispatcher.setAddProductCartService(addProductCartService);
        requestsDispatcher.setGetCartService(getCartService);
        requestsDispatcher.setDelProductCartService(delProductCartService);
        requestsDispatcher.setGetAddressesService(getAddressesService);
        requestsDispatcher.setDelAddressService(delAddressService);
        requestsDispatcher.setAddAddressService(addAddressService);
        requestsDispatcher.setNewOrderService(newOrderService);
        requestsDispatcher.setGetMessagesService(getMessagesService);
        requestsDispatcher.setGetOrdersService(getOrdersService);
        return requestsDispatcher;
    }
}
