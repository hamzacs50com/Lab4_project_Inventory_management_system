import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeUserDatabase {

    private String filename;
    private ArrayList<EmployeeUser> records;

    public EmployeeUserDatabase(String filename) {
        this.filename = filename;
        this.records = new ArrayList<>();
        readFromFile();
    }


    public void readFromFile() {
        records.clear(); 
        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!line.trim().isEmpty()) {
                    records.add(createRecordFrom(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename + ". A new file will be created on save.");
        }
    }

    public EmployeeUser createRecordFrom(String line) {
        String[] parts = line.split(",");
        return new EmployeeUser(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public ArrayList<EmployeeUser> returnAllRecords() {
        return records;
    }

    public boolean contains(String key) {
        for (EmployeeUser user : records) {
            if (user.getSearchKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public EmployeeUser getRecord(String key) {
        for (EmployeeUser user : records) {
            if (user.getSearchKey().equals(key)) {
                return user;
            }
        }
        return null;
    }

    public void insertRecord(EmployeeUser record) {
        if (!contains(record.getSearchKey()))
            records.add(record);
    }

    public void deleteRecord(String key) {
        records.removeIf(user -> user.getSearchKey().equals(key));
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (EmployeeUser user : records) {
                writer.println(user.lineRepresentation());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
            e.printStackTrace();
        }
    }
}
