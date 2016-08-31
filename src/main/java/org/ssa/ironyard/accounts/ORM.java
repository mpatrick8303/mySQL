package org.ssa.ironyard.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface ORM<T extends DomainObject> 
{
    default String projection()
    {
        return "id,customer";
    }
    
    default String table()
    {
        return "accounts";
    }

    default T mapResult(ResultSet results) throws SQLException
    {
        Account a = new Account(results.getInt(1),results.getInt(2),results.getString(3),results.getFloat(4));
       
        return null;
        
    }
    
    default Account mapAccount(Account account)
    {
        
            Account a = null;
            a = new Account(account.getId(),account.getCustomer(),account.getType(),account.getBalance());
            return a;
    
    }
    
    default String prepareInsert()
    {
        return "Insert into " + table() + " (customer,type,balance) Values(?,?,?)";
    }
    default String prepareUpdate()
    {
        return "Update " + table() + " Set customerId = ?, type = ?, balance=? Where id=?";
    }
    
    default String prepareRead()
    {
        return "Select " + projection() + " From" + table() + "Where id=?";
    }
    default String prepareDelete()
    {
        return "Delete From " + table() + " Where id=?";
    }
    default String prepareDeleteAll()
    {
        return "Delete From " + table();
    }
}