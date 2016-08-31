package org.ssa.ironyard.accounts;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.CustomerDAO;
import org.ssa.ironyard.customer.Customers;

import com.mysql.cj.jdbc.MysqlDataSource;



public class AccountDAOImplTest
{
    Customer mikePatrick;
    Customer travisAdams;
    Account mikePatrickCH;
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
        
        mikePatrickCH = new Account(mikePatrick.getId(),"CH",1000.00f);
        travisAdamsSA = new Account(travisAdams.getId(),"SA",500.00f);
        
        
        
        
    }
    
    @Test
    public void testInsert()
    {
        DomainObject dbMikeCH = new Account();
        dbMikeCH = accounts.insert(mikePatrickCH);
        
        
        
    }
}
