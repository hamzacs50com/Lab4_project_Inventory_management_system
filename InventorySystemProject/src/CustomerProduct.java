import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class CustomerProduct {
    private String customerSSN;
    private String productID;
    private boolean paid;
    private LocalDate purchaseDate;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public CustomerProduct(String customerSSN, String productID, LocalDate purchaseDate) {
        this.customerSSN = customerSSN;
        this.productID = productID;
        this.purchaseDate = purchaseDate;
        this.paid = false;
    }

    public String getCustomerSSN() {
        return customerSSN;
    }

    public String getProductID() {
        return productID;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public String lineRepresentation() {
        return customerSSN + "," + productID + "," + purchaseDate.format(DATE_FORMATTER) + "," + paid;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getSearchKey() {
        return customerSSN + "," + productID + "," + purchaseDate.format(DATE_FORMATTER);
    }
}