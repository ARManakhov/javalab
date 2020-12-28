package dev.sirosh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy")
    Date birthDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy")
    Date dateOfIssue;
    Boolean isEmployee;

}
