package com.webkorps.library.models;

public class User {
    private long userId;
    private String memberId;
    private String name;
    private String nameOfLibrary;
    private String address;
    private String email;
    private String role;
    private String password;

    public User() {
    }

    public User(long userId, String memberId, String name, String nameOfLibrary, String address, String email, String role, String password) {
        this.userId = userId;
        this.memberId = memberId;
        this.name = name;
        this.nameOfLibrary = nameOfLibrary;
        this.address = address;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfLibrary() {
        return nameOfLibrary;
    }

    public void setNameOfLibrary(String nameOfLibrary) {
        this.nameOfLibrary = nameOfLibrary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", memberId=" + memberId + ", name=" + name + ", nameOfLibrary=" + nameOfLibrary + ", address=" + address + ", email=" + email + ", role=" + role + ", password=" + password + '}';
    }
    
}
