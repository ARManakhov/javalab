package dev.sirosh.model;

import lombok.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    String firstName;
    String lastName;
    String passportNumber;
    String phone;
    String email;
    Date birthDay;
    Date dateOfIssue;

}
