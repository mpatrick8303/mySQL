package org.ssa.ironyard;

import org.ssa.ironyard.accounts.Account;
import org.ssa.ironyard.customer.Customer;

public interface DomainObject
{
    
    public default Integer getId()
    {
        return null;
        
    }

    
    public default boolean deeplyEquals(Object obj)
    {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (getClass() != obj.getClass())
          return false;
        
        Account a = new Account();
        Customer c = new Customer();
        if(obj.getClass().equals(a.getClass()))
        {
            Account a2 = (Account) this;
            a = (Account) obj;
            return a.getId().equals(a2.getId()) && a.getCustomer().equals(a2.getCustomer()) && a.getBalance().compareTo(a2.getBalance())==0 && a.getType().equals(a2.getType());
        }
        if(obj.getClass().equals(c.getClass()))
        {
            Customer c2 = (Customer) this;
            c = (Customer) obj;
            return c.getId().equals(c2.getId()) && c.getFirstName().equals(c2.getFirstName()) && c.getLastName().equals(c2.getLastName());
            
        }
        
        
        return false;
        
    }
    
    
    
    public default boolean isLoaded()
    {
       
        Account a = new Account();
        Customer c = new Customer();
        if(this.getClass().equals(a.getClass()))
        {
            a = (Account) this;
            if(a.getId() == null)
                return false;
            if(a.getCustomer().getId() == null)
                return false;
            if(a.getType() == null)
                return false;
            if(a.getBalance() == null)
                return false;
            
            return true;
        }
        if(this.getClass().equals(c.getClass()))
        {
            c = (Customer) this;
            if(c.getId() == null)
                return false;
            if(c.getFirstName() == null)
                return false;
            if(c.getLastName() == null)
                return false;
        }
        
        
        
        return true;
    }
}
