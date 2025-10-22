import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdminRole {
    private EmployeeUserDatabase database;

    public AdminRole() {
        database = new EmployeeUserDatabase("Employees.txt");
    }

    public void addEmployee(String employeeId, String name, String email, String address, String phoneNumber) {
        EmployeeUser user = new EmployeeUser(employeeId, name, email, address, phoneNumber);
        database.insertRecord(user);
    }

    public EmployeeUser[] getListOfEmployees() {
        ArrayList<EmployeeUser> list = database.returnAllRecords();
        return list.toArray(new EmployeeUser[0]);
    }

    public void removeEmployee(String key) {
        database.deleteRecord(key);
    }

    public void logout() {
        database.saveToFile();
    }
}
