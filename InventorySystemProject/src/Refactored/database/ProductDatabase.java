package Refactored.database;
import Refactored.records.Product;

public class ProductDatabase extends Database<Product> {

    public ProductDatabase(String filename) {
        super(filename);
    }

    @Override
    public Product createRecordFrom(String line) {
        String[] parts = line.split(",");
        return new Product(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Float.parseFloat(parts[5]));
    }
}