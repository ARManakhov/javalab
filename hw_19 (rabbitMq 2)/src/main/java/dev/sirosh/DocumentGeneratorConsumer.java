package dev.sirosh;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dev.sirosh.documentConstructor.DeductionDocumentConstructor;
import dev.sirosh.documentConstructor.DocumentConstructor;
import dev.sirosh.documentConstructor.FiringDocumentConstructor;
import dev.sirosh.documentConstructor.VacationDocumentConstructor;
import dev.sirosh.model.User;

import java.io.File;

public class DocumentGeneratorConsumer {

    private static final String EXCHANGE_NAME = "user_info";

    public static void main(String[] argv) throws Exception {
        if (argv.length == 0){
            System.out.println("there is no arg, exiting");
            return;
        }

        DocumentConstructor documentConstructor = null;

        switch (argv[0]){
            case "1":
                System.out.println("generating firing doc");
                documentConstructor = new FiringDocumentConstructor(new File("firing_documents"));
                break;
            case "2":
                System.out.println("generating deduction doc");
                documentConstructor = new DeductionDocumentConstructor(new File("deduction_documents"));
                break;
            case "3":
                System.out.println("generating vacation doc");
                documentConstructor = new VacationDocumentConstructor(new File("vacation_documents"));
                break;
            default:
                System.out.println("unknown arg, exiting");
                return;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        ObjectMapper objectMapper = new ObjectMapper();

        DocumentConstructor finalDocumentConstructor = documentConstructor;

        System.out.println("waiting for data");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String message = new String(delivery.getBody(), "UTF-8");
                User user = objectMapper.readValue(message, User.class);

                finalDocumentConstructor.construct(user, user.getFirstName() + "_" + user.getPassportNumber() + ".pdf");
            }catch (Exception e ){
                e.printStackTrace();
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
