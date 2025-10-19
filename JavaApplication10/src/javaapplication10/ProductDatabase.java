/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication10;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 *
 * @author apple
 */
public class ProductDatabase {
    private ArrayList<Product> records;
    private String filename;
    public ProductDatabase (String filename){
        records = new ArrayList<>();
        this.filename=filename;
    }
    public void readFromFile() {   
        try {
        File f = new File(filename);
        Scanner scanner = new Scanner(f);        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            records.add(createRecordFrom(line));
        }
        scanner.close();    
        } catch (FileNotFoundException e) {
        System.out.println("File not found!");    
        }    
    }
    public Product createRecordFrom(String line){
        String[] item = line.split(",");
        Product p=new Product(item[0],item[1],item[2],item[3],Integer.parseInt(item[4]),Float.parseFloat(item[5]));
        return p;
    }
    public ArrayList<Product> returnAllRecords(){
        return records;
    }
    public boolean contains(String key){
        for(int i=0;i<records.size();i++){
            if(records.get(i).getSearchKey().equals(key))
                return true;
        }
        return false;
    }
    public Product getRecord(String key){
        for(int i=0;i<records.size();i++){
            if(records.get(i).getSearchKey().equals(key))
                return records.get(i);
        }
        return null;
    }
    public void insertRecord(Product record){
        records.add(record);
    }
    public void deleteRecord(String key){
        for(int i=0;i<records.size();i++){
            if(records.get(i).getSearchKey().equals(key)){
                records.remove(i);
                break;
            }
        }
    }
    public void saveToFile(){
        try {
            PrintWriter printer = new PrintWriter(new FileWriter(filename));
            for(int i=0;i<records.size();i++){
                printer.println(records.get(i).lineRepresentation());
            }
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
