// ReservationData.java
package com.example.user.data;

import com.example.user.models.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.util.stream.Collectors;

public class ReservationData {

    private static ReservationData instance = new ReservationData();
    private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    private ReservationData() {}

    public static ReservationData getInstance() {
        return instance;
    }

    public ObservableList<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public ObservableList<Reservation> getReservationsByStatus(String status) {
        return FXCollections.observableArrayList(
                reservations.stream()
                        .filter(r -> r.getStatus().equalsIgnoreCase(status))
                        .collect(Collectors.toList())
        );
    }
}
