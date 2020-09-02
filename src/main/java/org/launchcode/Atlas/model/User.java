package org.launchcode.Atlas.model;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


@Entity
@Table(name = "Atlas_User")
public class User extends AbstractEntity{

    private String userName;

    private String passwordHash;

    //ill need a marker create and remove and update... so the user gets matched
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Marker> markers = new ArrayList<>();



    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public User addMarker(Marker marker) {
        markers.add(marker);
        return this;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

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

}
