package com.najmeddine.BestReservation;

public class UserHelperClass {
    String fullName, email, password,phoneNo,as;

    public UserHelperClass(String fullName, String email, String password, String phoneNo, String as) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.as = as;
    }

    public UserHelperClass() {

    }

    public UserHelperClass(String fullName, String email, String phoneNo) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}



