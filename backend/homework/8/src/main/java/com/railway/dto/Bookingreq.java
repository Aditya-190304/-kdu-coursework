package com.railway.dto;

import java.io.Serializable;


public class Bookingreq implements Serializable {

    private String bookingId;
    private String userId;

    // i added age specifically to test the poison scenario.
    // if i send a negative age, the inventory service throws an error to test retries.
    private int age;

    private double amount;

    public Bookingreq() {}

    public String getBookingId() { return bookingId; }
    // setter is used by the controller if the ID is missing
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}