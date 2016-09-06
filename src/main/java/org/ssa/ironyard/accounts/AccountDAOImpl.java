package org.ssa.ironyard.accounts;

import java.math.BigDecimal;
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
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.customer.Customer;

public class AccountDAOImpl extends AbstractDAO<Account> implements AccountDAO
{

    public AccountDAOImpl(DataSource datasource)
    {
        super(datasource, new AccountORM()
        {
        });

    }

    @Override
    public Account insert(Account account)
    {
        Account a = null;
        Connection connection = null;
        PreparedStatement insertStatement = null;
        ResultSet generatedKeys = null;
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
                generatedKeys = insertStatement.getGeneratedKeys();
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
        finally
        {
            cleanup(generatedKeys,insertStatement, connection);
        }
    }

    @Override
    public Account update(Account account)
    {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        Account a = null;
        try
        {
            connection = datasource.getConnection();
            updateStatement = connection.prepareStatement(orm.prepareUpdate());
            updateStatement.setInt(1, account.getCustomer().getId());
            updateStatement.setString(2, account.getType().abbrev);
            updateStatement.setBigDecimal(3, account.getBalance());
            updateStatement.setInt(4, account.getId());
            
            if(updateStatement.executeUpdate() > 0)
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
        finally
        {
            cleanup(updateStatement,connection);
        }
    }

    public List<Account> readUser(int user)
    {
        Connection connection = null;
        List<Account> customerByUser = new ArrayList<>();
        PreparedStatement readUserStatement = null;
        ResultSet results = null;
        try
        {
            connection = datasource.getConnection();
            readUserStatement = connection.prepareStatement(orm.readUser());
            readUserStatement.setInt(1, user);
            results = readUserStatement.executeQuery();
            
            while(results.next())
            {
                Account a = orm.map(results);
                customerByUser.add(a);
            }
            
            return customerByUser;  
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
        finally
        {
            cleanup(results, readUserStatement,connection);
        }
    }
    
    public List<Account> readType(Type type)
    {
        Connection connection = null;
        List<Account> customerByType = new ArrayList<>();
        PreparedStatement readTypeStatement = null;
        ResultSet results = null;
        try
        {
            connection = datasource.getConnection();
            readTypeStatement = connection.prepareStatement(orm.readType());
            readTypeStatement.setString(1, type.abbrev);
            results = readTypeStatement.executeQuery();
            
            while(results.next())
            {
                Account a = orm.map(results);
                customerByType.add(a);
            }
            
            return customerByType;  
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
        finally
        {
            cleanup(results, readTypeStatement,connection);
        }
    }
    
    public List<Account> readUnderwater()
    {
        Connection connection = null;
        List<Account> customerUnderwater = new ArrayList<>();
        PreparedStatement underwaterStatement = null;
        ResultSet results = null;
        try
        {
            connection = datasource.getConnection();
            underwaterStatement = connection.prepareStatement(orm.prepareReadLessThan());
            underwaterStatement.setBigDecimal(1, BigDecimal.valueOf(0));
            results = underwaterStatement.executeQuery();
            
            while(results.next())
            {
                Account a = orm.map(results);
                customerUnderwater.add(a);
            }
            
            return customerUnderwater;  
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
        finally
        {
            cleanup(results, underwaterStatement,connection);
        }
        
        
        
        
    }
    
    public List<Account> readEagerUser(int user)
    {
        Connection connection = null;
        List<Account> customerByUser = new ArrayList<>();
        PreparedStatement readUserStatement = null;
        ResultSet results = null;
        try
        {
            connection = datasource.getConnection();
            readUserStatement = connection.prepareStatement(orm.eagerReadUser());
            readUserStatement.setInt(1, user);
            results = readUserStatement.executeQuery();
            
            while(results.next())
            {
                Account a = orm.deepMap(results);
                customerByUser.add(a);
            }
            
            return customerByUser;  
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
        finally
        {
            cleanup(results, readUserStatement,connection);
        }
    }
    
    public List<Account> readEagerType(Type type)
    {
        Connection connection = null;
        List<Account> customerByType = new ArrayList<>();
        PreparedStatement readTypeStatement = null;
        ResultSet results = null;
        try
        {
            connection = datasource.getConnection();
            readTypeStatement = connection.prepareStatement(orm.eagerReadType());
            readTypeStatement.setString(1, type.abbrev);
            results = readTypeStatement.executeQuery();
            
            while(results.next())
            {
                Account a = orm.deepMap(results);
                customerByType.add(a);
            }
            
            return customerByType;  
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
        finally
        {
            cleanup(results, readTypeStatement,connection);
        }
    }
    
    public List<Account> readEagerUnderwater()
    {
        Connection connection = null;
        List<Account> customerUnderwater = new ArrayList<>();
        PreparedStatement underwaterStatement = null;
        ResultSet results = null;
        try
        {
            connection = datasource.getConnection();
            underwaterStatement = connection.prepareStatement(orm.eagerPrepareReadLessThan());
            underwaterStatement.setBigDecimal(1, BigDecimal.valueOf(0));
            results = underwaterStatement.executeQuery();
            
            while(results.next())
            {
                Account a = orm.deepMap(results);
                customerUnderwater.add(a);
            }
            
            return customerUnderwater;  
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
        finally
        {
            cleanup(results, underwaterStatement,connection);
        }
        
        
        
        
    }



}
