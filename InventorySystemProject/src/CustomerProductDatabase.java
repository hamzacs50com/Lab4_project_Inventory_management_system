import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class CustomerProductDatabase {
    private ArrayList<CustomerProduct> records;
    private String filename;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    
    public CustomerProductDatabase(String filename) {
        this.filename = filename;
        this.records = new ArrayList<>();
        readFromFile();
    }

   
    public void readFromFile() {
        records.clear();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    records.add(createRecordFrom(line));
                }
            }
        } catch (FileNotFoundException e) {
             System.err.println("File not found: " + filename + ". A new file will be created upon saving.");
        }
    }

    
    public CustomerProduct createRecordFrom(String line) {
        String[] parts = line.split(",");
        LocalDate purchaseDate = LocalDate.parse(parts[2], DATE_FORMATTER);
        CustomerProduct record = new CustomerProduct(parts[0], parts[1], purchaseDate);
        if (parts.length > 3) {
            record.setPaid(Boolean.parseBoolean(parts[3]));
        }
        return record;
    }

    
    public ArrayList<CustomerProduct> returnAllRecords() {
        return records;
    }

    
    public boolean contains(String key) {
        for (CustomerProduct record : records) {
            if (record.getSearchKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    
    public CustomerProduct getRecord(String key) {
        for (CustomerProduct record : records) {
            if (record.getSearchKey().equals(key)) {
                return record;
            }
        }
        return null;
    }

    
    public void insertRecord(CustomerProduct record) {
        if (!contains(record.getSearchKey())) {
            records.add(record);
        }
    }

    
    public void deleteRecord(String key) {
        records.removeIf(record -> record.getSearchKey().equals(key));
    }

    
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (CustomerProduct record : records) {
                writer.println(record.lineRepresentation());
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + filename);
            e.printStackTrace();
        }
    }
}
