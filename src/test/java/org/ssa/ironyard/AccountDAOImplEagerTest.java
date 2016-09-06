package org.ssa.ironyard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.AccountDAOImpl;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.CustomerDAO;
import org.ssa.ironyard.customer.CustomerDAOImpl;

import com.mysql.cj.jdbc.MysqlDataSource;

public class AccountDAOImplEagerTest
{
    Customer mikePatrick;
    Customer travisAdams;
    Account mikePatrickCH;
    Account mikePatrickSA;
    Account travisAdamsSA;
    Account travisAdamsCH;
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    AccountDAOImpl accounts;
    CustomerDAO cus;
    
    @Before
    public void setup()
    {
        
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        accounts = new AccountDAOImpl(mysqlDataSource);
        cus = new CustomerDAOImpl(mysqlDataSource);
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
    
    @Test
    public void testIsLoaded()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account readMC = accounts.eagerRead(dbMikeCH.getId());
        Account readMS = accounts.read(dbMikeSA.getId());
        
        assertTrue(dbMikeCH.isLoaded());
        assertTrue(dbMikeSA.isLoaded());
        assertTrue(readMC.isLoaded());
        assertTrue(readMS.isLoaded());
        assertTrue(readMC.getCustomer().isLoaded());
        assertFalse(readMS.getCustomer().isLoaded());
    }
    
    @Test
    public void testEagerRead()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account readMC = accounts.eagerRead(dbMikeCH.getId());
        Account readMS = accounts.read(dbMikeSA.getId());
        
        assertNotNull(readMC.getCustomer().getFirstName());
        assertNotNull(readMC.getCustomer().getLastName());
        assertNull(readMS.getCustomer().getFirstName());

        assertFalse(mikePatrickCH.isLoaded());
        assertFalse(mikePatrickSA.isLoaded());
        
    }
    
    @Test
    public void testEagerReadUser()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account dbTravisCH = accounts.insert(travisAdamsCH);
        
        
        List<Account> byUser = accounts.readEagerUser(mikePatrick.getId());
        assertTrue(byUser.contains(dbMikeCH));       
        assertTrue(byUser.contains(dbMikeSA));
        assertFalse(byUser.contains(dbTravisCH));
        
        assertNotNull(byUser.get(0).getCustomer().getFirstName());
        assertNotNull(byUser.get(0).getCustomer().getLastName());
        assertNotNull(byUser.get(0).getCustomer().getId()); 
        assertNotNull(byUser.get(1).getCustomer().getFirstName());
        assertNotNull(byUser.get(1).getCustomer().getLastName());
        assertNotNull(byUser.get(1).getCustomer().getId()); 
         
    }
    
    @Test
    public void testReadEagerType()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account dbTravisCH = accounts.insert(travisAdamsCH);
        
        List<Account> byType = accounts.readEagerType(mikePatrickCH.getType());
        assertTrue(byType.contains(dbMikeCH));
        assertTrue(byType.contains(dbTravisCH));
        assertFalse(byType.contains(dbMikeSA));
        
        assertNotNull(byType.get(0).getCustomer().getFirstName());
        assertNotNull(byType.get(0).getCustomer().getLastName());
        assertNotNull(byType.get(0).getCustomer().getId());
        assertNotNull(byType.get(1).getCustomer().getFirstName());
        assertNotNull(byType.get(1).getCustomer().getLastName());
        assertNotNull(byType.get(1).getCustomer().getId()); 

    }
    
    @Test
    public void testReadEagerUnderwater()
    {
        Account dbMikeCH = accounts.insert(mikePatrickCH);
        Account dbMikeSA = accounts.insert(mikePatrickSA);
        Account dbTravisCH = accounts.insert(travisAdamsCH);
        
        List<Account> lessThanZero = accounts.readEagerUnderwater();
        assertTrue(lessThanZero.contains(dbTravisCH));
        
        assertNotNull(lessThanZero.get(0).getCustomer().getFirstName());
        assertNotNull(lessThanZero.get(0).getCustomer().getLastName());
        assertNotNull(lessThanZero.get(0).getCustomer().getId());
        
        assertFalse(lessThanZero.contains(dbMikeCH));
        assertFalse(lessThanZero.contains(dbMikeSA));
        
 
    }
}
