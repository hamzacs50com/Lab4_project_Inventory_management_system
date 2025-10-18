/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.mohamed;

import java.io.FileNotFoundException;

public class InventoryMohamed {
    public static void main(String[] args) {
        // اسم الملف الذي يحتوي على بيانات الموظفين
        String filename = "employees.txt";

        // إنشاء قاعدة بيانات الموظفين
        EmployeeUserDatabase db = new EmployeeUserDatabase(filename);

        try {
            // قراءة البيانات من الملف
            db.readFromFile();
            System.out.println("تم تحميل البيانات من الملف.");

            // إضافة موظف جديد
            EmployeeUser newUser = new EmployeeUser("105", "سعيد", "saeed@example.com", "0100000000", "شارع النيل");
            db.insertRecord(newUser);
            System.out.println("تمت إضافة الموظف الجديد.");

            // حذف موظف برقم معين
            db.deleteRecord("102");
            System.out.println("تم حذف الموظف رقم 102 (إن وجد).");

            // حفظ البيانات في الملف
            db.saveToFile();
            System.out.println("تم حفظ البيانات في الملف.");

            // عرض كل السجلات
            System.out.println("قائمة الموظفين:");
            for (EmployeeUser user : db.returnAllRecords()) {
                System.out.println(user.getEmployeeId() + " - " + user.getName());
            }

        } catch (FileNotFoundException e) {
            System.out.println("حدث خطأ أثناء التعامل مع الملف: " + e.getMessage());
        }
    }
}
