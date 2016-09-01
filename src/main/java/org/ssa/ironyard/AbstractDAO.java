package org.ssa.ironyard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.ssa.ironyard.ORM;
import org.ssa.ironyard.accounts.Account;

public abstract class AbstractDAO<T extends DomainObject>
{
    protected final DataSource datasource;
    protected final ORM<T> orm;

    protected AbstractDAO(DataSource datasource, ORM<T> orm)
    {
        this.datasource = datasource;
        this.orm = orm;
    }

    public abstract T insert(T domain);

    public boolean delete(int id)
    {
        PreparedStatement prepareStatement;
        try
        {
            Connection connection = this.datasource.getConnection();
            prepareStatement = connection.prepareStatement(orm.prepareDelete());
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;

        }
        catch (SQLException e)
        {
            return false;
        }
    }
    
    

    public abstract T update(T domain);

    public T read(int id)
    {
        try
        {
            Connection connection = this.datasource.getConnection();
            PreparedStatement read = connection.prepareStatement(this.orm.prepareRead());
            read.setInt(1, id);
            ResultSet query = read.executeQuery();
            if (query.next())
                return this.orm.map(query);

        }
        catch (Exception ex)
        {

        }
        return null;
    }

    public int clear() throws UnsupportedOperationException
    {
        Connection conn = null;
        Statement clear = null;
        try
        {
            conn = this.datasource.getConnection();
            clear = conn.createStatement();
            return clear.executeUpdate("DELETE FROM " + this.orm.table() + ";");
        }
        catch (Exception ex)
        {

        }
        finally
        {
            cleanup(clear, conn);
        }
        return 0;
    }

    static protected void cleanup(ResultSet results, Statement statement, Connection connection)
    {
       try
       {
           results.close();
       }
       catch(SQLException e)
       {
           throw new RuntimeException();
       }
       cleanup(statement, connection);

    }

    static protected void cleanup(Statement statement, Connection connection)
    {
        try
        {
            statement.close();
            connection.close();
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
    }

}
