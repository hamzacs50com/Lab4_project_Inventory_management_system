package Refactored.database;

import Refactored.records.Record;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

public abstract class Database<T extends Record> {
    protected ArrayList<T> records;
    protected String fileName;
    public Database(String fileName) {
        this.fileName = fileName;
        this.records = new ArrayList<>();
        readFromFile();
    }

    public abstract T createRecordFrom(String line);

    public void readFromFile() {
        records.clear();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    records.add(createRecordFrom(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName + ". A new file will be created upon saving.");
        }
    }

    public void saveToFile() {
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (T record : records) {
                writer.println(record.lineRepresentation());
            }
        }catch(IOException error) {
            System.err.println("Error saving to file: " + fileName);
            error.printStackTrace();
        }
    }

    public ArrayList<T> returnAllRecords() {
        return records;
    }

    public T getRecord(String key) {
        for (T record : records) {
            if (record.getSearchKey().equals(key)) {
                return record;
            }
        }
        return null;
    }

    public boolean contains(String key) {
        return getRecord(key) != null;
    }

    public void insertRecord(T record) {
        if (!contains(record.getSearchKey())) {
            records.add(record);
        }
    }

    public void deleteRecord(String key) {
        records.removeIf(record -> record.getSearchKey().equals(key));
    }
}
