package org.ssa.ironyard.accounts;

import java.util.List;

import org.ssa.ironyard.accounts.Account.Type;

public interface AccountDAO
{
    Account insert(Account account);
    boolean delete(int id);
    Account read(int id);
    Account update(Account account);
    List<Account> readUser(int user);
    List<Account> readType(Type type);
    List<Account> readUnderwater();
    

}
