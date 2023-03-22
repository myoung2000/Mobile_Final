package com.example.finalproject.models;

public class Players {
    private long id;
    private String firstName;
    private String lastName;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        String msg = "ID: " + id + " NAME: " + firstName + " " + lastName + " ACTIVE: " + active;
        return msg;
    }
}
