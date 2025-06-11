package com.example.user.controllers;

import com.example.user.data.ReservationData;
import com.example.user.models.Reservation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyReservationsController {

    @FXML
    private ToggleGroup filterGroup;

    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private TableColumn<Reservation, String> restaurantColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> dateColumn;

    @FXML
    private TableColumn<Reservation, String> timeColumn;

    @FXML
    private TableColumn<Reservation, Integer> guestsColumn;

    @FXML
    private TableColumn<Reservation, String> statusColumn;

    @FXML
    private TableColumn<Reservation, Void> actionColumn; // ✅ New column for Cancel button

    @FXML
    private Label emptyLabel;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private FilteredList<Reservation> filteredList;

    @FXML
    public void initialize() {
        // Set up TableView columns
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));

        dateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDateTime().toLocalDate())
        );

        timeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateTime().toLocalTime().format(TIME_FORMATTER))
        );

        guestsColumn.setCellValueFactory(new PropertyValueFactory<>("guests"));

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Action column (Cancel button)
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button cancelBtn = new Button("Cancel");

            {
                cancelBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                cancelBtn.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    reservation.setStatus("Cancelled");
                    reservationsTable.refresh(); // Update table view
                    filterReservations("Upcoming"); // Remove cancelled item from current tab
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }

                Reservation reservation = getTableView().getItems().get(getIndex());
                if ("Upcoming".equalsIgnoreCase(reservation.getStatus())) {
                    setGraphic(cancelBtn);
                } else {
                    setGraphic(null);
                }
            }
        });

        // Get shared list and wrap it in a FilteredList
        filteredList = new FilteredList<>(ReservationData.getInstance().getReservations());

        // Bind TableView to filtered list
        reservationsTable.setItems(filteredList);

        // Toggle group listener for filtering
        filterGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null && oldToggle != null) {
                filterGroup.selectToggle(oldToggle); // prevent deselecting all
                return;
            }

            ToggleButton selectedBtn = (ToggleButton) newToggle;
            filterReservations(selectedBtn.getText());
        });

        // Set default filter
        filterGroup.selectToggle(filterGroup.getToggles().get(0));
    }

    private void filterReservations(String status) {
        filteredList.setPredicate(reservation ->
                reservation.getStatus().equalsIgnoreCase(status)
        );

        emptyLabel.setVisible(filteredList.isEmpty());
    }
}
