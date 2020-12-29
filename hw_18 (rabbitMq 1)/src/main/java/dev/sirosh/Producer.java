package dev.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sirosh.model.User;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


public class Producer {
    private static final String EXCHANGE_NAME = "user_info";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        Scanner consoleScanner =new Scanner(System.in);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        ObjectMapper objectMapper = new ObjectMapper();

        for (;;){
            System.out.println("please enter first name");
            String firstName = consoleScanner.nextLine();
            System.out.println("please enter last name");
            String lastName = consoleScanner.nextLine();
            System.out.println("please enter passport number");
            String passportNumber = consoleScanner.nextLine();
            Date birthDayDate = null;
            for(;;) {
                System.out.println("please enter birth day in dd/MM/yyyy format");
                try{
                    String birthDay = consoleScanner.nextLine();
                    birthDayDate = dateFormat.parse(birthDay);
                    break;
                } catch (ParseException e) {
                    System.out.println("date format error, try again");
                }
            }
            Date dateOfIssueDate = null;
            for(;;) {
                System.out.println("please enter date of issue in dd/MM/yyyy format");
                try{
                    String dateOfIssue = consoleScanner.nextLine();
                    dateOfIssueDate = dateFormat.parse(dateOfIssue);
                    break;
                } catch (ParseException e) {
                    System.out.println("date format error, try again");
                }
            }

            User User = new User(firstName,lastName,passportNumber,birthDayDate,dateOfIssueDate);
            String userData = objectMapper.writeValueAsString(User);
            channel.basicPublish(EXCHANGE_NAME, "", null, userData.getBytes("UTF-8"));
            System.out.println("u'r data is send.");
        }
    }
}
