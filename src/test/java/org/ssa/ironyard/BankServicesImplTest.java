package org.ssa.ironyard;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.AccountDAOImpl;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.bank.BankServicesImpl;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.CustomerDAO;
import org.ssa.ironyard.customer.CustomerDAOImpl;

import com.mysql.cj.jdbc.MysqlDataSource;


public class BankServicesImplTest
{

    Customer mikePatrick;
    Customer travisAdams;
    Account mikePatrickCH;
    Account mikePatrickSA;
    Account travisAdamsSA;
    Account travisAdamsCH;
    Account mikePatrickCHIns;
    Account mikePatrickSAIns;
    Account travisAdamsSAIns;
    Account travisAdamsCHIns;
    
    
    AccountDAOImpl accounts;
    CustomerDAO cus;

    BankServicesImpl bankS = new BankServicesImpl();
    
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    
    @Before
    public void setup()
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        accounts = new AccountDAOImpl(mysqlDataSource);
        cus = new CustomerDAOImpl(mysqlDataSource);
        
        bankS.deleteAll();
        
        mikePatrick = new Customer("Mike","Patrick");
        travisAdams = new Customer("Travis","Adams");
        mikePatrick = cus.insert(mikePatrick);
        travisAdams = cus.insert(travisAdams);
        
        mikePatrickCH = new Account(mikePatrick, Type.CHECKING, BigDecimal.valueOf(1000.00));
        mikePatrickSA = new Account(mikePatrick, Type.SAVINGS, BigDecimal.valueOf(400.00));
        travisAdamsCH = new Account(travisAdams,Type.CHECKING, BigDecimal.valueOf(-700.00));
        travisAdamsSA = new Account(travisAdams,Type.SAVINGS, BigDecimal.valueOf(500.00));
        
        
        mikePatrickCHIns = accounts.insert(mikePatrickCH);
        mikePatrickSAIns = accounts.insert(mikePatrickSA);
        travisAdamsCHIns = accounts.insert(travisAdamsCH);
        travisAdamsSAIns = accounts.insert(travisAdamsSA);
        
    }
    
    //@Test
    public void testWithdrawl()
    {
        BigDecimal amount = BigDecimal.valueOf(200);
        Account a = accounts.read(mikePatrickCHIns.getId());
        
        Account wA = bankS.Withdrawl(a.getId(), amount);

        
        assertTrue(a.getBalance().add(amount).equals(wA.getBalance()));
    }
    
    //@Test
    public void testDeposit()
    {
        BigDecimal amount = BigDecimal.valueOf(200);
        Account a = accounts.read(travisAdamsCHIns.getId());
        
        Account wA = bankS.Deposit(a.getId(), amount);

        
        assertTrue(a.getBalance().subtract(amount).equals(wA.getBalance()));
        
    }
    
    //@Test
    public void testTransfer()
    {
        BigDecimal amount = BigDecimal.valueOf(200.00);
        Account a = accounts.read(mikePatrickSAIns.getId());
        Account b = accounts.read(travisAdamsSAIns.getId());
        
        Account tR = bankS.Transfer(a.getId(),b.getId(),amount);
        
        
        
        assertTrue(b.getBalance().add(amount).equals(tR.getBalance()));
    }
    
    @Test
    public void testDeleteAll()
    {
        bankS.deleteAll();
    }

}
