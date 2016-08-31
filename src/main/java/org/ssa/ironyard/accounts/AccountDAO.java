package org.ssa.ironyard.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.ssa.ironyard.customer.Customer;

public class AccountDAO extends AbstractDAO<Account>
{
    private Connection connection;
    ORM orm;
    
    protected AccountDAO(DataSource datasource)
    {
        super(datasource, orm);
        
    }


    @Override
    public Account insert(Account account)
    {

        Account a = null;
        Connection connection;
        PreparedStatement insertStatement;
        try
        {
            connection = datasource.getConnection();
            insertStatement = connection.prepareStatement(orm.prepareInsert(), Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, account.getCustomer());
            insertStatement.setString(2, account.getType());  
            insertStatement.setFloat(3, account.getBalance());  
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            a = new Account(generatedKeys.getInt(1),account.getCustomer(),account.getType(),account.getBalance());
            return a;
        }
        catch (SQLException e)
        {
            return a;
        }
    }


    @Override
    public Account update(Account domain)
    {

        PreparedStatement prepareStatement;
        Account a = null;
        try
        {
            prepareStatement = connection.prepareStatement(orm.prepareUpdate());
            prepareStatement.setInt(1, domain.getCustomer());
            prepareStatement.setString(2, domain.getType());
            prepareStatement.setFloat(3, domain.getBalance());
            prepareStatement.setInt(4, domain.getId());
            
            if(prepareStatement.executeUpdate() > 0)
            {
                a = orm.mapAccount(domain);
                return a;
            }
            else
                return a;
        }
        catch (SQLException e)
        {
            return a;
        }
    }
    
    public List<Account> readUser(int user)
    {
        return null;
    }
    
    public List<Account> readUnderwater()
    {
        return null;
    }



    

  
}
