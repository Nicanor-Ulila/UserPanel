package com.example.user.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class RestaurantListController {

    @FXML
    private ListView<String> restaurantList;

    // Injected from DashboardController or main parent
    private StackPane mainContent;

    public void setMainContent(StackPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    public void initialize() {
        List<String> restaurants = List.of("Sakura Sushi", "Pasta Palace", "Grill & Chill", "Burgertown");
        restaurantList.getItems().addAll(restaurants);

        restaurantList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = restaurantList.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        loadReservationForm(selected);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadReservationForm(String restaurantName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/reservation-form-view.fxml"));
        Parent form = loader.load();

        ReservationFormController controller = loader.getController();
        controller.setRestaurantName(restaurantName);

        if (mainContent != null) {
            mainContent.getChildren().setAll(form);
        } else {
            System.err.println("mainContent is null. Make sure to call setMainContent before initializing.");
        }
    }
}
