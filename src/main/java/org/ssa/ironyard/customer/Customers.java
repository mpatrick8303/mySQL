package org.ssa.ironyard.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class Customers implements CustomerDAO
{

    private Connection connection;
    CustomerORM cORM = new CustomerORM() {};

    public Customers(DataSource datasource)
    {
        try
        {
            this.connection = datasource.getConnection();
        }
        catch (SQLException e)
        {
            return;//the catch handles what could go wrong
            //so in this case normally if the database has an error 
            //and you have to handle the exception you want to handle and can either throw another exception or handle it how you want
        }

    }

    @Override
    public Customer insert(Customer customer)
    {
        Customer c = null;
        PreparedStatement prepareStatement;
        try
        {
            prepareStatement = this.connection.prepareStatement(cORM.prepareInsert(), Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, customer.getFirstName());
            prepareStatement.setString(2, customer.getLastName());      
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            generatedKeys.next();
            c = new Customer(generatedKeys.getInt(1),customer.getFirstName(),customer.getLastName());
            return c;
        }
        catch (SQLException e)
        {
            return c;
        }

    }

    @Override
    public boolean delete(Customer toDelete)
    {

        PreparedStatement prepareStatement;
        try
        {
            prepareStatement = this.connection.prepareStatement(cORM.prepareDelete());
            prepareStatement.setInt(1, toDelete.getId());
            return prepareStatement.executeUpdate()>0;

        }
        catch (SQLException e)
        {
            return false;
        }

    }

    public Customer update(Customer customerUpdate)
    {

        PreparedStatement prepareStatement;
        Customer c = null;
        try
        {
            prepareStatement = this.connection.prepareStatement(cORM.prepareUpdate());
            prepareStatement.setString(1, customerUpdate.getFirstName());
            prepareStatement.setString(2, customerUpdate.getLastName());
            prepareStatement.setInt(3, customerUpdate.getId());
            
            if(prepareStatement.executeUpdate() > 0)
            {
                c = cORM.mapCustomer(customerUpdate);
                return c;
            }
            else
                return c;
        }
        catch (SQLException e)
        {
            return c;
        }

    }

    @Override
    public Customer read(int id)
    {
        PreparedStatement prepareStatementQ;
        Customer c = null;
        try
        {
            prepareStatementQ = this.connection.prepareStatement("Select * From customers Where id=?");
            prepareStatementQ.setInt(1, id);
            ResultSet results = prepareStatementQ.executeQuery();

            if (results.next())
            {
                c = cORM.mapResult(results);

            }
            return c;
        }
        catch (SQLException e)
        {
            return c;
        }

    }

    public List<Customer> readAll()
    {
        List<Customer> customers = new ArrayList<>();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = this.connection.prepareStatement("Select * From customers");
            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                Customer c = cORM.mapResult(results);
                customers.add(c);
            }
            return customers;
        }
        catch (SQLException e)
        {
            return customers;
        }

    }

    public List<Customer> readFirstName(String firstName)
    {
        List<Customer> customersByFirstName = new ArrayList<>();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = this.connection.prepareStatement("Select * From customers Where first=?");
            preparedStatement.setString(1, firstName);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                Customer c = cORM.mapResult(results);
                customersByFirstName.add(c);
            }

            return customersByFirstName;
        }
        catch (SQLException e)
        {
            return customersByFirstName;
        }

    }

    public List<Customer> readLastName(String lastName)
    {
        List<Customer> customersByLastName = new ArrayList<>();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = this.connection.prepareStatement("Select * From customers Where last=?");
            preparedStatement.setString(1, lastName);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                Customer c = cORM.mapResult(results);
                customersByLastName.add(c);
            }

            return customersByLastName;

        }
        catch (SQLException e)
        {
            return customersByLastName;
        }

    }

    public boolean deleteAll()
    {
        PreparedStatement prepareStatement;
        try
        {
            prepareStatement = this.connection.prepareStatement(cORM.prepareDeleteAll());
            return prepareStatement.execute();
        }
        catch (SQLException e)
        {
            return false;
        }

    }

    public void close()
    {
        try
        {
            this.connection.close();
        }
        catch (SQLException e)
        {
            return;
        }
    }

}
