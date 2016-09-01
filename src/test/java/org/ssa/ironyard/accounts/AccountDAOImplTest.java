package org.ssa.ironyard.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

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
    Account travisAdamsCH;
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
        travisAdamsCH = new Account(travisAdams,Type.CHECKING, BigDecimal.valueOf(-700.00));
        
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

  
        assertTrue(dbMikeCH.deeplyEquals(readM));
        assertTrue(dbMikeCH.getId().equals(readM.getId()));
        assertTrue(dbMikeCH.getCustomer().equals(readM.getCustomer()));
        assertTrue(dbMikeCH.getType().equals(readM.getType()));
        assertTrue(dbMikeCH.getBalance().compareTo(readM.getBalance()) == 0);
        
        assertFalse(dbMikeCH.deeplyEquals(readT));
        assertFalse(dbMikeCH.getId().equals(readT.getId()));
        assertFalse(dbMikeCH.getCustomer().equals(readT.getCustomer()));
        assertFalse(dbMikeCH.getType().equals(readT.getType()));
        assertFalse(dbMikeCH.getBalance().compareTo(readT.getBalance()) == 0);
        
        assertTrue(dbMikeSA.deeplyEquals(readMS));
        assertFalse(dbMikeSA.deeplyEquals(readM));
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
    
    //@Test
    public void testDelete()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        assertTrue(dbMikeCH.deeplyEquals(accounts.read(dbMikeCH.getId())));
        
        accounts.delete(dbMikeCH.getId());
        assertTrue(accounts.read(dbMikeCH.getId()) == null);
        assertFalse(accounts.read(dbMikeSA.getId()) == null);
        
    }
    
    //@Test
    public void testUpdate()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account readMC = accounts.read(dbMikeCH.getId());
        
        dbMikeCH.setBalance(BigDecimal.valueOf(300.00));
        Account updateMC = accounts.update(dbMikeCH);
        Account readUpdateMC = accounts.read(dbMikeCH.getId());
        
        assertTrue(dbMikeCH.deeplyEquals(updateMC));
        assertFalse(dbMikeCH.deeplyEquals(readMC));
        
        assertTrue(readUpdateMC.getBalance().compareTo(dbMikeCH.balance)==0);
        assertTrue(readUpdateMC.getType() == dbMikeCH.getType());
        assertTrue(readUpdateMC.getId() == dbMikeCH.getId());
        assertTrue(readUpdateMC.getCustomer().equals(dbMikeCH.getCustomer()));
        
        
        
    }
    
    //@Test
    public void testRead()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account readMC = accounts.read(dbMikeCH.getId());
        Account readMS = accounts.read(dbMikeSA.getId());
        
        assertTrue(readMC.deeplyEquals(dbMikeCH));
        assertTrue(readMS.equals(dbMikeSA));
        
        assertFalse(readMC.deeplyEquals(dbMikeSA));
        assertFalse(readMS.deeplyEquals(dbMikeCH));
        
    }
    
    //@Test
    public void testReadUser()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account dbTravisSA = accounts.insert(travisAdamsSA);
        
        List<Account> readUser = accounts.readUser(dbMikeCH.getCustomer().getId());
        
        assertTrue(readUser.contains(dbMikeCH));
        assertTrue(readUser.contains(dbMikeSA));
        assertFalse(readUser.contains(dbTravisSA));
        
        
        
    }
    
    @Test
    public void testReadType()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account dbTravisSA = accounts.insert(travisAdamsSA);
        
        List<Account> readType = accounts.readType(dbMikeSA.getType());
        
        assertFalse(readType.contains(dbMikeCH));
        assertTrue(readType.contains(dbMikeSA));
        assertTrue(readType.contains(dbTravisSA));
    }
    
    @Test
    public void testUnderwater()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account dbTravisSA = accounts.insert(travisAdamsSA);
        Account dbTravisCH = accounts.insert(travisAdamsCH);
        
        List<Account> underwater = accounts.readUnderwater();
        assertTrue(underwater.contains(dbTravisCH));
        assertFalse(underwater.contains(dbTravisSA));
        assertFalse(underwater.contains(dbMikeCH));
        assertFalse(underwater.contains(dbMikeSA));
        
        dbMikeCH.setBalance(BigDecimal.valueOf(-100));
        accounts.update(dbMikeCH);
        
        List<Account> underwater2 = accounts.readUnderwater();
        assertTrue(underwater2.contains(dbTravisCH));
        assertFalse(underwater2.contains(dbTravisSA));
        assertTrue(underwater2.contains(dbMikeCH));
        assertFalse(underwater2.contains(dbMikeSA));
        
        
    }
    
    


}
