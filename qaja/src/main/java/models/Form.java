package models;

public class Form {
    public String firstName;
    public String lastName;
    public String email;
    public String comment;

    public Form(
            String firstName,
            String lastName,
            String email,
            String comment)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.comment = comment;
    }
}
