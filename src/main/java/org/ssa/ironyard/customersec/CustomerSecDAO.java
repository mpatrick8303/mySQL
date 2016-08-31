package org.ssa.ironyard.customersec;

import java.sql.SQLException;

import org.ssa.ironyard.customer.Customer;

public interface CustomerSecDAO
{
    Customer insert(Customer customer) throws SQLException;
    boolean delete(Customer toDelete) throws SQLException;
    Customer update(Customer customer, String firstName, String lastName) throws SQLException;

    Customer read(int id) throws SQLException;
}
