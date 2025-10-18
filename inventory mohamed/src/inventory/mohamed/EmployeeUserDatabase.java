/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.mohamed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class EmployeeUserDatabase {
    
    private  String filename;
    private  ArrayList<EmployeeUser> records;
    
    public EmployeeUserDatabase(String filename)
    {
        this.filename=filename;
        this.records= new ArrayList<>();
        File employees =new File(filename);
               
    }
    
    public void readFromFile() throws FileNotFoundException
    {
        File employees= new File(filename);
        Scanner scan= new Scanner(employees);
        
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 5) {
                EmployeeUser user = new EmployeeUser( parts[0], parts[1], parts[2], parts[3], parts[4]);
                records.add(user);
            }
        }
        scan.close();


    }
    
    public EmployeeUser createRecordFrom(String line)
    {
            String[] parts = line.split(",");
            EmployeeUser user =null;
            if (parts.length == 5) {
                user = new EmployeeUser( parts[0], parts[1], parts[2], parts[3], parts[4]);
                //records.add(user);
            }
        return user;
    }
    
    public ArrayList<EmployeeUser> returnAllRecords()
    {
        return records;
    }
    
    public boolean contains(String key )   // emp. ID
    {
        ArrayList <EmployeeUser> search = records;
        
        for(int i=0;i<search.size();i++)
        {
            if(search.get(i).getEmployeeId().equals(key))
                return true;
        }
        return false;
    }
    
    public EmployeeUser getRecord(String key)
    {
        for(int i=0;i<records.size();i++)
        {
            if(records.get(i).getEmployeeId().equals(key))
                return records.get(i);
        }
        return null ;
        
    }
    
    public void insertRecord(EmployeeUser record)
    {
        if(!contains(record.getEmployeeId()))
           records.add(record);
    }
    
    public void deleteRecord(String key)
    {
        if( getRecord(key)==null) return;
        if(contains(key))
       records.remove(getRecord(key));
    }
    
    public void saveToFile() throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter(filename);
        
        for(int i=0;i<records.size();i++)
        {
            EmployeeUser  user =records.get(i);
            String line = user.getEmployeeId() + "," +
                          user.getName() + "," +
                          user.getEmail() + "," +
                          user.getPhoneNumber() + "," +
                          user.getAddress();
            writer.println(line);


        }
        writer.close();

    }
    
    
    
}
