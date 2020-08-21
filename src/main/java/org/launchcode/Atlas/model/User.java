package org.launchcode.Atlas.model;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Atlas_User")
public class User extends AbstractEntity{

    private String userName;

    private String passwordHash;



    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPasswordHash(String password) {
        passwordHash = encoder.encode(password);
        this.passwordHash = passwordHash;
    }

    public boolean isPasswordValid(String password) {
        return encoder.matches(password, passwordHash);
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getPasswordHash() {
        return passwordHash;
    }

//    name
//    needs the basics of a user class
//    password
//    password verification method
//    password converter/set
}
