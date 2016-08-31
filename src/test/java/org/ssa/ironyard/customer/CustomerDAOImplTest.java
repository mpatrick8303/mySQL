package org.ssa.ironyard.customer;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CustomerDAOImplTest
{

    Customer johnDoe;
    Customer janeDoe;
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    CustomerDAO customers;

    @Before
    public void setup()
    {
        johnDoe = new Customer("john", "doe");
        janeDoe = new Customer("jane", "doe");
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        customers = new Customers(mysqlDataSource);
        customers.deleteAll();
        
    }

    @Test
    public void testInsert()
    {
        Customer dbJohn = new Customer();
        dbJohn = customers.insert(johnDoe);
        assertTrue(dbJohn.getFirstName().equals(johnDoe.getFirstName()));
        assertTrue(dbJohn.getLastName().equals(johnDoe.getLastName()));
        assertTrue(dbJohn.getId() > 0);
        
        assertTrue(dbJohn.equals(customers.read(dbJohn.getId())));
//        Customer check = new Customer(customers.read(dbJohn.getId()).getFirstName(),customers.read(dbJohn.getId()).getLastName());
//        check.setId(customers.read(dbJohn.getId()).getId());
//        
//        assertTrue(check.equals(dbJohn));
        
    }

    @Test
    public void testDelete()
    {
        Customer dbJohn = customers.insert(johnDoe);
        assertTrue(customers.delete(dbJohn));
        assertTrue(customers.read(dbJohn.getId()) == null);
        assertFalse(customers.delete(dbJohn));
        
    }

    @Test
    public void testUpdate()
    {
        Customer dbJohn = customers.insert(johnDoe);
        dbJohn.setFirstName("Jane");
        dbJohn = customers.update(dbJohn);
        assertTrue(dbJohn.getFirstName().equals("Jane"));
        assertTrue(customers.read(dbJohn.getId()).getFirstName().equals("Jane"));
        assertTrue(customers.read(dbJohn.getId()).equals(dbJohn));
    }
    
    @Test
    public void testRead()
    {
        Customer dbJohn = customers.insert(johnDoe);
        
        assertTrue(dbJohn.equals(customers.read(dbJohn.getId())));
        
        customers.delete(dbJohn);
        assertTrue(customers.read(dbJohn.getId()) == null);
    }

    @Test
    public void testReadAll()
    {
        Customer dbJohn = customers.insert(johnDoe);
        Customer dbJane = customers.insert(janeDoe);
        List<Customer> customerList = customers.readAll();
        assertTrue(customerList.contains(dbJohn));
        assertTrue(customerList.contains(dbJane));
    }

    @Test
    public void testReadFirstName()
    {
        Customer dbJohn = customers.insert(johnDoe);
        Customer dbJane = customers.insert(janeDoe);
        List<Customer> customerList = customers.readFirstName("Jane");
        assertFalse(customerList.contains(dbJohn));
        assertTrue(customerList.contains(dbJane));
    }

    @Test
    public void testReadLastName()
    {
        Customer dbJohn = customers.insert(johnDoe);
        Customer dbJane = customers.insert(janeDoe);
        dbJane.setLastName("Jones");
        customers.update(dbJane);
        List<Customer> customerList = customers.readLastName("Jones");
        assertFalse(customerList.contains(dbJohn));
        assertTrue(customerList.contains(dbJane));

    }

    @Test
    public void testDeleteAll()
    {
        Customer dbJohn = customers.insert(johnDoe);
        Customer dbJane = customers.insert(janeDoe);
        
        customers.deleteAll();
        assertTrue(customers.read(dbJohn.getId()) == null);
        assertTrue(customers.read(dbJane.getId()) == null);
        
    }
    
    @After
    public void tearDown()
    {
        customers.close();
    }

}
