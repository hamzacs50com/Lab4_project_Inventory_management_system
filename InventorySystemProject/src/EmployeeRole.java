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
        // Check if product exists and is in stock
        if (product != null && product.getQuantity() > 0) {
            // Decrease quantity and record the purchase
            product.setQuantity(product.getQuantity() - 1);
            CustomerProduct purchase = new CustomerProduct(customerSSN, productID, purchaseDate);
            customerProductDatabase.insertRecord(purchase);
            return true;
        }
        return false; // Purchase failed
    }

    public double returnProduct(String customerSSN, String productID, LocalDate purchaseDate, LocalDate returnDate) {
        // Condition 1: returnDate must not be earlier than purchaseDate
        if (returnDate.isBefore(purchaseDate)) return -1;

        // Condition 2: The product must exist in the database
        if (!productsDatabase.contains(productID)) return -1;
        
        // Condition 4: The return must be within 14 days of purchase
        long daysBetween = ChronoUnit.DAYS.between(purchaseDate, returnDate);
        if (daysBetween > 14) return -1;

        // Condition 3: The specific purchase must exist
        String key = customerSSN + "," + productID + "," + purchaseDate.format(DATE_FORMATTER);
        if (!customerProductDatabase.contains(key)) return -1;

        // If all conditions pass, process the return
        Product product = productsDatabase.getRecord(productID);
        product.setQuantity(product.getQuantity() + 1); // Increment stock
        customerProductDatabase.deleteRecord(key); // Remove purchase record

        return product.getPrice(); // Return the price for the refund
    }

    public boolean applyPayment(String customerSSN, String productID, LocalDate purchaseDate) {
         String key = customerSSN + "," + productID + "," + purchaseDate.format(DATE_FORMATTER);
         CustomerProduct purchase = customerProductDatabase.getRecord(key);

         // Check if the purchase exists and is not already paid
         if (purchase != null && !purchase.isPaid()){
             purchase.setPaid(true); // Mark as paid
             return true;
         }
        return false; // Payment application failed
    }

    public void logout() {
        // Save all changes to the respective files
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
    }
}