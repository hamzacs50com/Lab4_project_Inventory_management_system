/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.mohamed;
import java.io.*;


public class EmployeeUser {
    private String employeeId;
    private String  name;
    private String  email;
    private String address;
    private String phoneNumber;
    
    public EmployeeUser(String employeeId, String name, String email, String address, String phoneNumber)
    {
        this.employeeId=employeeId;
        this.name=name;
        this.email=email;
        this.address=address;
        this.phoneNumber=phoneNumber;
        
    }

    EmployeeUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String lineRepresentation()
    {
        return employeeId +","+ name +","+ email +","+ address+"," +phoneNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getSearchKey()
    {
        return employeeId;
    }
    
    
    
    
    
}
