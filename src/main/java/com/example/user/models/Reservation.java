package com.example.user.models;

import java.time.LocalDateTime;

public class Reservation {
    private final String restaurantName;
    private final LocalDateTime dateTime;
    private final int guests;
    private final String specialRequest;
    private String status; // ✅ Removed 'final'

    public Reservation(String restaurantName, LocalDateTime dateTime, int guests, String specialRequest, String status) {
        this.restaurantName = restaurantName;
        this.dateTime = dateTime;
        this.guests = guests;
        this.specialRequest = specialRequest;
        this.status = status;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getGuests() {
        return guests;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { // ✅ Setter added
        this.status = status;
    }
}
