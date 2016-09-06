package org.ssa.ironyard.bank;

import java.math.BigDecimal;

import org.ssa.ironyard.accounts.Account;

public interface BankServices
{
    Account Withdrawl(int account, BigDecimal amount);
    
    Account Deposit(int account, BigDecimal amount);
    
    Account Transfer(int accountOne, int accountTwo, BigDecimal amount);
    
}
