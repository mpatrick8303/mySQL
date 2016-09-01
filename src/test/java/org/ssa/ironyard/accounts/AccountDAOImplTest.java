package org.ssa.ironyard.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.CustomerDAO;
import org.ssa.ironyard.customer.Customers;

import com.mysql.cj.jdbc.MysqlDataSource;



public class AccountDAOImplTest
{
    Customer mikePatrick;
    Customer travisAdams;
    Account mikePatrickCH;
    Account mikePatrickSA;
    Account travisAdamsSA;
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    AccountDAO accounts;
    CustomerDAO cus;
    
    @Before
    public void setup()
    {
        
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        accounts = new AccountDAO(mysqlDataSource);
        cus = new Customers(mysqlDataSource);
        cus.deleteAll();
        
        
        mikePatrick = new Customer("Mike","Patrick");
        travisAdams = new Customer("Travis","Adams");
        mikePatrick = cus.insert(mikePatrick);
        travisAdams = cus.insert(travisAdams);
        
        mikePatrickCH = new Account(mikePatrick, Type.CHECKING, BigDecimal.valueOf(1000.00));
        mikePatrickSA = new Account(mikePatrick, Type.SAVINGS, BigDecimal.valueOf(400.00));
        travisAdamsSA = new Account(travisAdams,Type.SAVINGS, BigDecimal.valueOf(500.00));
        accounts.clear();
        
    }
    
    //@Test
    public void testInsert()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account dbTravisSA = accounts.insert(travisAdamsSA);
        Account readM = accounts.read(dbMikeCH.getId());
        Account readMS = accounts.read(dbMikeSA.getId());
        Account readT = accounts.read(dbTravisSA.getId());

  
        assertTrue(dbMikeCH.equals(readM));
        assertTrue(dbMikeCH.getId().equals(readM.getId()));
        assertTrue(dbMikeCH.getCustomer().equals(readM.getCustomer()));
        assertTrue(dbMikeCH.getType().equals(readM.getType()));
        assertTrue(dbMikeCH.getBalance().compareTo(readM.getBalance()) == 0);
        
        assertFalse(dbMikeCH.equals(readT));
        assertFalse(dbMikeCH.getId().equals(readT.getId()));
        assertFalse(dbMikeCH.getCustomer().equals(readT.getCustomer()));
        assertFalse(dbMikeCH.getType().equals(readT.getType()));
        assertFalse(dbMikeCH.getBalance().compareTo(readT.getBalance()) == 0);
        
        assertTrue(dbMikeSA.equals(readMS));
        assertFalse(dbMikeSA.equals(readM));
        assertTrue(dbMikeSA.getId().equals(readMS.getId()));
        assertFalse(dbMikeSA.getId().equals(readM.getId()));
        assertTrue(dbMikeSA.getCustomer().equals(readMS.getCustomer()));
        assertTrue(dbMikeSA.getCustomer().equals(readM.getCustomer()));
        assertTrue(dbMikeSA.getType().equals(readMS.getType()));
        assertFalse(dbMikeSA.getType().equals(readM.getType()));
        assertTrue(dbMikeSA.getType().equals(readT.getType()));
        assertTrue(dbMikeSA.getBalance().compareTo(readMS.getBalance()) ==0);
        assertFalse(dbMikeSA.getBalance().compareTo(readM.getBalance()) ==0);
    }
    
    @Test
    public void testDelete()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        assertTrue(dbMikeCH.equals(accounts.read(dbMikeCH.getId())));
        
        accounts.delete(dbMikeCH.getId());
        assertTrue(accounts.read(dbMikeCH.getId()) == null);
        assertFalse(accounts.read(dbMikeSA.getId()) == null);
        
    }


}
