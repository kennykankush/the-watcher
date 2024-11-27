package com.watcher.app.the_watcher.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Size(min = 3, max = 20, message = "Username be 3 - 20 Characters")
    private String username;

    @Size(min = 8, max = 20, message = "Password must be 8 - 20 Characters")
    private String password;

    // public User(String username, String password) {
    //     this.username = username;
    //     this.password = password;
    // }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    

}
