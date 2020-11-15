package dev.sirosh;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dev.sirosh.model.User;

import java.util.regex.Pattern;

public class DocumentValidateConsumer {

    private static final String PRODUCER_EXCHANGE_NAME = "producer";
    private static final String LOG_EXCHANGE_NAME = "logs";
    private static final Pattern MAIL_REGEX = Pattern.compile("^(.+)@(.+)$");
    private static final String PHONE_START = "+7";


    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection producerConnection = factory.newConnection();
        Channel channel = producerConnection.createChannel();

        channel.exchangeDeclare(PRODUCER_EXCHANGE_NAME, "fanout");
        String producerQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(producerQueueName, PRODUCER_EXCHANGE_NAME, "");

        channel.exchangeDeclare(LOG_EXCHANGE_NAME, "direct");

        ObjectMapper objectMapper = new ObjectMapper();


        System.out.println("waiting for data");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String message = new String(delivery.getBody(), "UTF-8");
                User user = objectMapper.readValue(message, User.class);
                String logMessage = "new message with user " + user.toString();
                channel.basicPublish(LOG_EXCHANGE_NAME, "", null, logMessage.getBytes("UTF-8"));

                boolean isValid = true;
                if (!MAIL_REGEX.matcher(user.getEmail()).find()){
                    logMessage = "new user is not valid (wrong email)";
                    isValid = false;
                }
                if (!user.getPhone().startsWith(PHONE_START)){
                    logMessage = "new user is not valid (wrong email)";
                    isValid = false;
                }
                if (isValid) logMessage = "new user is valid";
                channel.basicPublish(LOG_EXCHANGE_NAME, "", null, logMessage.getBytes("UTF-8"));
            }catch (Exception e ){
                e.printStackTrace();
            }
        };
        channel.basicConsume(producerQueueName, true, deliverCallback, consumerTag -> { });
    }
}
