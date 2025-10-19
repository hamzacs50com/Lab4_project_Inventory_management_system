/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab4;

import static java.lang.constant.ConstantDescs.NULL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class CustomerProduct
{
   private String customerSSN;
   private String productID;
   private boolean paid;
   private LocalDate purchaseDate;
   
   public CustomerProduct(String customerSSN,String productID,LocalDate purchaseDate)
   {
       this.customerSSN=customerSSN;
       this.productID=productID;
       this.purchaseDate=purchaseDate;
       this.paid=false;
   }
   public String getCustomerSSN()
   {
       return customerSSN;
   }
   public String getProductID()
   {
       return productID;
   }
   public LocalDate getPurchaseDate()
   {
       return purchaseDate;
   }
   public String lineRepresentation()
   {
       return ""+customerSSN+","+productID+","+purchaseDate+","+paid;
   }
   public boolean isPaid()
   {
     return paid;
   }
   public void setPaid(boolean paid)
   {
       this.paid=paid;
   }
   public String getSearchKey()
   {
       DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
       return ""+customerSSN+","+productID+","+purchaseDate.format(formatter); 
   }
   
   
   
}

public class Lab4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        LocalDate date=LocalDate.parse("2025-10-15");
    CustomerProduct obj=new CustomerProduct("chips","99",date);
   String n= obj.getSearchKey();
   LocalDate dat=obj.getPurchaseDate();
   System.out.print(n);
   System.out.print(dat);

    
    
    }
    
}
