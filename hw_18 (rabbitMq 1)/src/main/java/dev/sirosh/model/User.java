package dev.sirosh.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
//    System.out.println("please enter first name");
//            System.out.println("please enter last name");
//            System.out.println("please enter passport number");
//            System.out.println("please enter birth day");
//            System.out.println("please enter date of issue");
    String firstName;
    String lastName;
    String passportNumber;
    Date birthDay;
    Date dateOfIssue;

    public User(String firstName, String lastName, String passportNumber, Date birthDay, Date dateOfIssue) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
        this.birthDay = birthDay;
        this.dateOfIssue = dateOfIssue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    private User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", birthDay=" + birthDay +
                ", dateOfIssue=" + dateOfIssue +
                '}';
    }
}
