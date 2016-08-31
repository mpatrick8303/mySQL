package org.ssa.ironyard.customer;

import java.sql.SQLException;

public interface CustomerDAO
{
    Customer insert(Customer customer);
    boolean delete(Customer toDelete);
    Customer update(Customer customer);
    Customer read(int id);
}
