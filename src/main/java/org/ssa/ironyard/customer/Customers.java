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

    public Customers(DataSource datasource)
    {
        Connection connection;
        try
        {
            connection = datasource.getConnection();
            this.connection = connection;
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public Customer insert(Customer customer)
    {
        Customer c = null;
        PreparedStatement prepareStatement;
        try
        {
            prepareStatement = this.connection.prepareStatement("Insert Into customers(first,last) Values(?,?)", Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, customer.getFirstName());
            prepareStatement.setString(2, customer.getLastName());
            prepareStatement.execute();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            generatedKeys.next();
            c = read(generatedKeys.getInt(1));
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
            prepareStatement = this.connection.prepareStatement("Delete From customers Where id=?");
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
            prepareStatement = this.connection.prepareStatement("Update customers Set first = ?, last = ? Where id=?");
            prepareStatement.setString(1, customerUpdate.getFirstName());
            prepareStatement.setString(2, customerUpdate.getLastName());
            prepareStatement.setInt(3, customerUpdate.getId());
            prepareStatement.executeUpdate();

            return read(customerUpdate.getId());
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
                c = new Customer(results.getString(2), results.getString(3));
                c.setId(results.getInt(1));

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
                Customer c = new Customer(results.getString(2), results.getString(3));
                c.setId(results.getInt(1));
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
                Customer c = new Customer(results.getString(2), results.getString(3));
                c.setId(results.getInt(1));
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
                Customer c = new Customer(results.getString(2), results.getString(3));
                c.setId(results.getInt(1));
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
            prepareStatement = this.connection.prepareStatement("Delete From customers");
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
