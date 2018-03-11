package sda.ldz5;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.List;

class FileWriter {

    private static final String NAME = "GRADES";
    private static final String[] HEADERS = {"ID", "STUDENT_ID", "TYPE", "SCORE"};

    void save(List<Grade> grades) throws IOException {
        java.io.FileWriter fileWriter = new java.io.FileWriter(NAME);

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader();
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);
        csvPrinter.printRecord(HEADERS);
        grades.forEach(grade -> {
            try {
                csvPrinter.printRecord(grade.get_id(), grade.getStudent_id(), grade.getType(), grade.getScore());
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    fileWriter.flush();
                    fileWriter.close();
                    csvPrinter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        fileWriter.flush();
        fileWriter.close();
        csvPrinter.close();
    }
}
