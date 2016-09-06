package org.ssa.ironyard.bank;

import java.math.BigDecimal;

import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.AccountDAOImpl;
import org.ssa.ironyard.customer.CustomerDAOImpl;

import com.mysql.cj.jdbc.MysqlDataSource;

public class BankServicesImpl implements BankServices
{
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root&" + "useServerPrepStmts=true";
    AccountDAOImpl accounts;
    CustomerDAOImpl customers;
    
    public BankServicesImpl()
    {
        
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        accounts = new AccountDAOImpl(mysqlDataSource);
        customers = new CustomerDAOImpl(mysqlDataSource);
        
    }

    @Override
    public Account Withdrawl(int account, BigDecimal amount)
    {
        Account a = accounts.read(account);
        
        BigDecimal wBalance = a.getBalance().add(amount);
        
        Account wA = new Account(a.getId(),a.getCustomer(),a.getType(),wBalance);
        return accounts.update(wA);
    }

    @Override
    public Account Deposit(int account, BigDecimal amount)
    {
        Account a = accounts.read(account);
        
        BigDecimal wBalance = a.getBalance().subtract(amount);
        
        Account wA = new Account(a.getId(),a.getCustomer(),a.getType(),wBalance);
        return accounts.update(wA);
    }

    @Override
    public Account Transfer(int accountOne, int accountTwo, BigDecimal amount)
    {
        Account a = accounts.read(accountOne);
        Account b = accounts.read(accountTwo);
        
        BigDecimal aBalance = a.getBalance().subtract(amount);
        BigDecimal bBalance = b.getBalance().add(amount);

        
        Account wA = new Account(a.getId(),a.getCustomer(),a.getType(),aBalance);
        Account wB = new Account(b.getId(),b.getCustomer(),b.getType(),bBalance);
        accounts.update(wA);
        return accounts.update(wB);
    }
    
    public void deleteAll()
    {
        customers.deleteAll();
        accounts.clear();
    }

}
