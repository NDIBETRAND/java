package com.example.gym;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button big_login;

    @FXML
    private Button big_signup;

    @FXML
    private Button login_button;

    @FXML
    private AnchorPane login_pane;

    @FXML
    private PasswordField login_password;

    @FXML
    TextField login_username;

    @FXML
    private Button signup_button;

    @FXML
    private TextField signup_email;

    @FXML
    private AnchorPane signup_pane;

    @FXML
    private TextField signup_password;

    @FXML
    private TextField signup_username;

    @FXML
    private ProgressIndicator loadingSpinner;

    @FXML
    private ProgressIndicator loadingSpinner1;

    @FXML
    private Label statusLabel;

    @FXML
    private Label statusLabel1;


    public void initialize() {
        hideAllSpinners();
        clearAllStatusLabels();
    }

    public void loginShow() {
        login_pane.setVisible(true);
        signup_pane.setVisible(false);
    }

    public void signupShow() {
        login_pane.setVisible(false);
        signup_pane.setVisible(true);
    }

    public void handleSignup() {
        String username = signup_username.getText();
        String email = signup_email.getText();
        String password = signup_password.getText();

        if (isInputEmpty(username, email, password)) {
            setStatusLabel(statusLabel1, "Please fill in all fields.");
            return;
        }

        if (password.length() > 64) { // Database limit assumption
            setStatusLabel(statusLabel1, "Password is too long. Maximum 64 characters allowed.");
            return;
        }

        showLoadingSpinner(loadingSpinner1);

        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate network delay
                Platform.runLater(() -> {
                    hideLoadingSpinner(loadingSpinner1);

                    mysqlconnection auth = new mysqlconnection();
                    boolean success = auth.signup(username, email, password);

                    setStatusLabel(
                            statusLabel1,
                            success ? "Signup successful!" : "Signup failed."
                    );

                    if (success) { // Navigate to a new scene after successful signup
                        navigateToDashboard();
                    }

                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void handleLogin() {
        if (isInputEmpty(login_username.getText(), login_password.getText())) {
            setStatusLabel(statusLabel, "Please fill in all fields.");
            return;
        }

        showLoadingSpinner(loadingSpinner);

        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate network delay
                Platform.runLater(() -> {
                    hideLoadingSpinner(loadingSpinner);

                    mysqlconnection auth = new mysqlconnection();
                    boolean success = auth.login(
                            login_username.getText(),
                            login_password.getText()
                    );

                    setStatusLabel(
                            statusLabel,
                            success ? "Login successful!" : "Login failed."
                    );

                    if (success) { // Navigate to a new scene after successful login
                      navigateToDashboard();
                    }

                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gym/Homepage.fxml")); // Leading slash
            Scene dashboardScene = new Scene(loader.load());

            Stage currentStage = (Stage) login_username.getScene().getWindow(); // Current stage reference
            currentStage.setScene(dashboardScene);
            currentStage.setTitle("Dashboard");

        } catch (IOException e) {
            e.printStackTrace(); // Log error for debugging

            // Optional: Show error dialog to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Navigation Error");
            alert.setContentText("Failed to navigate to the dashboard.");
            alert.showAndWait();
        }
    }

    private void showLoadingSpinner(ProgressIndicator spinner) {
        spinner.setVisible(true);
    }

    private void hideLoadingSpinner(ProgressIndicator spinner) {
        spinner.setVisible(false);
    }

    private void setStatusLabel(Label label, String message) {
        label.setText(message);
    }

    private boolean isInputEmpty(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void hideAllSpinners() {
        loadingSpinner.setVisible(false);
        loadingSpinner1.setVisible(false);
    }

    private void clearAllStatusLabels() {
        statusLabel.setText("");
        statusLabel1.setText("");
    }
}
