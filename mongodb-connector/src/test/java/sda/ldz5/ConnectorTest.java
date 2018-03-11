package sda.ldz5;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

public class ConnectorTest {

    private static final Long COLLECTION_SIZE = 802L;

    @Test
    public void connect() throws Exception {
        //given
        Connector connector = new Connector();

        //when
        MongoDatabase db = connector.connect();

        //then
        Assert.assertEquals(db.getName(), "test_ldz5");
        MongoCollection<Document> grades = db.getCollection("grades");
        FindIterable<Document> documents = grades.find();
        for (Document document : documents) {
            System.out.println(document);
        }
        Assert.assertEquals(COLLECTION_SIZE, (Long) grades.count());

    }

    @Test
    public void findStudentsWhereStudentIdGt100() throws Exception {
        //given
        Connector connector = new Connector();
        JsonWriterSettings jsonBuilder = JsonWriterSettings.builder().indent(true).build();

        //when
        MongoDatabase db = connector.connect();

        //then
        MongoCollection<Document> grades = db.getCollection("grades");

        //first way
        Document filter = new Document("student_id",
                new Document("$gt", 100));

        //second way
        Bson filter2 = gt("student_id", 100);

        FindIterable<Document> documents = grades.find(filter2);
        for (Document document : documents) {
            System.out.println(document.toJson(jsonBuilder));
        }
        Assert.assertEquals(COLLECTION_SIZE, (Long) grades.count());

    }

    @Test
    public void findStudentsWhereStudentIdGt100AndGradeTypeIsExam() throws Exception {
        //given
        Connector connector = new Connector();
        JsonWriterSettings jsonBuilder = JsonWriterSettings.builder().indent(true).build();

        //when
        MongoDatabase db = connector.connect();

        //then
        MongoCollection<Document> grades = db.getCollection("grades");

//
//        {
//            $and: [{
//            student_id: {
//                $gt: 100
//            }
//        }, {
//            type: "exam"
//        }]
//        }

        //first way
        Document filter = new Document("student_id",
                new Document("$gt", 100));

        Document filterExam = new Document("type", "exam");

        List andList = new ArrayList<>();
        andList.add(filter);
        andList.add(filterExam);

        Document and = new Document("$and", andList);

        //second way
        Bson filter2 =
                and(
                        gt("student_id", 100),
                        eq("type", "exam")
                );

        FindIterable<Document> documentsFilter1 = grades.find(and);
        for (Document document : documentsFilter1) {
            System.out.println(document.toJson(jsonBuilder));
        }
        List<Grade> gradeObjects = new ArrayList<>();

        FindIterable<Document> documentsFilter2 = grades.find(filter2);
        for (Document document : documentsFilter2) {
            System.out.println(document.toJson(jsonBuilder));
            Grade grade = new Grade();
            grade.set_id(document.get("_id"));
            grade.setScore(document.getDouble("score"));
            grade.setStudent_id(document.getString("student_id"));
            grade.setType(document.getString("type"));
            gradeObjects.add(grade);
        }
        gradeObjects.forEach(System.out::println);

        Assert.assertEquals(COLLECTION_SIZE, (Long) grades.count());

    }

    //> db.grades.aggregate([{ $match: {}, },
    // { $group: {  _id: "$student_id", srednia: {$avg: "$score"} } } ] )

    @Test
    public void calculateAvgForStudents() {
        //given
        Connector connector = new Connector();
        JsonWriterSettings jsonBuilder = JsonWriterSettings.builder().indent(true).build();

        //when
        MongoDatabase db = connector.connect();

        //then
        MongoCollection<Document> grades = db.getCollection("grades");

        //matcher
        Bson match = match(new Document());
        //grouper
        Bson group = group(
                "$student_id",
                avg("srednia", "$score")
        );
        Bson sort = sort(new Document("srednia", -1));

        //pipeline
        List<Bson> pipeline = new ArrayList();
        pipeline.add(match);
        pipeline.add(group);
        pipeline.add(sort);

        //then
        AggregateIterable<Document> results = grades.aggregate(pipeline);
        for (Document result : results) {
            System.out.println(result.toJson(jsonBuilder));
        }

    }

}