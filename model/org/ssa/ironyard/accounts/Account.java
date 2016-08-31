package org.ssa.ironyard.accounts;

public class Account implements DomainObject
{
    
    private int id;
    private int customer;
    private String type;
    private float balance;
    
    public Account(int id, int customer, String type, float balance)
    {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.balance = balance;
        
        
    }
    
    public Account(int customerId,String type, float balance)
    {
        this.customer = customerId;
        this.type = type;
        this.balance = balance;
    }
    
    public Account()
    {
        
    }
    
    public int getId()
    {
        return id;
    }

    public int getCustomer()
    {
        return customer;
    }

    public String getType()
    {
        return type;
    }

    public float getBalance()
    {
        return balance;
    }

    
    
    
    enum accountType{CH, SA}




    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(balance);
        result = prime * result + customer;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (Float.floatToIntBits(balance) != Float.floatToIntBits(other.balance))
            return false;
        if (customer != other.customer)
            return false;
        if (id != other.id)
            return false;
        if (type == null)
        {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }
    
    
    
}
