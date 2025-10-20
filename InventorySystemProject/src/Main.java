import Refactored.roles.AdminRole;
import Refactored.roles.EmployeeRole;
import Refactored.records.CustomerProduct;
import Refactored.records.EmployeeUser;
import Refactored.records.Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    // Scanner is shared across the application
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        System.out.println("Welcome to the Inventory Management System!");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Employee");
            System.out.println("0. Exit");
            System.out.print("Choose your role: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    showAdminMenu();
                    break;
                case 2:
                    showEmployeeMenu();
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the menu for Admin operations.
     */
    private static void showAdminMenu() {
        AdminRole admin = new AdminRole();
        System.out.println("\n--- Admin Panel ---");

        while (true) {
            System.out.println("\n1. Add New Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. List All Employees");
            System.out.println("0. Logout (Return to Main Menu)");
            System.out.print("Select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addEmployee(admin);
                    break;
                case 2:
                    removeEmployee(admin);
                    break;
                case 3:
                    listAllEmployees(admin);
                    break;
                case 0:
                    admin.logout();
                    System.out.println("Admin changes saved. Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the menu for Employee operations.
     */
    private static void showEmployeeMenu() {
        EmployeeRole employee = new EmployeeRole();
        System.out.println("\n--- Employee Panel ---");

        while (true) {
            System.out.println("\n1. Add New Product");
            System.out.println("2. Purchase Product for Customer");
            System.out.println("3. Return Product for Customer");
            System.out.println("4. Apply Payment");
            System.out.println("5. List All Products");
            System.out.println("6. List All Purchase Records");
            System.out.println("0. Logout (Return to Main Menu)");
            System.out.print("Select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addProduct(employee);
                    break;
                case 2:
                    purchaseProduct(employee);
                    break;
                case 3:
                    returnProduct(employee);
                    break;
                case 4:
                    applyPayment(employee);
                    break;
                case 5:
                    listAllProducts(employee);
                    break;
                case 6:
                    listAllPurchases(employee);
                    break;
                case 0:
                    employee.logout();
                    System.out.println("Employee changes saved. Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // --- Admin Helper Methods ---

    private static void addEmployee(AdminRole admin) {
        System.out.println("\n- Enter New Employee Details -");
        System.out.print("Employee ID (e.g., E105): ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();
        admin.addEmployee(id, name, email, address, phone);
        System.out.println("Employee added successfully!");
    }

    private static void removeEmployee(AdminRole admin) {
        System.out.print("\nEnter Employee ID to remove: ");
        String id = scanner.nextLine();
        admin.removeEmployee(id);
        System.out.println("Attempted to remove employee. If ID existed, they have been removed.");
    }

    private static void listAllEmployees(AdminRole admin) {
        System.out.println("\n--- List of All Employees ---");
        EmployeeUser[] employees = admin.getListOfEmployees();
        if (employees.length == 0) {
            System.out.println("No employees found.");
        } else {
            for (EmployeeUser emp : employees) {
                System.out.println(emp.lineRepresentation());
            }
        }
    }

    // --- Employee Helper Methods ---

    private static void addProduct(EmployeeRole employee) {
        System.out.println("\n- Enter New Product Details -");
        System.out.print("Product ID (e.g., P005): ");
        String id = scanner.nextLine();
        System.out.print("Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Supplier: ");
        String supplier = scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = getIntInput();
        System.out.print("Price: ");
        float price = getFloatInput();
        employee.addProduct(id, name, manufacturer, supplier, quantity, price);
        System.out.println("Product added successfully!");
    }

    private static void purchaseProduct(EmployeeRole employee) {
        System.out.println("\n- Process New Purchase -");
        System.out.print("Enter Customer SSN: ");
        String ssn = scanner.nextLine();
        System.out.print("Enter Product ID to purchase: ");
        String productId = scanner.nextLine();
        LocalDate date = getDateInput("Enter Purchase Date (dd-MM-yyyy): ");

        if (employee.purchaseProduct(ssn, productId, date)) {
            System.out.println("Purchase processed successfully!");
        } else {
            System.out.println("Purchase failed. Product might be out of stock or ID is invalid.");
        }
    }

    private static void returnProduct(EmployeeRole employee) {
        System.out.println("\n- Process Product Return -");
        System.out.print("Enter Customer SSN: ");
        String ssn = scanner.nextLine();
        System.out.print("Enter Product ID to return: ");
        String productId = scanner.nextLine();
        LocalDate purchaseDate = getDateInput("Enter Original Purchase Date (dd-MM-yyyy): ");
        LocalDate returnDate = getDateInput("Enter Return Date (dd-MM-yyyy): ");

        double refund = employee.returnProduct(ssn, productId, purchaseDate, returnDate);
        if (refund != -1) {
            System.out.printf("Return successful! Refund amount: $%.2f%n", refund);
        } else {
            System.out.println("Return failed. Please check the details (SSN, Product ID, dates, 14-day limit).");
        }
    }

    private static void applyPayment(EmployeeRole employee) {
        System.out.println("\n- Apply Payment to Purchase -");
        System.out.print("Enter Customer SSN: ");
        String ssn = scanner.nextLine();
        System.out.print("Enter Product ID of the purchase: ");
        String productId = scanner.nextLine();
        LocalDate purchaseDate = getDateInput("Enter Purchase Date (dd-MM-yyyy): ");

        if (employee.applyPayment(ssn, productId, purchaseDate)) {
            System.out.println("Payment applied successfully!");
        } else {
            System.out.println("Failed to apply payment. Record not found or already paid.");
        }
    }

    private static void listAllProducts(EmployeeRole employee) {
        System.out.println("\n--- List of All Products ---");
        Product[] products = employee.getListOfProducts();
        if (products.length == 0) {
            System.out.println("No products found.");
        } else {
            for (Product p : products) {
                System.out.println(p.lineRepresentation());
            }
        }
    }

    private static void listAllPurchases(EmployeeRole employee) {
        System.out.println("\n--- List of All Purchase Records ---");
        CustomerProduct[] purchases = employee.getListOfPurchasingOperations();
        if (purchases.length == 0) {
            System.out.println("No purchase records found.");
        } else {
            for (CustomerProduct cp : purchases) {
                System.out.println(cp.lineRepresentation());
            }
        }
    }

    // --- Universal Input Helper Methods ---

    private static int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a whole number: ");
            }
        }
    }

    private static float getFloatInput() {
        while (true) {
            try {
                float value = Float.parseFloat(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number (e.g., 25.50): ");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateString = scanner.nextLine();
            try {
                return LocalDate.parse(dateString, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            }
        }
    }
}


