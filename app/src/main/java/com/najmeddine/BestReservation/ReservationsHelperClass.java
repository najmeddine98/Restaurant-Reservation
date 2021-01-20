package com.najmeddine.BestReservation;

public class ReservationsHelperClass {
    String key, firstName, lastName, phoneNo, reservationDate, reservationTime, tableNo, commentFild;

    public ReservationsHelperClass(String key,String reservationDate, String reservationTime, String tableNo) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.tableNo = tableNo;
        this.key=key;
    }

    public ReservationsHelperClass() {
    }

    public ReservationsHelperClass(String firstName, String lastName, String phoneNo, String reservationDate, String reservationTime, String tableNo, String commentFild) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.tableNo = tableNo;
        this.commentFild = commentFild;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ReservationsHelperClass(String reservationDate) {
        this.reservationDate = reservationDate;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getCommentFild() {
        return commentFild;
    }

    public void setCommentFild(String commentFild) {
        this.commentFild = commentFild;
    }
}
