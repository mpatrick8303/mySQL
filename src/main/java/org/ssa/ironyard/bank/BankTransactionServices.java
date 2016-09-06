package org.ssa.ironyard.bank;

import java.math.BigDecimal;

import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.accounts.Account.Type;
import org.ssa.ironyard.customer.Customer;

public interface BankTransactionServices
{
    
    Account Withdrawl(int account, BigDecimal amount) throws IllegalArgumentException;
    
    Account Deposit(int account, BigDecimal amount) throws IllegalArgumentException;
    
    Account Transfer(int accountOne, int accountTwo, BigDecimal amount) throws IllegalArgumentException;
    

    
}
