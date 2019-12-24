package ru.sirosh.dispatcher;


import ru.sirosh.dto.Dto;
import ru.sirosh.protocol.Request;
import ru.sirosh.services.GetProductsService;
import ru.sirosh.services.LogInService;
import ru.sirosh.services.RegSevice;
import ru.sirosh.services.SendService;

public class RequestsDispatcher  {

    private LogInService logInService;
    private RegSevice regSevice;
    private SendService sendService;
    private GetProductsService getProductsService;

    public RequestsDispatcher(LogInService logInService, RegSevice regSevice, SendService sendService, GetProductsService getProductsService) {
        this.logInService = logInService;
        this.regSevice = regSevice;
        this.sendService = sendService;
        this.getProductsService = getProductsService;
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