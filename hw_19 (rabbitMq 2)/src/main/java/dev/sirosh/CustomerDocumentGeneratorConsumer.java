package dev.sirosh;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dev.sirosh.documentConstructor.*;
import dev.sirosh.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDocumentGeneratorConsumer {

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange.DOCUMENT, "topic");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, Exchange.DOCUMENT, "customer");

        channel.exchangeDeclare(Exchange.LOG, "direct");

        ObjectMapper objectMapper = new ObjectMapper();

        List<DocumentConstructor> documentConstructors = new ArrayList<DocumentConstructor>(){{
            add(new ContractDocumentConstructor(new File("contracts")));
            add(new DiscountCouponDocumentConstructor(new File("discounts")));
        }};

        System.out.println("waiting for data");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String message = new String(delivery.getBody(), "UTF-8");
                User user = objectMapper.readValue(message, User.class);

                documentConstructors.forEach(documentConstructor -> {
                    try {
                        documentConstructor.construct(user,
                                user.getFirstName() + "_" + user.getPassportNumber() + ".pdf");
                        String logMessage = "new discount and contract generated for " + user.getFirstName();
                        channel.basicPublish(Exchange.LOG, "", null, logMessage.getBytes("UTF-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }catch (Exception e ){
                e.printStackTrace();
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
