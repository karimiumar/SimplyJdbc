package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerQueryTest {
    
    //@AfterAll
    public static void clean() {
        OrderDeleteService ods = new OrderDeleteService();
        ods.delete().execute();
        CustomerDeleteService cds = new CustomerDeleteService();
        cds.delete().execute();
    }
    
    //@Test
    @org.junit.jupiter.api.Order(1)
    public void saveCustomerOrders() {
        CustomerSaveService css = new CustomerSaveService();
        OrderSaveService oss = new OrderSaveService();
        Customer c1 = new Customer("Ashley", "Stevens", "LA", "US");
        Customer c2 = new Customer("Jennifer", "Stevens", "LA", "US");
        Customer c3 = new Customer("Jennifer", "Aniston", "Chicago", "US");
        Customer c4 = new Customer("Brian", "Adams", "New Orleans", "US");
        c1 = css.save(c1).execute();
        c2 = css.save(c2).execute();
        c3 = css.save(c3).execute();
        c4 = css.save(c4).execute();
        
        Order o1 = new Order(LocalDateTime.now(), 12, c1.getId(), 4500.00);
        Order o2 = new Order(LocalDateTime.now(), 12, c1.getId(), 1500.00);
        Order o3 = new Order(LocalDateTime.now(), 12, c1.getId(), 500.00);
        
        Order o4 = new Order(LocalDateTime.now(), 13, c3.getId(), 500.00);
        Order o5 = new Order(LocalDateTime.now(), 13, c3.getId(), 1200.00);
        Order o6 = new Order(LocalDateTime.now(), 13, c3.getId(), 500.00);
        
        Order o7 = new Order(LocalDateTime.now(), 23, c4.getId(), 1100.00);
        Order o8 = new Order(LocalDateTime.now(), 33, c2.getId(), 700.00);
        oss.save(o1).execute();
        oss.save(o2).execute();
        oss.save(o3).execute();
        oss.save(o4).execute();
        oss.save(o5).execute();
        oss.save(o6).execute();
        oss.save(o7).execute();
        oss.save(o8).execute();
    }

    /*
     * CustomerOrderQueryService.totalAmtOrderedByEachCustomerUsingInnerQuery()
     * The sum of total_amount is grouped by first_name, last_name and ordered by sum(total_amount) in descending order
     * The output for the saveCustomerOrders() above should be as below:
     * ________________________________________________
     * CUSTOMER_TOTALS      first_name      last_name
     * ________________________________________________
     *      6500.0	      Ashley         Stevens
     *      2200.0	      Jennifer       Aniston
     *      1100.0	      Brian          Adams
     *      700.0	      Jennifer       Stevens
     * ________________________________________________
     *
     * SELECT * FROM ex.customer 
     *  LEFT JOIN 
     *  (
     *      SELECT customer_id, SUM(total_amount) AS TOTAL_AMOUNT  
     *      FROM ex.orders GROUP BY customer_id 
     *  ) AS CUSTOMER_TOTALS  ON id=CUSTOMER_TOTALS.CUSTOMER_ID ORDER BY  CUSTOMER_TOTALS.TOTAL_AMOUNT desc
     */
    @Test
    @org.junit.jupiter.api.Order(1)
    public void findTotalAmtOrderedByEachCustomer() {
        CustomerQueryService coqs = new CustomerQueryService(JdbcUtilService.getConnection());
        List<Customer> customerOrders = coqs.findTotalAmtOrderedByEachCustomer();
        Assertions.assertFalse(customerOrders.isEmpty());
        Assertions.assertTrue(customerOrders.size() == 4); // the size should be 4 for the data saveCustomerOrders();
        
        Customer c0 = customerOrders.get(0);
        Customer ashleyStevens = new Customer("Ashley", "Stevens", "", "");
        Assertions.assertEquals(ashleyStevens, c0);
        ashleyStevens.setTotalAmount(6500.00);
        Assertions.assertEquals(ashleyStevens.getTotalAmount(), c0.getTotalAmount());
        
        Customer c1 = customerOrders.get(1);
        Customer jenniferAniston = new Customer("Jennifer", "Aniston", "", "");
        jenniferAniston.setTotalAmount(2200);
        Assertions.assertEquals(jenniferAniston, c1);
        Assertions.assertEquals(jenniferAniston.getTotalAmount(), c1.getTotalAmount());
        
        Customer c2 = customerOrders.get(2);
        Customer brianAdams = new Customer("Brian", "Adams", "", "");
        brianAdams.setTotalAmount(1100);
        Assertions.assertEquals(brianAdams, c2);
        Assertions.assertEquals(brianAdams.getTotalAmount(), c2.getTotalAmount());
        
        Customer c3 = customerOrders.get(3);
        Customer jenniferStevens = new Customer("Jennifer", "Stevens", "", "");
        jenniferStevens.setTotalAmount(700);
        Assertions.assertEquals(jenniferStevens, c3);
        Assertions.assertEquals(jenniferStevens.getTotalAmount(), c3.getTotalAmount());
    }
}
