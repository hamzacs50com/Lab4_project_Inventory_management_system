package Refactored.database;

import Refactored.records.CustomerProduct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CustomerProductDatabase extends Database<CustomerProduct> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public CustomerProductDatabase(String filename) {super(filename);}

    @Override
    public CustomerProduct createRecordFrom(String line) {
        String[] parts = line.split(",");
        LocalDate purchaseDate = LocalDate.parse(parts[2], DATE_FORMATTER);
        CustomerProduct record = new CustomerProduct(parts[0], parts[1], purchaseDate);
        if (parts.length > 3) {
            record.setPaid(Boolean.parseBoolean(parts[3]));
        }
        return record;
    }
}