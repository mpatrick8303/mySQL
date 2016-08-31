//package org.ssa.ironyard.accounts;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//
//import org.ssa.ironyard.customer.Customer;
//
//public class AccountDAOImpl implements AccountDAO
//{
//    AccountDAO d;
//    
//
//    @Override
//    public Account insert(Account account)
//    {
//        Customer c = null;
//        PreparedStatement insert;
//        try
//        {
//            insert = this.connection.prepareStatement(, Statement.RETURN_GENERATED_KEYS);
//            insert.setString(1, account.getFirstName());
//            insert.setString(2, account.getLastName());      
//            insert.executeUpdate();
//            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
//            generatedKeys.next();
//            c = new Customer(generatedKeys.getInt(1),customer.getFirstName(),customer.getLastName());
//            return c;
//        }
//        catch (SQLException e)
//        {
//            return c;
//        }
//    }
//
//      
//
//    @Override
//    public Account update(Account Account)
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Account read(int id)
//    {
//        return d.read(id);
//
//    }
//
//    @Override
//    public boolean deleteAll()
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//
//
//    @Override
//    public boolean delete(Account toDelete)
//    {
//        return d.delete(toDelete);
//    }
//
//
//}
