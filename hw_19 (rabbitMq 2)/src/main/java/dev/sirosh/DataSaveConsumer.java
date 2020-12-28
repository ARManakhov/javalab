package dev.sirosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class DataSaveConsumer {

    private static final File dataFile = new File("rawUserData");
    private static FileWriter dataFileWriter = null;

    static {
        try {
            dataFileWriter = new FileWriter(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataSaveConsumer() throws IOException {
    }

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection producerConnection = factory.newConnection();
        Channel channel = producerConnection.createChannel();

        channel.exchangeDeclare(Exchange.PRODUCER, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, Exchange.PRODUCER, "");

        channel.exchangeDeclare(Exchange.LOG, "direct");

        ObjectMapper objectMapper = new ObjectMapper();


        System.out.println("waiting for data");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String message = new String(delivery.getBody(), "UTF-8");
                String logMessage = "new data recieved : " + message ;
                channel.basicPublish(Exchange.LOG, "", null, logMessage.getBytes("UTF-8"));
                dataFileWriter.append(message);
                dataFileWriter.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
