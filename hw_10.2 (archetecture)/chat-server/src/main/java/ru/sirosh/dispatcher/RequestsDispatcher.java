package ru.sirosh.dispatcher;


import ru.sirosh.dto.Dto;
import ru.sirosh.protocol.Request;
import ru.sirosh.services.*;

public class RequestsDispatcher {

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
    public GetOrdersService getGetOrdersService() {
        return getOrdersService;
    }

    public void setGetOrdersService(GetOrdersService getOrdersService) {
        this.getOrdersService = getOrdersService;
    }

    private GetOrdersService getOrdersService;
    public Dto doDispatch(Request request) {
        if (request.getCommand().equals("logIn")) {
            return logInService.execute(request);
        } else if (request.getCommand().equals("Register")) {
            return regSevice.execute(request);
        } else if (request.getCommand().equals("chatMode")) {
            return null;
        } else if (request.getCommand().equals("message")) {
            return sendService.execute(request);
        } else if (request.getCommand().equals("getProducts")) {
            return getProductsService.execute(request);
        } else if (request.getCommand().equals("add_product")) {
            return addProductService.execute(request);
        } else if (request.getCommand().equals("del_product")) {
            return deleteProductService.execute(request);
        } else if (request.getCommand().equals("add_to_cart_product")) {
            return addProductCartService.execute(request);
        } else if (request.getCommand().equals("get_cart")) {
            return getCartService.execute(request);
        } else if (request.getCommand().equals("del_product_cart")) {
            return delProductCartService.execute(request);
        } else if (request.getCommand().equals("get_addresses")) {
            return getAddressesService.execute(request);
        } else if (request.getCommand().equals("del_address")) {
            return delAddressService.execute(request);
        } else if (request.getCommand().equals("add_address")) {
            return addAddressService.execute(request);
        } else if (request.getCommand().equals("new_order")) {
            return newOrderService.execute(request);
        } else if (request.getCommand().equals("get_orders")) {
            return getOrdersService.execute(request);
        } else if (request.getCommand().equals("get_messages")) {
            return getMessagesService.execute(request);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public GetMessagesService getGetMessagesService() {
        return getMessagesService;
    }

    public void setGetMessagesService(GetMessagesService getMessagesService) {
        this.getMessagesService = getMessagesService;
    }

    public NewOrderService getNewOrderService() {
        return newOrderService;
    }

    public void setNewOrderService(NewOrderService newOrderService) {
        this.newOrderService = newOrderService;
    }

    public AddAddressService getAddAddressService() {
        return addAddressService;
    }

    public void setAddAddressService(AddAddressService addAddressService) {
        this.addAddressService = addAddressService;
    }

    public GetAddressesService getGetAddressesService() {
        return getAddressesService;
    }

    public DelAddressService getDelAddressService() {
        return delAddressService;
    }

    public void setDelAddressService(DelAddressService delAddressService) {
        this.delAddressService = delAddressService;
    }

    public void setGetAddressesService(GetAddressesService getAddressesService) {
        this.getAddressesService = getAddressesService;
    }

    public RegSevice getRegSevice() {
        return regSevice;
    }

    public void setRegSevice(RegSevice regSevice) {
        this.regSevice = regSevice;
    }

    public SendService getSendService() {
        return sendService;
    }

    public void setSendService(SendService sendService) {
        this.sendService = sendService;
    }

    public GetProductsService getGetProductsService() {
        return getProductsService;
    }

    public void setGetProductsService(GetProductsService getProductsService) {
        this.getProductsService = getProductsService;
    }

    public AddProductService getAddProductService() {
        return addProductService;
    }

    public void setAddProductService(AddProductService addProductService) {
        this.addProductService = addProductService;
    }

    public DeleteProductService getDeleteProductService() {
        return deleteProductService;
    }

    public void setDeleteProductService(DeleteProductService deleteProductService) {
        this.deleteProductService = deleteProductService;
    }

    public AddProductCartService getAddProductCartService() {
        return addProductCartService;
    }

    public void setAddProductCartService(AddProductCartService addProductCartService) {
        this.addProductCartService = addProductCartService;
    }

    public GetCartService getGetCartService() {
        return getCartService;
    }

    public void setGetCartService(GetCartService getCartService) {
        this.getCartService = getCartService;
    }

    public DelProductCartService getDelProductCartService() {
        return delProductCartService;
    }

    public void setDelProductCartService(DelProductCartService delProductCartService) {
        this.delProductCartService = delProductCartService;
    }

    public LogInService getLogInService() {
        return logInService;
    }

    public void setLogInService(LogInService logInService) {
        this.logInService = logInService;
    }
}