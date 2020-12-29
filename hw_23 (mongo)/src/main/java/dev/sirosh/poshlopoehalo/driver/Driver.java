package dev.sirosh.poshlopoehalo.driver;

import com.mongodb.client.*;
import dev.sirosh.poshlopoehalo.model.Movement;
import org.bson.Document;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class Driver {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create();
        MongoDatabase database = client.getDatabase("poshloPoehalo");

        MongoCollection<Document> movementCollection = database.getCollection("movement");

        FindIterable<Document> movementResultDocuments = movementCollection.find(lt("price",  3000))
                .projection(fields(include("from", "to", "price"), excludeId()));

        for (Document document : movementResultDocuments) {
            System.out.println(document.toJson());
        }

        MongoCollection<Document> cityCollection = database.getCollection("city");

        Document searchQuery = new Document();
        searchQuery.append("name", "Moscow");

        FindIterable<Document> cityResultDocuments = cityCollection.find(searchQuery)
                .projection(fields(include("name", "cordX", "cordY"), excludeId()));

        for (Document document : cityResultDocuments) {
            System.out.println(document.toJson());
        }

    }
}
