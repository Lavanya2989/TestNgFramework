package models;

import dictionaries.State;

public class BillingOrder {
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String city;
    public String zipCode;
    public State state;
    public String addressLine1;
    public String addressLine2;
    public int itemNumber;
    public String comment;

    public BillingOrder(
            String firstName,
            String lastName,
            String email,
            String phone,
            String city,
            String zipCode,
            State state,
            String addressLine1,
            String addressLine2,
            int itemNumber,
            String comment) 
    {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.city = city;
            this.zipCode = zipCode;
            this.state = state;
            this.addressLine1 = addressLine1;
            this.addressLine2 = addressLine2;
            this.itemNumber = itemNumber;
            this.comment = comment;
    }
}
