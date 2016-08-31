package org.ssa.ironyard.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public abstract class AbstractDAO<T extends DomainObject> 
{
    final DataSource datasource;
    final ORM<T> orm;
    

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
            return prepareStatement.executeUpdate()>0;

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
                return this.orm.mapResult(query);

        }
        catch (Exception ex)
        {

        }
        return null;
    }
    
    static protected void cleanup(ResultSet results, Statement statement, Connection connection)
    {
        if(results != null)
        {
            
        }
          
    }
    static protected void cleanup(Statement statement, Connection connection)
    {
        
    }

}
