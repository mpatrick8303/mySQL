package org.ssa.ironyard.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.accounts.AccountDAOImpl;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.CustomerDAOImpl;

import com.mysql.cj.jdbc.MysqlDataSource;

public class BanksAccountServicesImpl implements BankAccountServices
{
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    AccountDAOImpl accounts;
    CustomerDAOImpl customers;
    
    public BanksAccountServicesImpl()
    {
        
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        accounts = new AccountDAOImpl(mysqlDataSource);
        customers = new CustomerDAOImpl(mysqlDataSource);
        
    }
    
    @Override
    public Customer insertCustomer(String fName, String lName)
    {
        Customer c = new Customer(fName, lName);
        return customers.insert(c);
    }
    
    @Override
    public Account insterAccount(Customer customer, Type type, BigDecimal balance)
    {
        Account a = new Account(customer, type, balance);
        return accounts.insert(a);
    }


    @Override
    public boolean deleteAccount(int id)
    {
        Account a = accounts.read(id);
        
        if(a == null)
            return false;
        
        return accounts.delete(a.getId());
    }
    
    @Override
    public boolean deleteCustomer(int id)
    {
        Customer c = customers.read(id);
        
        if(c == null)
            return false;
        
        List<Account> accountsByUser = new ArrayList<>();
        accountsByUser = accounts.readUser(c.getId());
        
        
        for(int i = 0, i < accountsByUser.size(),i++)
        {
            
        }
    }

    @Override
    public Customer updateCustomer(Customer customer)
    {        
        if(customers.read(customer.getId()) == null)
            return new Customer();
        
        Customer c = new Customer(customer.getId(),customer.getFirstName(),customer.getLastName());
        
        return customers.update(c);
    }

    @Override
    public Account updateAccount(Account account)
    {
        if(accounts.read(account.getId()) == null)
            return new Account();
        
        Account a = new Account(account.getId(),account.getCustomer(),account.getType(),account.getBalance());
        
        return accounts.update(a);
    }
}
