package Refactored.roles;

import Refactored.database.CustomerProductDatabase;
import Refactored.database.ProductDatabase;
import Refactored.records.CustomerProduct;
import Refactored.records.Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class EmployeeRole {
    private ProductDatabase productsDatabase;
    private CustomerProductDatabase customerProductDatabase;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public EmployeeRole() {
        this.productsDatabase = new ProductDatabase("Products.txt");
        this.customerProductDatabase = new CustomerProductDatabase("CustomersProducts.txt");
    }

    public void addProduct(String productID, String productName, String manufacturerName, String supplierName, int quantity, float price) {
        Product newProduct = new Product(productID, productName, manufacturerName, supplierName, quantity, price);
        productsDatabase.insertRecord(newProduct);
    }

    public void addProduct(String productID, String productName, String manufacturerName, String supplierName, int quantity) {
        Product newProduct = new Product(productID, productName, manufacturerName, supplierName, quantity, 0);
        productsDatabase.insertRecord(newProduct);
    }

    public Product[] getListOfProducts() {
        ArrayList<Product> records = productsDatabase.returnAllRecords();
        return records.toArray(new Product[0]);
    }

    public CustomerProduct[] getListOfPurchasingOperations() {
        ArrayList<CustomerProduct> records = customerProductDatabase.returnAllRecords();
        return records.toArray(new CustomerProduct[0]);
    }

    public boolean purchaseProduct(String customerSSN, String productID, LocalDate purchaseDate) {
        Product product = productsDatabase.getRecord(productID);
        if (product != null && product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            CustomerProduct purchase = new CustomerProduct(customerSSN, productID, purchaseDate);
            customerProductDatabase.insertRecord(purchase);
            return true;
        }
        return false;
    }

    public double returnProduct(String customerSSN, String productID, LocalDate purchaseDate, LocalDate returnDate) {
        if (returnDate.isBefore(purchaseDate)) return -1;
        if (!productsDatabase.contains(productID)) return -1;

        long daysBetween = ChronoUnit.DAYS.between(purchaseDate, returnDate);
        if (daysBetween > 14) return -1;

        String key = customerSSN + "," + productID + "," + purchaseDate.format(DATE_FORMATTER);
        if (!customerProductDatabase.contains(key)) return -1;

        Product product = productsDatabase.getRecord(productID);
        product.setQuantity(product.getQuantity() + 1);
        customerProductDatabase.deleteRecord(key);

        return product.getPrice();
    }

    public boolean applyPayment(String customerSSN, LocalDate purchaseDate) {

        // The key for CustomerProductDatabase requires SSN, ProductID, and Date[cite: 181, 182].
        // Since only SSN and Date are provided, we must iterate through the records list.
        ArrayList<CustomerProduct> records = customerProductDatabase.returnAllRecords(); // Get all records [cite: 194]

        for (CustomerProduct purchase : records) {

            // Step 1: Check if SSN and Date match
            if (purchase.getCustomerSSN().equals(customerSSN) &&
                    purchase.getPurchaseDate().isEqual(purchaseDate)) {
                if (!purchase.isPaid()) {
                    purchase.setPaid(true);
                    String key = purchase.getSearchKey();
                    customerProductDatabase.deleteRecord(key);
                    customerProductDatabase.insertRecord(purchase);
                    return true;
                }
                return false;
            }
        }
        return false;
    }


    public void logout() {
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
    }
}
