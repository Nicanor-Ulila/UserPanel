package com.example.user.controllers;

import com.example.user.utils.UserDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ChangePasswordController {

    @FXML
    private Label usernameLabel;

    @FXML
    private PasswordField currentPasswordField, newPasswordField, confirmPasswordField;

    @FXML
    private Label statusLabel;

    private String currentUsername;
    private StackPane mainContent;

    public void setUsername(String username) {
        this.currentUsername = username;
        usernameLabel.setText(username);
    }

    public void setMainContent(StackPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    private void handleUpdatePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (currentUsername == null || currentUsername.isEmpty()) {
            statusLabel.setText("User not loaded.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("Please fill in all password fields.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        UserDatabase.User user = UserDatabase.getUser(currentUsername);

        if (user == null) {
            statusLabel.setText("User not found.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        if (!user.getPassword().equals(currentPassword)) {
            statusLabel.setText("Current password is incorrect.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            statusLabel.setText("New passwords do not match.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        if (currentPassword.equals(newPassword)) {
            statusLabel.setText("New password must be different from current password.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        user.setPassword(newPassword);
        statusLabel.setText("Password updated successfully!");
        statusLabel.setTextFill(Color.GREEN);

        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }


    @FXML
    private void handleBackToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/profile-view.fxml"));
            Parent content = loader.load();

            ProfileController controller = loader.getController();
            controller.setUserData(currentUsername);
            controller.setMainContent(mainContent);

            mainContent.getChildren().setAll(content);
        } catch (Exception e) {
            statusLabel.setText("Failed to load profile view.");
            statusLabel.setTextFill(Color.RED);
            e.printStackTrace();
        }
    }
}
