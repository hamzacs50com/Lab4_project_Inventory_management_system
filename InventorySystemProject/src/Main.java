import java.time.LocalDate;

/**
 * Main class to run and test the Inventory Management System using the
 * provided sample txt files.
 *
 * This test driver simulates a sequence of admin and employee actions
 * based on the initial data in Employees.txt, Products.txt, and
 * CustomersProducts.txt.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("--- Inventory Management System Test ---");
        System.out.println("--- Simulating operations for October 2025 ---\n");

        // --- 1. Admin Role Demonstration ---
        System.out.println("--- Testing Admin Role ---");
        AdminRole admin = new AdminRole();

        System.out.println("\nInitial list of employees from Employees.txt:");
        printEmployees(admin.getListOfEmployees());

        System.out.println("\nAction: Removing employee E102 (Jane Doe)...");
        admin.removeEmployee("E102");

        System.out.println("Action: Adding new employee E104 (Alice Wonder)...");
        admin.addEmployee("E104", "Alice Wonder", "alice.w@example.com", "404 Rabbit Hole", "555-0104");

        System.out.println("\nFinal list of employees:");
        printEmployees(admin.getListOfEmployees());

        admin.logout();
        System.out.println("--> Admin changes have been saved to Employees.txt.");


        // --- 2. Employee Role Demonstration ---
        System.out.println("\n\n--- Testing Employee Role ---");
        EmployeeRole employee = new EmployeeRole();

        System.out.println("\nInitial list of products from Products.txt:");
        printProducts(employee.getListOfProducts());
        
        // --- Test Case 1: Successful Purchase ---
        System.out.println("\nAction: Customer SSN77788 purchases a Mouse (P002)...");
        boolean purchaseSuccess = employee.purchaseProduct("SSN77788", "P002", LocalDate.of(2025, 10, 20));
        System.out.println("  Result: Purchase successful = " + purchaseSuccess + ". Mouse quantity should decrease.");
        
        // --- Test Case 2: Successful Return (within 14 days) ---
        // From CustomersProducts.txt: SSN54321 bought P004 on 18-10-2025. Return date is 25-10-2025.
        System.out.println("\nAction: Customer SSN54321 returns a Monitor (P004) bought on 18-10-2025...");
        double refund = employee.returnProduct("SSN54321", "P004", LocalDate.of(2025, 10, 18), LocalDate.of(2025, 10, 25));
        if(refund != -1) {
            System.out.println("  Result: Return successful! Refund amount: $" + refund + ". Monitor quantity should increase.");
        } else {
            System.out.println("  Result: Return failed.");
        }

        // --- Test Case 3: Failed Return (more than 14 days) ---
        // From CustomersProducts.txt: SSN67890 bought P001 on 12-10-2025. Return date is 30-10-2025.
        System.out.println("\nAction: Customer SSN67890 attempts to return a Laptop (P001) bought on 12-10-2025...");
        double failedRefund = employee.returnProduct("SSN67890", "P001", LocalDate.of(2025, 10, 12), LocalDate.of(2025, 10, 30));
        if(failedRefund == -1) {
            System.out.println("  Result: Return failed as expected (more than 14 days have passed).");
        } else {
            System.out.println("  Result: Return succeeded unexpectedly.");
        }
        
        // --- Test Case 4: Apply Payment to an Unpaid Order ---
        // From CustomersProducts.txt: SSN12345 has an unpaid order for P002 from 15-10-2025.
        System.out.println("\nAction: Applying payment for unpaid order from customer SSN12345...");
        boolean paymentSuccess = employee.applyPayment("SSN12345", "P002", LocalDate.of(2025, 10, 15));
        System.out.println("  Result: Payment application successful = " + paymentSuccess + ". The record should now be marked as 'true'.");
        
        System.out.println("\nFinal list of products after all operations:");
        printProducts(employee.getListOfProducts());

        employee.logout();
        System.out.println("--> Employee changes have been saved to Products.txt and CustomersProducts.txt.");
        System.out.println("\n--- Test Complete ---");
    }

    // Helper method to print employees nicely
    private static void printEmployees(EmployeeUser[] employees) {
        if (employees.length == 0) {
            System.out.println("  -> No employees found.");
            return;
        }
        for (EmployeeUser employee : employees) {
            System.out.println("  -> " + employee.lineRepresentation());
        }
    }

    // Helper method to print products nicely
    private static void printProducts(Product[] products) {
        if (products.length == 0) {
            System.out.println("  -> No products found.");
            return;
        }
        for (Product product : products) {
            System.out.println("  -> " + product.lineRepresentation());
        }
    }
}

