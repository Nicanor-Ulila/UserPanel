package com.example.user.controllers;

import com.example.user.utils.UserDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ProfileController {

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField nameField, emailField;

    @FXML
    private Label statusLabel;

    private String currentUsername;
    private StackPane mainContent;  // Added for in-dashboard view loading

    public void setUserData(String username) {
        this.currentUsername = username;
        UserDatabase.User user = UserDatabase.getUser(username);

        if (user != null) {
            usernameLabel.setText(user.getUsername());
            nameField.setText(user.getFullName());
            emailField.setText(user.getEmail());
            statusLabel.setText("");
        } else {
            statusLabel.setText("User not found.");
            statusLabel.setTextFill(Color.RED);
        }
    }

    public void setMainContent(StackPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    private void handleSaveChanges() {
        String fullName = nameField.getText();
        String email = emailField.getText();

        if (currentUsername == null || currentUsername.isEmpty()) {
            statusLabel.setText("Error: User not loaded.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        if (fullName.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        // Basic email validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            statusLabel.setText("Invalid email format.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        UserDatabase.User user = UserDatabase.getUser(currentUsername);
        if (user != null) {
            boolean isChanged = false;

            if (!fullName.equals(user.getFullName())) {
                user.setFullName(fullName);
                isChanged = true;
            }

            if (!email.equals(user.getEmail())) {
                user.setEmail(email);
                isChanged = true;
            }

            if (isChanged) {
                statusLabel.setText("Profile updated successfully!");
                statusLabel.setTextFill(Color.GREEN);
            } else {
                statusLabel.setText("No changes detected.");
                statusLabel.setTextFill(Color.ORANGE);
            }
        } else {
            statusLabel.setText("User not found.");
            statusLabel.setTextFill(Color.RED);
        }
    }



    @FXML
    private void handleChangePassword() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/change-password-view.fxml"));
            Parent content = loader.load();

            ChangePasswordController controller = loader.getController();
            controller.setUsername(currentUsername);
            controller.setMainContent(mainContent); // Set mainContent for view switching

            mainContent.getChildren().setAll(content);
        } catch (Exception e) {
            statusLabel.setText("Failed to load change password view.");
            statusLabel.setTextFill(Color.RED);
            e.printStackTrace();
        }
    }
}
