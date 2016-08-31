package org.ssa.ironyard.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerORM
{
    default String projection()
    {
        return "id,first,last";
    }
    
    default String table()
    {
        return "customers";
    }

    default Customer mapResult(ResultSet results) throws SQLException
    {
        Customer c = new Customer();
        c.setFirstName(results.getString("first"));
        c.setLastName(results.getString("last"));
        c.setID(results.getInt("id"));
        return c;
        
    }
    
    default Customer mapCustomer(Customer customer)
    {
        
            Customer c = null;
            c = new Customer(customer.getId(),customer.getFirstName(),customer.getLastName());
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
}
