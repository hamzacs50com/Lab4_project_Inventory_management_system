package Refactored.roles;

import Refactored.database.EmployeeUserDatabase;
import Refactored.records.EmployeeUser;
import java.util.ArrayList;

public class AdminRole {
    private EmployeeUserDatabase database;

    public AdminRole() {
        this.database = new EmployeeUserDatabase("Employees.txt");
    }

    public void addEmployee(String employeeId, String name, String email, String address, String phoneNumber) {
        EmployeeUser newEmployee = new EmployeeUser(employeeId, name, email, address, phoneNumber);
        database.insertRecord(newEmployee);
    }

    public EmployeeUser[] getListOfEmployees() {
        ArrayList<EmployeeUser> records = database.returnAllRecords();
        return records.toArray(new EmployeeUser[0]);
    }

    public void removeEmployee(String key) {
        database.deleteRecord(key);
    }

    public void logout() {
        database.saveToFile();
    }
}
