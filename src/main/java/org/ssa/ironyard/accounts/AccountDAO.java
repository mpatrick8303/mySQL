package org.ssa.ironyard.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.ssa.ironyard.AbstractDAO;
import org.ssa.ironyard.ORM;
import org.ssa.ironyard.customer.Customer;

public class AccountDAO extends AbstractDAO<Account>
{
    private Connection connection;

    protected AccountDAO(DataSource datasource)
    {
        super(datasource, new AccountORM()
        {
        });

    }

    @Override
    public Account insert(Account account)
    {
        Account a = null;
        Connection connection;
        PreparedStatement insertStatement;
        try
        {
            Account ins = null;
            connection = datasource.getConnection();
            insertStatement = connection.prepareStatement(orm.prepareInsert(), Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, account.getCustomer().getId());
            insertStatement.setString(2, account.getType().abbrev);
            insertStatement.setBigDecimal(3, account.getBalance());
            if (insertStatement.executeUpdate() > 0)
            {
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next())
                {
                    a = new Account(generatedKeys.getInt(1),account.getCustomer(),account.getType(),account.getBalance());

//                    ins = account.clone();
//                    ins.setId(generatedKeys.getInt(1));
                }


            }
            return a;
        }
        catch (SQLException e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public Account update(Account account)
    {

        PreparedStatement prepareStatement;
        Account a = null;
        try
        {
            prepareStatement = connection.prepareStatement(orm.prepareUpdate());
            prepareStatement.setInt(1, account.getCustomer().getId());
            prepareStatement.setString(2, account.getType().toString());
            prepareStatement.setBigDecimal(3, account.getBalance());
            prepareStatement.setInt(4, account.getId());
            
            if(prepareStatement.executeUpdate() > 0)
            {
                
                return account;
            }
            else
                return a;
        }
        catch (SQLException e)
        {
            throw new RuntimeException();
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
