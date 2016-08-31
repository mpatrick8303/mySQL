package org.ssa.ironyard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;


public class ConnectTests
{
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root";
    
    DataSource datasource;
    Connection connection;
    
    @Before
    public void setupDB() throws SQLException
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);//setting the url to go look for the database
       // mysqlDataSource.getConnection();
        Connection connection = mysqlDataSource.getConnection();
        this.datasource = mysqlDataSource;
        this.connection = connection;
    }
    
    @Test
    public void datasource() throws SQLException//connecting to the sql database
    {
        
        Statement sql = connection.createStatement();//making it so you can send a statement to sql to retrieve something
        ResultSet results = sql.executeQuery("select * from customers where id = 1");
        assertTrue("", results.next());
        
        assertEquals("",1,results.getInt(1));
        assertEquals("","John", results.getString(2));
        assertEquals("","Doe", results.getString(3));
        
    }
    
    @Test
    public void prepare() throws SQLException
    {
        //prepared statement sends the command to the db server. db server sends back some id that they both know.
        //so the driver can now talk to the database using the prepared statement. So when you send the id the statement is already parsed
        //makes the object reusable (prepareStatement)(so put in a cahce)
        //uses the id sent at first to know what it's looking for
        //another benefit is sql injection which means that people can't format a entry so that it can do more than you want them to be able to do
        //bc all your sending back and forth is the id and/or the parameter your looking for
        PreparedStatement prepareStatement = this.connection.prepareStatement("Select * From customers Where id = ?");//? is serving as a place holder for the argument
        prepareStatement.setInt(1,1);
        ResultSet results = prepareStatement.executeQuery();
        
        
        assertTrue("", results.next());
        
        assertEquals("",1,results.getInt(1));
        assertEquals("","John", results.getString(2));
        assertEquals("","Doe", results.getString(3));
    }
    
    //@Test
    public void create() throws SQLException
    {
        PreparedStatement prepareStatement = this.connection.prepareStatement("insert into customers(first,last) values(?,?)");
        prepareStatement.setString(1, "Mike");
        prepareStatement.setString(2, "Patrick");
        assertFalse("",prepareStatement.execute());
        
    }
    
    //@Test
    public void createDelete() throws SQLException
    {
        PreparedStatement prepareStaementCreate = this.connection.prepareStatement("insert into customers(first,last) values(?,?)", Statement.RETURN_GENERATED_KEYS);
        //PreparedStatement prepareStatementDelete = this.connection.prepareStatement("delete from customers where first=? && last=?");
        PreparedStatement prepareStatementDelete = this.connection.prepareStatement("delete from customers where id=?");
        
        
        prepareStaementCreate.setString(1, "Jane");
        prepareStaementCreate.setString(2, "Doe");
        assertEquals("",1,prepareStaementCreate.executeUpdate());//execute update excutes the code and then send back how many rows where updated
        
        ResultSet generatedKeys = prepareStaementCreate.getGeneratedKeys();
        assertTrue("", generatedKeys.next());
        System.out.println("inserted customer with id " + generatedKeys.getInt(1));
        
        prepareStatementDelete.setInt(1, generatedKeys.getInt(1));
//        prepareStaementDelete.setString(1, "Jane");
//        prepareStaementDelete.setString(2, "Doe");
        assertEquals("",1,prepareStatementDelete.executeUpdate());
    }
    
    @Test
    public void IUQD() throws SQLException
    {
        PreparedStatement prepareStaementCreate = this.connection.prepareStatement("Insert Into customers(first,last) Values(?,?)", Statement.RETURN_GENERATED_KEYS);
        PreparedStatement prepareStatementUpdate = this.connection.prepareStatement("Update customers Set first=?, last = ? Where id=?");
        PreparedStatement prepareStatementQ = this.connection.prepareStatement("Select * From customers Where Id=?");
        PreparedStatement prepareStatementDelete = this.connection.prepareStatement("Delete From customers Where id=?");
        
        prepareStaementCreate.setString(1, "Brett");
        prepareStaementCreate.setString(2, "Garver");
        assertEquals("",1,prepareStaementCreate.executeUpdate());
        
        
        ResultSet generatedKeys = prepareStaementCreate.getGeneratedKeys();
        assertTrue("",generatedKeys.next());
        System.out.println("inserted customer with ID " + generatedKeys.getInt(1));
        
        prepareStatementUpdate.setString(1, "Travis");
        prepareStatementUpdate.setString(2, "Adams");
        prepareStatementUpdate.setInt(3, generatedKeys.getInt(1));
        assertEquals("",1,prepareStatementUpdate.executeUpdate());
        
        
        
        
        prepareStatementQ.setInt(1, generatedKeys.getInt(1));
        ResultSet results = prepareStatementQ.executeQuery();
        assertTrue("",results.next());
        assertEquals("", "Travis",results.getString(2));
        assertEquals("", "Adams",results.getString(3));
        
        
        
        prepareStatementDelete.setInt(1, generatedKeys.getInt(1));
        prepareStatementDelete.execute();
        
        
        
    }
    
    @After
    public void teardown() throws SQLException
    {
        this.connection.close();
    }
}
