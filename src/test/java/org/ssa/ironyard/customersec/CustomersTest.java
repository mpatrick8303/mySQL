package org.ssa.ironyard.customersec;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customersec.CustomersSec;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CustomersTest
{
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root";
    CustomersSec c1;    
    
    @Before
    public void setupDB() throws SQLException
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        c1 = new CustomersSec(mysqlDataSource);
        c1.deleteAll();
        
        
        
    }
    
    @Test
    public void testInsert() throws SQLException
    {
        Customer c = new Customer("Mike", "Patrick");
        c1.insert(c);
        
    }
    
    @Test
    public void testDelete() throws SQLException
    {
        Customer c = new Customer("Mike","Patrick");
        
        Customer cI = c1.insert(c);
        assertEquals("","Mike",cI.getFirstName());
        assertEquals("","Patrick",cI.getLastName());
        
        int id = c1.getID(c);
        c1.delete(c);

        assertFalse("",c1.find(id).next());
        
        Customer cF = new Customer("John","Doe");
        assertFalse(c1.delete(cF));
    }
    
    @Test
    public void testUpdate() throws SQLException
    {
        Customer c = new Customer("Mike","Patrick");
        
        Customer cI = c1.insert(c);
        assertEquals("","Mike",cI.getFirstName());
        assertEquals("","Patrick",cI.getLastName());
        
        Customer cU = c1.update(c, "Michael", "Pat");
        assertEquals("","Michael",cU.getFirstName());
        assertEquals("","Pat",cU.getLastName());
        
    }
    
    @Test
    public void testRead() throws SQLException
    {
        Customer c = new Customer("Mike","Patrick");
        c1.insert(c);
        
        int ID = c1.getID(c);
        //System.out.println(ID);
        
        Customer cR = c1.read(ID);
        assertEquals("","Mike",cR.getFirstName());
        assertEquals("","Patrick",cR.getLastName());
        
    }
    
    @After
    public void teardown() throws SQLException
    {
       c1.close();
    }

}
