package org.launchcode.Atlas.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterUserDTO {

    @NotBlank
    @Size(max = 25, min = 1, message = "Name needs 1 to 25 characters")
    private String userName;

    @NotBlank
    @Size(max = 25, min = 1, message = "Password needs 1 to 25 characters")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
