package org.ssa.ironyard.customer;

public class Customer 
{
    private int id;
    private String firstName;
    private String lastName;
    
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

    public void setId(int id)
    {
        this.id = id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
        Customer other = (Customer) obj;
        if (firstName == null)
        {
            if (other.firstName != null)
                return false;
        }
        else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null)
        {
            if (other.lastName != null)
                return false;
        }
        else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }
    
    
    
}
