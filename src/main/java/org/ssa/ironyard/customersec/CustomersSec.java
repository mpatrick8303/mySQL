package org.ssa.ironyard.customersec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.ssa.ironyard.customer.Customer;



public class CustomersSec implements CustomerSecDAO
{

    private Connection connection;

    public CustomersSec(DataSource datasource) throws SQLException
    {
        Connection connection = datasource.getConnection();
        this.connection = connection;
        
    }
    
    @Override
    public Customer insert(Customer customer) throws SQLException
    {
        Customer c = new Customer(customer.getFirstName(),customer.getLastName());
        
        PreparedStatement prepareStatement = this.connection.prepareStatement("Insert Into customers(first,last) Values(?,?)");
        prepareStatement.setString(1, customer.getFirstName());
        prepareStatement.setString(2, customer.getLastName());
        prepareStatement.execute();

        return c;
        
    }

    @Override
    public boolean delete(Customer toDelete) throws SQLException 
    {
        PreparedStatement prepareStatementQ = this.connection.prepareStatement("Select * From customers Where first=? && last=?");
        prepareStatementQ.setString(1, toDelete.getFirstName());
        prepareStatementQ.setString(2, toDelete.getLastName());
        ResultSet results = prepareStatementQ.executeQuery();

        
        
        if(results.next())
        {
            PreparedStatement prepareStatement = this.connection.prepareStatement("Delete From customers Where id=?");
            prepareStatement.setInt(1, results.getInt(1));
            return prepareStatement.execute();
       }
        
        return false;
        
            
    }
    

    @Override
    public Customer update(Customer customerUpdate, String newFirstName, String newLastName) throws SQLException
    {
        PreparedStatement prepareStatementQ = this.connection.prepareStatement("Select * From customers Where first=? && last=?");
        prepareStatementQ.setString(1, customerUpdate.getFirstName());
        prepareStatementQ.setString(2, customerUpdate.getLastName());
        ResultSet results = prepareStatementQ.executeQuery();
        Customer c = null;
        
        
        if(results.next())
        {
            int cID = results.getInt(1);
            c = new Customer(newFirstName, newLastName);
            PreparedStatement prepareStatement = this.connection.prepareStatement("Update customers Set first = ?, last = ? Where id=?");
            prepareStatement.setString(1, newFirstName);
            prepareStatement.setString(2, newLastName);
            prepareStatement.setInt(3, cID);
            prepareStatement.executeUpdate();
        }
        
        
        return c;   
        
                
    }
    


    @Override
    public Customer read(int id) throws SQLException
    {
        PreparedStatement prepareStatementQ = this.connection.prepareStatement("Select * From customers Where id=?");
        prepareStatementQ.setInt(1, id);
        ResultSet results = prepareStatementQ.executeQuery();
        Customer c = null;
        
        if(results.next())
        {
             c = new Customer(results.getInt(1),results.getString(2), results.getString(3));
            
        }
        
        return c;
            
        
        
        
        
    }
    
    protected int getID(Customer customer) throws SQLException
    {
        PreparedStatement prepareStatementQ = this.connection.prepareStatement("Select * From customers Where first=? && last=?");
        prepareStatementQ.setString(1, customer.getFirstName());
        prepareStatementQ.setString(2, customer.getLastName());
        
        ResultSet results = prepareStatementQ.executeQuery();
        results.next();
        return results.getInt(1);
    }
    
    protected ResultSet find(int id) throws SQLException
    {
        PreparedStatement prepareStatementQ = this.connection.prepareStatement("Select * From customers Where id=?");
        prepareStatementQ.setInt(1, id);
        ResultSet results = prepareStatementQ.executeQuery();
        return results;
    }
    
    protected boolean deleteAll() throws SQLException
    {
        PreparedStatement prepareStatement = this.connection.prepareStatement("Delete From customers");
        return prepareStatement.execute();
    }
    
    protected void close() throws SQLException
    {
        this.connection.close();
    }


    
    
}
