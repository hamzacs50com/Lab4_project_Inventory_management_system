import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ProductDatabase {
    private ArrayList<Product> records;
    private String filename;

    public ProductDatabase(String filename) {
        records = new ArrayList<>();
        this.filename = filename;
        readFromFile(); 
    }

    public void readFromFile() {
        records.clear(); 
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()){
                    records.add(createRecordFrom(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename + ". A new file will be created on save.");
        }
    }

    public Product createRecordFrom(String line) {
        String[] item = line.split(",");
        return new Product(item[0], item[1], item[2], item[3], Integer.parseInt(item[4]), Float.parseFloat(item[5]));
    }

    public ArrayList<Product> returnAllRecords() {
        return records;
    }

    public boolean contains(String key) {
        for (Product p : records) {
            if (p.getSearchKey().equals(key))
                return true;
        }
        return false;
    }

    public Product getRecord(String key) {
        for (Product p : records) {
            if (p.getSearchKey().equals(key))
                return p;
        }
        return null;
    }

    public void insertRecord(Product record) {
        if (!contains(record.getSearchKey())) {
            records.add(record);
        }
    }

    public void deleteRecord(String key) {
        records.removeIf(p -> p.getSearchKey().equals(key));
    }

    public void saveToFile() {
        try (PrintWriter printer = new PrintWriter(new FileWriter(filename))) {
            for (Product p : records) {
                printer.println(p.lineRepresentation());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
            e.printStackTrace();
        }
    }
}