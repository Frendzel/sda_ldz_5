package sda.ldz5;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.List;

public class FileWriter {

    private static final String NAME = "GRADES";

    public void save(List<Grade> grades) throws IOException {
        java.io.FileWriter fileWriter = new java.io.FileWriter(NAME);
        CSVFormat csvFormat = CSVFormat.DEFAULT;
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);
        csvPrinter.print(grades);
    }
}
