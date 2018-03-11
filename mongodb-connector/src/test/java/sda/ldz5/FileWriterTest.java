package sda.ldz5;

import org.junit.Test;

import java.util.List;

public class FileWriterTest {


    @Test
    public void save() throws Exception {
        //given
        Connector connector = new Connector();
        List<Grade> grades = connector.getGrades();
        FileWriter fileWriter = new FileWriter();
        //when
        fileWriter.save(grades);
        //then
        //TODO
    }

}