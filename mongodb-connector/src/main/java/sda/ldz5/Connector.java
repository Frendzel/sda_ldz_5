package sda.ldz5;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoCredential.createCredential;

public class Connector {

    MongoClient mongoClient;

    public MongoDatabase connect() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        ServerAddress serverAddress = new ServerAddress();
        MongoCredential credential =
                createCredential(propertiesLoader.getUser(),
                        propertiesLoader.getDB(),
                        propertiesLoader.getPass().toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        mongoClient = new MongoClient(serverAddress, credentials);
        return mongoClient.getDatabase(propertiesLoader.getDB());
    }

    public List<Grade> getGrades() {
        MongoDatabase db = connect();
        MongoCollection<Document> grades = db.getCollection("grades");
        FindIterable<Document> documents = grades.find();
        List<Grade> objects = new ArrayList<>();
        for (Document document : documents) {
            Grade grade = new Grade();
            grade.set_id(document.get("_id"));
            grade.setScore(document.getDouble("score"));
            grade.setStudent_id(document.get("student_id"));
            grade.setType(document.getString("type"));
            objects.add(grade);
        }
        return objects;
    }
}
