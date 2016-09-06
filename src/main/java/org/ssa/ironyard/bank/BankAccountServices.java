package org.ssa.ironyard.bank;

import java.math.BigDecimal;

import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.customer.Customer;

public interface BankAccountServices
{
    Customer insertCustomer(String fName, String lName);
    Account insterAccount(Customer customer, Type type, BigDecimal balance);
    boolean deleteAccount(int id);
    boolean deleteCustomer(int id);
    Customer updateCustomer(Customer customer);
    Account updateAccount(Account account);
    
}
