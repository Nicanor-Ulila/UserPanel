package com.example.user.controllers;

import com.example.user.data.ReservationData;
import com.example.user.models.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationFormController {

    @FXML
    private Label restaurantNameLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private Spinner<Integer> guestsSpinner;

    @FXML
    private TextArea specialRequestField;

    @FXML
    private Label statusLabel;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a");

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        guestsSpinner.setValueFactory(valueFactory);
    }

    @FXML
    private void handleSubmitReservation() {
        try {
            LocalDate date = datePicker.getValue();
            String timeInput = timeField.getText();
            int guests = guestsSpinner.getValue();
            String request = specialRequestField.getText();

            if (date == null || timeInput == null || timeInput.isEmpty()) {
                setStatus("Please complete all required fields.", Color.RED);
                return;
            }

            if (!isValidTimeFormat(timeInput)) {
                setStatus("Time must be in format like 7:00 PM", Color.RED);
                return;
            }

            LocalTime parsedTime = LocalTime.parse(timeInput.toUpperCase(), TIME_FORMATTER);
            LocalDateTime reservationDateTime = date.atTime(parsedTime);

            // Determine status based on current date and time
            String status = reservationDateTime.isBefore(LocalDateTime.now()) ? "Past" : "Upcoming";

            // Create Reservation
            Reservation newReservation = new Reservation(
                    restaurantNameLabel.getText().replace("Make a Reservation at ", ""),
                    reservationDateTime,
                    guests,
                    request,
                    status
            );

            // Add to data list
            ReservationData.getInstance().addReservation(newReservation);

            setStatus("Reservation submitted successfully!", Color.GREEN);
            clearForm();

        } catch (Exception e) {
            e.printStackTrace();
            setStatus("An error occurred: " + e.getMessage(), Color.RED);
        }
    }

    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setTextFill(color);
    }

    private void clearForm() {
        datePicker.setValue(null);
        timeField.clear();
        guestsSpinner.getValueFactory().setValue(1);
        specialRequestField.clear();
    }

    private boolean isValidTimeFormat(String time) {
        try {
            LocalTime.parse(time.toUpperCase(), TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void setRestaurantName(String name) {
        restaurantNameLabel.setText("Make a Reservation at " + name);
    }
}
