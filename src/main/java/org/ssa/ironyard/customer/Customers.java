package org.ssa.ironyard.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.ssa.ironyard.AbstractDAO;
import org.ssa.ironyard.ORM;

public class Customers  extends AbstractDAO<Customer> implements CustomerDAO
{

    
  

    protected Customers(DataSource datasource, ORM<Customer> orm)
    {
        super(datasource, orm);
        // TODO Auto-generated constructor stub
    }
    
    public Customers(DataSource datasource){
        this(datasource, new CustomerORM(){});

    }

    @Override
    public Customer insert(Customer customer)
    {
        Connection connection = null;
        Customer c = null;
        PreparedStatement prepareStatement;
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement(orm.prepareInsert(), Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, customer.getFirstName());
            prepareStatement.setString(2, customer.getLastName());      
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            generatedKeys.next();
            c = new Customer(generatedKeys.getInt(1),customer.getFirstName(),customer.getLastName());
            close(connection);
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
        Connection connection;
        PreparedStatement prepareStatement;
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement(orm.prepareDelete());
            prepareStatement.setInt(1, toDelete.getId());
            boolean delete =  prepareStatement.executeUpdate()>0;
            close(connection);
            return delete;

        }
        catch (SQLException e)
        {
            return false;
        }

    }

    public Customer update(Customer customerUpdate)
    {
        Connection connection = null;
        PreparedStatement prepareStatement;
        Customer c = null;
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement(orm.prepareUpdate());
            prepareStatement.setString(1, customerUpdate.getFirstName());
            prepareStatement.setString(2, customerUpdate.getLastName());
            prepareStatement.setInt(3, customerUpdate.getId());
            
            if(prepareStatement.executeUpdate() > 0)
            {
                 close(connection);
                 return customerUpdate;            }
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
        Connection connection = null;
        PreparedStatement prepareStatementQ;
        Customer c = null;
        try
        {
            connection = datasource.getConnection();
            prepareStatementQ = connection.prepareStatement("Select * From customers Where id=?");
            prepareStatementQ.setInt(1, id);
            ResultSet results = prepareStatementQ.executeQuery();

            if (results.next())
            {
                c = orm.map(results);

            }
            close(connection);
            return c;
        }
        catch (SQLException e)
        {
            return c;
        }

    }

    public List<Customer> readAll()
    {
        Connection connection = null;
        List<Customer> customers = new ArrayList<>();
        PreparedStatement preparedStatement;
        try
        {
            connection = datasource.getConnection();
            preparedStatement = connection.prepareStatement("Select * From customers");
            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                Customer c = orm.map(results);
                customers.add(c);
            }
            close(connection);
            return customers;
        }
        catch (SQLException e)
        {
            return customers;
        }

    }

    public List<Customer> readFirstName(String firstName)
    {
        Connection connection = null;
        List<Customer> customersByFirstName = new ArrayList<>();
        PreparedStatement preparedStatement;
        try
        {
            connection = datasource.getConnection();
            preparedStatement = connection.prepareStatement("Select * From customers Where first=?");
            preparedStatement.setString(1, firstName);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                Customer c = orm.map(results);
                customersByFirstName.add(c);
            }
            close(connection);
            return customersByFirstName;
        }
        catch (SQLException e)
        {
            return customersByFirstName;
        }

    }

    public List<Customer> readLastName(String lastName)
    {
        Connection connection = null;
        List<Customer> customersByLastName = new ArrayList<>();
        PreparedStatement preparedStatement;
        try
        {
            connection = datasource.getConnection();
            preparedStatement = connection.prepareStatement("Select * From customers Where last=?");
            preparedStatement.setString(1, lastName);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                Customer c = orm.map(results);
                customersByLastName.add(c);
            }
            close(connection);
            return customersByLastName;

        }
        catch (SQLException e)
        {
            return customersByLastName;
        }

    }

    public boolean deleteAll()
    {
        Connection connection;
        PreparedStatement prepareStatement;
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement(orm.prepareDeleteAll());
            boolean deleteAll = prepareStatement.execute();
            close(connection);
            return deleteAll;
        }
        catch (SQLException e)
        {
            return false;
        }
        

    }

    public void close(Connection connection)
    {

        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            return;
        }
    }

    @Override
    public void close()
    {
        // TODO Auto-generated method stub
        
    }

}
