package com.example.s11.myemailclient;

public class User implements java.io.Serializable {
    private String username;
    private String password;
    private EmailProvider emailProvider;

    public User (String username, String password){
        this.username = username;
        this.password = password;
        //this.emailProvider = emailProvider;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailProvider(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }
}
