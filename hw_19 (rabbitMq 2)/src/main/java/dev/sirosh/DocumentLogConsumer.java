package dev.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.time.LocalDateTime;

public class DocumentLogConsumer {
    private static final String LOG_EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection producerConnection = factory.newConnection();
        Channel channel = producerConnection.createChannel();

        channel.exchangeDeclare(LOG_EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, LOG_EXCHANGE_NAME, "");

        ObjectMapper objectMapper = new ObjectMapper();


        System.out.println("waiting for data");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(LocalDateTime.now() + " message : " + message);
            }catch (Exception e ){
                e.printStackTrace();
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
