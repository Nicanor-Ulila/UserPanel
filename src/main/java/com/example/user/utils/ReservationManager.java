// ReservationManager.java
package com.example.user.utils;

import com.example.user.models.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReservationManager {
    private static final ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    public static ObservableList<Reservation> getReservations() {
        return reservations;
    }

    public static void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}
