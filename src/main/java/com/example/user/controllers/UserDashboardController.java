package com.example.user.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserDashboardController {

    @FXML
    private StackPane mainContent;

    private String currentUsername;

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @FXML
    public void initialize() throws IOException {
        showRestaurantList(); // default
    }

    @FXML
    private void showRestaurantList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/restaurant-list-view.fxml"));
        Parent content = loader.load();

        RestaurantListController controller = loader.getController();
        controller.setMainContent(mainContent);

        mainContent.getChildren().setAll(content);
    }

    @FXML
    private void showMyReservations() throws IOException {
        Parent content = FXMLLoader.load(getClass().getResource("/com/example/views/my-reservations-view.fxml"));
        mainContent.getChildren().setAll(content);
    }

    @FXML
    private void showProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/profile-view.fxml"));
        Parent content = loader.load();

        ProfileController controller = loader.getController();
        controller.setUserData(currentUsername);
        controller.setMainContent(mainContent); // Inject mainContent

        mainContent.getChildren().setAll(content);
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) mainContent.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/login-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm()); // ✅ Add your stylesheet

        stage.setScene(scene);
        stage.setTitle("Login");
    }
}
