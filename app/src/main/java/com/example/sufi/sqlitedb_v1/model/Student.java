package com.example.sufi.sqlitedb_v1.model;

/**
 * Created by SuFi on 06-Oct-17.
 */

public class Student {
    private int id;
    private String username;
    private String password;
    private Float cgpa;
    private String phone;

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

    public Float getCgpa() {
        return cgpa;
    }

    public void setCgpa(Float cgpa) {
        this.cgpa = cgpa;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {

        return  "UserName: " + username + '\n' +
                "CGPA: " + cgpa;
    }

    public String toStringStudent() {

        return  "UserName: " + username + '\n' +
                "CGPA: " + cgpa + '\n' +
                "PhoneNo: " + phone;
    }
}
