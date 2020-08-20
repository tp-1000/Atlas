package org.launchcode.Atlas.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "Atlas_User")
public class User extends AbstractEntity{

    @NonNull
    private String name;
    @NonNull
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
