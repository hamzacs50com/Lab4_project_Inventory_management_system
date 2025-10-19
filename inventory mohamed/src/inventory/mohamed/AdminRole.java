/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.mohamed;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class AdminRole 
{
    private EmployeeUserDatabase database;
    
    public AdminRole()
    {
         database = new EmployeeUserDatabase();
         
         
    }
    
    public void addEmployee(String employeeId, String name, String email, String address, String phoneNumber)
    {
        EmployeeUser user =new EmployeeUser(employeeId,  name,  email, address, phoneNumber);
        database.insertRecord(user);
    }
    
    public EmployeeUser[] getListOfEmployees()
    {
        ArrayList <EmployeeUser> list = database.returnAllRecords();
        return list.toArray(new EmployeeUser[0]);
    }
    
    public void removeEmployee(String key)
    {
        database.deleteRecord(key);
    }
    
    public void logout() throws FileNotFoundException 
    {
        database.saveToFile();
    }
    
    
}
