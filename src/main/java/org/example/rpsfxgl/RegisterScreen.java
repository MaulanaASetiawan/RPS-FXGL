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

public class RegisterScreen implements Initializable {

    @FXML
    private Button btnSignUp;

    @FXML
    private Text lblSignin;

    @FXML
    private PasswordField txtConfirm;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    Connection conn;

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
        txtConfirm.clear();
    }

    void switchToSignIn(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) lblSignin.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load signup form");
        }
    }

    @FXML
    void CreateAkun(ActionEvent event) {
        String Username = txtUsername.getText();
        String Password = txtPassword.getText();
        String ConfirmPassword = txtConfirm.getText();

        int Health = 100;
        int Xp = 0;
        int Level = 1;
        int Damage = 10;
        if (Username.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
            return;
        }
        if (!Password.equals(ConfirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Passwords do not match");
            return;
        }
        boolean usernameExists = false;
        String checkQuery = "SELECT COUNT(*) FROM user WHERE Username = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, Username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    usernameExists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error!", "Error checking username availability");
            return;
        }
        if (usernameExists) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Username already exists");
            clearFields();
            return;
        }
        try {
            String query = "INSERT INTO User (Username, Password, Health, Xp, Level, Damage) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, Username);
            pstmt.setString(2, Password);
            pstmt.setInt(3, Health);
            pstmt.setInt(4, Xp);
            pstmt.setInt(5, Level);
            pstmt.setInt(6, Damage);
            pstmt.executeUpdate();

            Akun akun = new Akun(Username, Password, Health, Xp, Level, Damage);
            akun.setUsername(Username);
            akun.setPassword(Password);
            akun.setHealth(Health);
            akun.setXp(Xp);
            akun.setLevel(Level);
            akun.setDamage(Damage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful!", "User registered successfully");
            Stage currentStage = (Stage) btnSignUp.getScene().getWindow();
            currentStage.close();

            clearFields();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error!", "User registration failed");
            clearFields();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSignUp.setOnAction(actionEvent -> {
            CreateAkun(actionEvent);
        });
        lblSignin.setOnMouseClicked(this::switchToSignIn);
    }
}
