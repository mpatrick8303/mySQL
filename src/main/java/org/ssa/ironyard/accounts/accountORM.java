package org.ssa.ironyard.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.ORM;
import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.CustomerDAOImpl;
import org.ssa.ironyard.customer.CustomerORM;



public interface AccountORM extends ORM<Account>
{
    @Override
    default String projection()
    {
        return "id, customer, type, balance";
    }
    
    default String projection2()
    {
        return "id, customer, type, balance, firstName, lastName";
    }
    
    @Override
    default String table()
    {
        return "accounts";
    }

    @Override
    default String prepareInsert()
    {
        return "Insert Into " + table() + "(customer, type, balance) Values(?,?,?)";
    }
    @Override
    default String prepareUpdate()
    {
        return "Update " + table() + " Set customer=?, type=?, balance=? Where id=?";
    }
    @Override
    default String prepareRead()
    {
        return "Select " + projection() + " From " + table() + " Where id=?";
    }
    
    @Override
    default String prepareDelete()
    {
        return "Delete From " + table() + " Where id=?";
    }
    @Override
    default String prepareDeleteAll()
    {
        return "Delete From " + table();
    }
    
    @Override
    default String readUser()
    {
        return "Select * From " + table() + " Where customer=?";
    }
    
    @Override
    default String readType()
    {
        return "Select * From " + table() + " Where type=?";
    }
    
    default String prepareReadLessThan()
    {
        return "Select * From " + table() + " Where balance<?";
    }
    
    @Override
    default Account map(ResultSet results) throws SQLException
    {
        
        Customer c = new Customer();
        c.setID(results.getInt("customer"));
        Account account = new Account(results.getInt("id"),c , Type.getInstance(results.getString("type")),results.getBigDecimal("balance"));
        account.setLoaded(true);

        return account;
    }
    
    default Account deepMap(ResultSet results) throws SQLException
    {
        
        Customer c = new Customer();
        c.setFirstName(results.getString("first"));
        c.setLastName(results.getString("last"));
        c.setID(results.getInt("id"));
        c.setLoaded(true);
        Account account = new Account(results.getInt("id"),c, Type.getInstance(results.getString("type")),results.getBigDecimal("balance"));
        account.setLoaded(true);
        return account;
    }
    
    @Override
    default String eagerRead()
    {
        
        CustomerORM cORM = new CustomerORM(){};
        String build = "Select * " + 
        "From " + table() +
        " Inner Join " + cORM.table() + 
        " On " + "accounts.customer = " + cORM.table() + ".id" +
        " Where " + table() + ".id=?";
        return build;
       
    }
    
    @Override
    default String eagerReadUser()
    {
        CustomerORM cORM = new CustomerORM(){};
        String build = "Select * " + 
        "From " + table() +
        " Inner Join " + cORM.table() + 
        " On " + "accounts.customer = " + cORM.table() + ".id" +
        " Where " + table() + ".customer=?";
        return build;
    }
    
    @Override
    default String eagerReadType()
    {
        CustomerORM cORM = new CustomerORM(){};
        String build = "Select * " + 
        "From " + table() +
        " Inner Join " + cORM.table() + 
        " On " + "accounts.customer = " + cORM.table() + ".id" +
        " Where " + table() + ".type=?";
        return build;
    }
    
    @Override
    default String eagerPrepareReadLessThan()
    {
        CustomerORM cORM = new CustomerORM(){};
        String build = "Select * " + 
        "From " + table() +
        " Inner Join " + cORM.table() + 
        " On " + "accounts.customer = " + cORM.table() + ".id" +
        " Where " + table() + ".balance<?";
        return build;
    }
    
    
    
    
}