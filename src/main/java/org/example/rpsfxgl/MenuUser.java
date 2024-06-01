package org.example.rpsfxgl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuUser implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMulti;

    @FXML
    private Button btnSingle;

    private static Akun currentPlayer;

    @FXML
    void MenuSingle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("arena.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) btnSingle.getScene().getWindow();
            Arena arenaController = loader.getController();
            arenaController.setCurrentPlayer(currentPlayer);
            currentStage.close();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void MenuMulti(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("multiplayer.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) btnMulti.getScene().getWindow();
            currentStage.close();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Exit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) btnExit.getScene().getWindow();
            currentStage.close();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Destiny Clash: Deluxe Edition");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSingle.setOnAction(this::MenuSingle);
        btnMulti.setOnAction(this::MenuMulti);
        btnExit.setOnAction(this::Exit);
    }

    public void setCurrentPlayer(Akun player) {
        currentPlayer = player;
    }
}
