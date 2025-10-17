/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.mohamed;

import java.io.File;
import java.io.FileNotFoundException;
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

    }
    
    public EmployeeUser createRecordFrom(String line)
    {
        String[] parts = line.split(",");
        EmployeeUser user=null;
            if (parts.length == 5)
            {
                 user = new EmployeeUser( parts[0], parts[1], parts[2], parts[3], parts[4]);
            }
            else return null;
            
            for (int i = 0; i < records.size(); i++)     // لسه هنظبطها عشان اتلخبط بين دالتين سيبها
            {
                if(AreEqual(user,records.get(i))){
                    return user;
                }
            }
            return null;

    }
    
    public boolean AreEqual(EmployeeUser a, EmployeeUser b)
    {
        return a.getAddress().equals(b.getAddress()) &&
                a.getEmail().equals(b.getEmail()) &&
                a.getEmployeeId().equals(b.getEmployeeId()) &&
                a.getName().equals(b.getName())&&
                a.getPhoneNumber().equals(b.getPhoneNumber());
    }
            
    
}
