package com.example.user.controllers;

import com.example.user.utils.UserDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.logging.Logger;
import java.util.logging.Level;

public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (UserDatabase.validateUser(username, password)) {
            errorLabel.setText("Login successful!");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/user-dashboard-view.fxml"));
                Scene dashboardScene = new Scene(loader.load(), 600, 400);

                // ✅ Reapply CSS here too
                dashboardScene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

                // Pass username to UserDashboardController
                UserDashboardController controller = loader.getController();
                controller.setCurrentUsername(username);

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.setTitle("User Dashboard");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to load dashboard view", e);
            }

        } else {
            errorLabel.setText("Invalid credentials.");
        }
    }


    @FXML
    private void goToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/signup-view.fxml"));
            Scene signupScene = new Scene(loader.load(), 400, 300);

            // Reapply CSS
            signupScene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(signupScene);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load signup view", e);
        }
    }
}
