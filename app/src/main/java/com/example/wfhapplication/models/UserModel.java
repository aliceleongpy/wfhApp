package com.example.wfhapplication.models;

public class UserModel {
    String email;
    String username;
    String password;
    String location;
    String contact;

    public UserModel(){

    }

    public UserModel(String email,String username, String password, String location, String contact){
        this.email = email;
        this.username = username;
        this.password = password;
        this.location = location;
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
        this.password= password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
