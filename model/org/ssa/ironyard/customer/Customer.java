package org.ssa.ironyard.customer;

import org.ssa.ironyard.DomainObject;
import org.ssa.ironyard.accounts.Account;

public class Customer implements DomainObject
{
    private int id;
    private String firstName;
    private String lastName;
    
    public Customer(int id, String fN, String lN)
    {
        this.id = id;
        this.firstName = fN;
        this.lastName = lN;
    }
    
    public Customer(String fN, String lN)
    {
        this.firstName = fN;
        this.lastName = lN;
    }
    
    public Customer()
    {
        // TODO Auto-generated constructor stub
    }

    public int getId()
    {
        return id;
    }


    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }


    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public void setID(int id)
    {
        this.id = id;
    }
    
    @Override
    public Customer clone()
    {
        try
        {
            Customer copy = (Customer) super.clone();
            return copy;
        }
        catch(CloneNotSupportedException ex)
        {
            
        }
        return null;
        
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + id;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj)//a lot of times want to change this to have if the id = id then they are equal to each other
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
      return this.id == other.id;
    }
    
    

    public boolean deeplyEquals(Object obj)//a lot of times want to change this to have if the id = id then they are equal to each other
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
      return this.id == other.id && this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName);
    }
    
    
}
