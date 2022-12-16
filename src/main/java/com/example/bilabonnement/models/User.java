package com.example.bilabonnement.models;
//Udarbejdet af Malik Kütük
public class User {
    private int id;
    private String username;
    private String password;
    private UserRole role;
    private int roleID;
    private int locationId;

    public User(int id, String username, String password, UserRole role, int locationId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.locationId = locationId;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

}
