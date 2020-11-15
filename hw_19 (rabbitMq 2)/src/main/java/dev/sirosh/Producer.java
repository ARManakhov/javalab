package dev.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sirosh.model.User;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


public class Producer {

    private static final String PRODUCER_EXCHANGE_NAME = "producer";
    private static final String LOG_EXCHANGE_NAME = "logs";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(PRODUCER_EXCHANGE_NAME, "fanout");
        channel.exchangeDeclare(LOG_EXCHANGE_NAME, "direct");

        Scanner consoleScanner = new Scanner(System.in);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        ObjectMapper objectMapper = new ObjectMapper();


        User.UserBuilder userBuilder = User.builder();
        for (; ; ) {
            System.out.println("please enter first name");
            userBuilder = userBuilder.firstName(consoleScanner.nextLine());
            System.out.println("please enter last name");
            userBuilder = userBuilder.lastName(consoleScanner.nextLine());
            System.out.println("please enter passport number");
            userBuilder = userBuilder.passportNumber(consoleScanner.nextLine());
            System.out.println("please enter phone number");
            userBuilder = userBuilder.phone(consoleScanner.nextLine());
            System.out.println("please enter email");
            userBuilder = userBuilder.email(consoleScanner.nextLine());

            Date birthDayDate = null;
            for (; ; ) {
                System.out.println("please enter birth day in dd/MM/yyyy format");
                try {
                    String birthDay = consoleScanner.nextLine();
                    userBuilder = userBuilder.birthDay(dateFormat.parse(birthDay));
                    break;
                } catch (ParseException e) {
                    System.out.println("date format error, try again");
                }
            }
            Date dateOfIssueDate = null;
            for (; ; ) {
                System.out.println("please enter date of issue in dd/MM/yyyy format");
                try {
                    String dateOfIssue = consoleScanner.nextLine();
                    userBuilder = userBuilder.dateOfIssue(dateFormat.parse(dateOfIssue));
                    break;
                } catch (ParseException e) {
                    System.out.println("date format error, try again");
                }
            }

            User User = userBuilder.build();
            String userData = objectMapper.writeValueAsString(User);
            channel.basicPublish(PRODUCER_EXCHANGE_NAME, "", null, userData.getBytes("UTF-8"));
            channel.basicPublish(LOG_EXCHANGE_NAME, "", null, ("new user :" + userData).getBytes("UTF-8"));

            System.out.println("u'r data is send.");
        }
    }
}
