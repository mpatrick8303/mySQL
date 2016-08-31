package org.ssa.ironyard.customer;

import java.sql.SQLException;

public interface CustomerSecDAO
{
    Customer insert(Customer customer) throws SQLException;
    boolean delete(Customer toDelete) throws SQLException;
    Customer update(Customer customer, String firstName, String lastName) throws SQLException;

    Customer read(int id) throws SQLException;
}
