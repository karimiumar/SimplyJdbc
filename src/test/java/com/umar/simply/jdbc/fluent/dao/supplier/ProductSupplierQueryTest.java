package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.ddl.CreateTablesInH2db;
import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductSupplierQueryTest {
    @BeforeAll
    public static void setup() {
        CreateTablesInH2db.setup();
    }
    @AfterAll 
    public static void clean() {
        ProductDeleteService pds = new ProductDeleteService();
        SupplierDeleteService sds = new SupplierDeleteService();
        pds.delete().execute();
        sds.delete().execute();
    }
    
    @Test
    @org.junit.jupiter.api.Order(1)
    public void saveProductsAndSuppliers(){
        Product p1 = new Product("Smart Phones",1, 345.00, false, 2);
        Product p2 = new Product("Electric Kettles",1, 495.00, false, 2);
        Product p3 = new Product("Men's Leather Purse",2, 405.00, false, 2);
        Product p4 = new Product("Men's Leather Belt",2, 475.00, false, 2);
        Product p5 = new Product("Mobile Accessories",2, 245.00, false, 2);
        Product p6 = new Product("Men's Apparel",3, 65.00, false, 2);
        Product p7 = new Product("Men's Shoes",3, 235.00, false, 2);
        Product p8 = new Product("Women's Fashion",4, 95.00, false, 2);
        Product p9 = new Product("Women's Apparel",5, 4050.00, false, 2);
        Product p10 = new Product("Women's Purses",5, 5050.00, false, 2);
        Product p11 = new Product("Women's Shoes",2, 395.00, false, 2);
        Product p12 = new Product("Kitchenwares",5, 299.00, false, 2);
        Product p13 = new Product("Sofa",1, 799.00, false, 2);
        Product p14 = new Product("Beds",1, 899.00, false, 2);
        Product p15 = new Product("Geysers",3, 369.00, false, 2);
        Product p16 = new Product("Cutlery",4, 999.00, false, 2);
        Product p17 = new Product("Imported Cutlery",4, 195.00, false, 2);
        Product p18 = new Product("Watches",5, 569.00, false, 2);
        Product p19 = new Product("Home Decor",2, 800.00, false, 2);
        Product p20 = new Product("Books",1, 450.00, false, 2);
        ProductSaveService pss = new ProductSaveService();
        p1.setId(1);
        p2.setId(2);
        p3.setId(3);
        p4.setId(4);
        p5.setId(5);
        p6.setId(6);
        p7.setId(7);
        p8.setId(8);
        p9.setId(9);
        p10.setId(10);
        p11.setId(11);
        p12.setId(12);
        p13.setId(13);
        p14.setId(14);
        p15.setId(15);
        p16.setId(16);
        p17.setId(17);
        p18.setId(18);
        p19.setId(19);
        p20.setId(20);
        pss.save(p1).execute();
        pss.save(p2).execute();
        pss.save(p3).execute();
        pss.save(p4).execute();
        pss.save(p5).execute();
        pss.save(p6).execute();
        pss.save(p7).execute();
        pss.save(p8).execute();
        pss.save(p9).execute();
        pss.save(p10).execute();
        pss.save(p11).execute();
        pss.save(p12).execute();
        pss.save(p13).execute();
        pss.save(p14).execute();
        pss.save(p15).execute();
        pss.save(p16).execute();
        pss.save(p17).execute();
        pss.save(p18).execute();
        pss.save(p19).execute();
        pss.save(p20).execute();
        
        SupplierSaveService sss = new SupplierSaveService();
        Supplier s1 = new Supplier("Informatica Ltd.", "Ashley Silvia", "43, Plaza Park, SA");
        Supplier s2 = new Supplier("DHL Suppliers", "Marlyn D", "67, Plaza Park, SA");
        Supplier s3 = new Supplier("Walmart Supplies", "SS", "St Aubrine");
        Supplier s4 = new Supplier("Amazon Retro", "Twain", "2, Connaught Park, Delhi");
        Supplier s5 = new Supplier("Tesco", "Tessa", "3, Bell Street, Lao Paulo");
        s1.setId(1);
        s2.setId(2);
        s3.setId(3);
        s4.setId(4);
        s5.setId(5);
        sss.save(s1).execute();
        sss.save(s2).execute();
        sss.save(s3).execute();
        sss.save(s4).execute();
        sss.save(s5).execute();
    }
    
    @Test
    @org.junit.jupiter.api.Order(2)
    public void listProductsOfSupplier2() {
        ProductSupplierQueryService psqs = new ProductSupplierQueryService(JdbcUtilService.getConnection());
        Supplier s2 = new Supplier("", "", "");
        s2.setId(2);
        List<Product> productsOfSupplier2 = psqs.listProductsSuppliedBy(s2);
        Assertions.assertFalse(productsOfSupplier2.isEmpty());
        Assertions.assertTrue(productsOfSupplier2.size() == 5);
    }
    
    @Test
    @org.junit.jupiter.api.Order(3)
    public void listProductsOfSupplier5() {
        ProductSupplierQueryService psqs = new ProductSupplierQueryService(JdbcUtilService.getConnection());
        Supplier s5 = new Supplier("", "", "");
        s5.setId(5);
        List<Product> productsOfSupplier5 = psqs.listProductsSuppliedBy(s5);
        Assertions.assertFalse(productsOfSupplier5.isEmpty());
        Assertions.assertTrue(productsOfSupplier5.size() == 4);
    }
    
    @Test
    @org.junit.jupiter.api.Order(4)
    public void listSuppliersOrders(){
        ProductSupplierQueryService psqs = new ProductSupplierQueryService(JdbcUtilService.getConnection());
        Map<Supplier, List<Product>> productsSuppliersWise = psqs.listProductsSupplierwise();
        Assertions.assertFalse(productsSuppliersWise.isEmpty());
        Assertions.assertTrue(5 == productsSuppliersWise.size());
        productsSuppliersWise.entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() +"->" + entry.getValue());
        });
    }
}
