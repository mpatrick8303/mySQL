package org.ssa.ironyard.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.ORM;
import org.ssa.ironyard.accounts.Account;

public interface CustomerORM extends ORM<Customer>
{
    default String projection()
    {
        return "id,first,last";
    }
    
    default String table()
    {
        return "customers";
    }
    



    default Customer map(ResultSet results) throws SQLException
    {
        Customer c = new Customer();
        c.setFirstName(results.getString("first"));
        c.setLastName(results.getString("last"));
        c.setID(results.getInt("id"));
        return c;
        
    }

    
    default String prepareInsert()
    {
        return "Insert into " + table() + " (first,last) Values(?,?)";
    }
    default String prepareUpdate()
    {
        return "Update " + table() + " Set first = ?, last = ? Where id=?";
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
    
    default String readUser()
    {
        return prepareRead();
    }
    default String readType()
    {
        return prepareRead();
    }
    default String prepareReadLessThan()
    {
        return null;
    }
    
    default String eagerRead()
    {
        return prepareRead();
    }
    
    default Customer deepMap(ResultSet query) throws SQLException
    {
        return map(query);
    }
    
    default String eagerReadUser()
    {
        return prepareRead();
    }

    default String eagerReadType()
    {
        return prepareRead();
    }

    default String eagerPrepareReadLessThan()
    {
        return prepareRead();
    }

  
    


}
