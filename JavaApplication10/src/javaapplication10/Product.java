/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication10;
/**
 *
 * @author apple
 */
public class Product {  
    private String  productID, productName, manufacturerName,supplierName;
    private int quantity;
    private float price;
    public Product(String productID, String productName, String manufacturerName, String supplierName, int quantity, float price){
        this.productID=productID;
        this.productName=productName;
        this.manufacturerName=manufacturerName;
        this.supplierName=supplierName;
        this.quantity=quantity;
        this.price=price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String lineRepresentation() {
        return productID + "," +productName + "," +manufacturerName + "," +supplierName + "," +quantity + "," +price;
    }
    public String getSearchKey() {
        return productID;
    }
}
