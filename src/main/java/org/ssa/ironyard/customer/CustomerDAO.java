package org.ssa.ironyard.customer;

import java.util.List;

public interface CustomerDAO
{
    Customer insert(Customer customer);
    boolean delete(Customer toDelete);
    Customer update(Customer customer);
    Customer read(int id);
    List<Customer> readAll();
    List<Customer> readFirstName(String firstName);
    List<Customer> readLastName(String lastName);
    boolean deleteAll();
    void close();
}
