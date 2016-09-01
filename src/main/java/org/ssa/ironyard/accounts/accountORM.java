package org.ssa.ironyard.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.ORM;
import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.customer.Customer;
import org.ssa.ironyard.customer.Customers;



public interface AccountORM extends ORM<Account>
{
    @Override
    default String projection()
    {
        return "id, customer, type, balance";
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
        return "Update " + table() + " Set customer = ?, type = ?, balance= ? Where id= ?";
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
 
    default String prepareDeleteAll()
    {
        return "Delete From " + table();
    }
    
    @Override
    default Account map(ResultSet results) throws SQLException
    {
        
        Customer c = new Customer();
        c.setID(results.getInt("customer"));
        Account account = new Account(results.getInt("id"),c , Type.getInstance(results.getString("type")),results.getBigDecimal("balance"));

        return account;
    }
    
    
}