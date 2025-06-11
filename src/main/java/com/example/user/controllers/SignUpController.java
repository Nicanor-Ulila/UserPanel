package com.example.user.controllers;

import com.example.user.utils.UserDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.scene.paint.Color;

public class SignUpController {
    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill all fields.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        if (UserDatabase.userExists(username)) {
            messageLabel.setText("Username already exists.");
            messageLabel.setTextFill(Color.RED);
        } else {
            UserDatabase.addUser(username, password);
            messageLabel.setText("Account created! Go to Login.");
            messageLabel.setTextFill(Color.GREEN);
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/login-view.fxml"));
            Scene loginScene = new Scene(loader.load(), 400, 300);

            // Reapply CSS
            loginScene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load login view", e);
        }
    }
}
