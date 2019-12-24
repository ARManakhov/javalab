package ru.sirosh.dispatcher;


import ru.sirosh.dto.Dto;
import ru.sirosh.protocol.Request;
import ru.sirosh.services.*;

public class RequestsDispatcher  {

    private LogInService logInService;
    private RegSevice regSevice;
    private SendService sendService;
    private GetProductsService getProductsService;
    private AddProductService addProductService;
    private DeleteProductService deleteProductService;
    private AddProductCartService addProductCartService;
    public RequestsDispatcher(LogInService logInService, RegSevice regSevice, SendService sendService, GetProductsService getProductsService, AddProductService addProductService) {
        this.logInService = logInService;
        this.regSevice = regSevice;
        this.sendService = sendService;
        this.getProductsService = getProductsService;
        this.addProductService = addProductService;
    }

    public Dto doDispatch(Request request) {
        if (request.getCommand().equals("logIn")) {
            return logInService.execute(request);
        } else if (request.getCommand().equals("Register")) {
            return regSevice.execute(request);
        } else if (request.getCommand().equals("chatMode")){
            return null;
        }else if (request.getCommand().equals("message")){
            return sendService.execute(request);
        }else if (request.getCommand().equals("getProducts")){
            return getProductsService.execute(request);
        }else if (request.getCommand().equals("add_product")){
            return addProductService.execute(request);
        }else if (request.getCommand().equals("del_product")){
            return deleteProductService.execute(request);
        }else if (request.getCommand().equals("add_to_cart_product")){
            return addProductCartService.execute(request);//todo реализацию
        }
        throw new IllegalArgumentException();
    }

    public LogInService getLogInService() {
        return logInService;
    }

    public void setLogInService(LogInService logInService) {
        this.logInService = logInService;
    }

    public RegSevice getRegSevice() {
        return regSevice;
    }

    public SendService getSendService() {
        return sendService;
    }

    public void setSendService(SendService sendService) {
        this.sendService = sendService;
    }
}