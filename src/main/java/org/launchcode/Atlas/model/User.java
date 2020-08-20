package org.launchcode.Atlas.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Atlas_User")
public class User extends AbstractEntity{

    @NotBlank
    @Size(max = 25, min = 1, message = "Name needs 1 to 25 characters")
    private String name;
    @NotBlank
    @Size(max = 25, min = 1, message = "Password needs 1 to 25 characters")
    private String passwordHash;



    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setPasswordHash(@NonNull String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @NonNull
    public String getName() {
        return name;
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
