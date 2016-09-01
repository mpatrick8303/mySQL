package org.ssa.ironyard.other;

    import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.accounts.AccountDAO;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.Customers;

import com.mysql.cj.jdbc.MysqlDataSource;

public class AndyTests
{
        Account positiveChecking;
        Account negativeSavings;
        static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root" + "&useServerPrepStmts=true";

        AccountDAO accounts;
        Customers customers;
        int custID;

        @Before
        public void setup() throws SQLException 
        {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(URL);
            accounts = new AccountDAO(mysqlDataSource);
            customers = new Customers(mysqlDataSource);
            Customer customer = customers.insert(new Customer("john", "doe"));
            positiveChecking = new Account(customer, Type.CHECKING, BigDecimal.valueOf(1000));
            negativeSavings = new Account(customer, Type.SAVINGS, BigDecimal.valueOf(-1000));
            accounts.clear();
        }

        @Test
        public void testRead() 
        {
            Account dbPositiveChecking = accounts.insert(positiveChecking);
            assertTrue(dbPositiveChecking.getId() == accounts.read(dbPositiveChecking.getId()).getId());
            assertTrue(dbPositiveChecking.getCustomer().getId() == accounts.read(dbPositiveChecking.getId()).getCustomer().getId());
            assertTrue(dbPositiveChecking.getBalance().compareTo(accounts.read(dbPositiveChecking.getId()).getBalance()) == 0);
            assertTrue(dbPositiveChecking.getType().equals(accounts.read(dbPositiveChecking.getId()).getType()));
            accounts.delete(dbPositiveChecking.getId());
            assertTrue(accounts.read(dbPositiveChecking.getId()) == null);
        }

        @Test
        public void testInsert() 
        {
            Account dbPositiveChecking = new Account();
            dbPositiveChecking = accounts.insert(positiveChecking);
            assertTrue(dbPositiveChecking.getId() == accounts.read(dbPositiveChecking.getId()).getId());
            assertTrue(dbPositiveChecking.getCustomer().getId() == accounts.read(dbPositiveChecking.getId()).getCustomer().getId());
            assertTrue(dbPositiveChecking.getBalance().equals(positiveChecking.getBalance()));
            assertTrue(dbPositiveChecking.getType().equals(positiveChecking.getType()));
            assertTrue(dbPositiveChecking.getId() != 0);
        }

        @Test
        public void testDelete() 
        {
            Account dbPositiveChecking = accounts.insert(positiveChecking);
            assertTrue(accounts.delete(dbPositiveChecking.getId()));
            assertTrue(accounts.read(dbPositiveChecking.getId()) == null);
            assertFalse(accounts.delete(dbPositiveChecking.getId()));
        }

        @Test
        public void testUpdate() 
        {
            Account dbPositiveChecking = accounts.insert(positiveChecking);
            dbPositiveChecking.setBalance(BigDecimal.valueOf(500.00));
            dbPositiveChecking = accounts.update(dbPositiveChecking);
            assertTrue(dbPositiveChecking.getBalance().compareTo(BigDecimal.valueOf(500.00)) == 0);
            assertTrue(accounts.read(dbPositiveChecking.getId()).getBalance().compareTo(BigDecimal.valueOf(500.00)) == 0);
        }

        @Test
        public void testClear() 
        {
            accounts.insert(positiveChecking);
            accounts.insert(negativeSavings);
            assertTrue(accounts.clear() == 2);
        }

        @Test
        public void testReadUser() 
        {
            int user = accounts.insert(positiveChecking).getCustomer().getId();
            accounts.insert(negativeSavings);
            assertTrue(accounts.readUser(user).size() == 2);
        }

        @Test
        public void testReadUnderwater() 
        {
            assertTrue(accounts.readUnderwater().size() == 0);
            accounts.insert(negativeSavings);
            assertTrue(accounts.readUnderwater().size() == 1);
            accounts.insert(positiveChecking);
            assertTrue(accounts.readUnderwater().size() == 1);
        }
        
        @Test
        public void testReadType()
        {
            assertTrue(accounts.readType(Type.CHECKING).size() == 0);
            assertTrue(accounts.readType(Type.SAVINGS).size() == 0);
            accounts.insert(negativeSavings);
            assertTrue(accounts.readType(Type.CHECKING).size() == 0);
            assertTrue(accounts.readType(Type.SAVINGS).size() == 1); 
            accounts.insert(positiveChecking);
            assertTrue(accounts.readType(Type.CHECKING).size() == 1);
            assertTrue(accounts.readType(Type.SAVINGS).size() == 1);
        }

        @After
        public void teardown() {
        // customers.clear();
        }
    

}
