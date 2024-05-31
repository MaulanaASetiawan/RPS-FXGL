package org.example.rpsfxgl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Text lblSignup;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private Connection conn;

    {
        try {
            conn = Koneksi.DBConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
    }

    void switchToSignUp(MouseEvent event) {
        // Method implementation for switching to signup form
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) lblSignup.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load signup form");
        }
    }

    @FXML
    void Login(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter username and password");
            return;
        }

        // Static admin login check
        if ("Admin".equalsIgnoreCase(username) && "admin123".equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("computer.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.close();

                showAlert(Alert.AlertType.INFORMATION, "Login Successful!", "Welcome, Admin");
                clearFields();
                return; // Exit method after admin login
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load admin form");
                return;
            }
        }
        try {
            String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Akun akun = new Akun(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("health"),
                        rs.getInt("xp"),
                        rs.getInt("level"),
                        rs.getInt("damage")
                );

                FXMLLoader loader = new FXMLLoader(getClass().getResource("multiplayer.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close current stage
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.close();

                showAlert(Alert.AlertType.INFORMATION, "Login Successful!", "Welcome, " + username);
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed!", "Invalid username or password");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to login");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLogin.setOnAction(actionEvent -> {
            Login(actionEvent);
        });

        lblSignup.setOnMouseClicked(this::switchToSignUp);

    }
}
