package com.stackroute.goquery.userservice.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class User {

    @Id
    UUID uuidUserId;
    String strUserName;
    String strUserEmail;
    ArrayList<String> arrUserInterest;
    String strUserType;
    String strpassword;
    String strConfirmPassword;

}
