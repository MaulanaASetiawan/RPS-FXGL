package org.example.rpsfxgl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Arena implements Initializable {

    @FXML
    private Button btnStage1;

    @FXML
    private Button btnStage2;

    @FXML
    private Button btnStage3;

    @FXML
    private Button btnStage4;

    @FXML
    private Button btnStage5;

    @FXML
    private Button btnStage6;

    @FXML
    private Button btnStage7;

    @FXML
    private Button btnStageBoss;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblPlayerName;

    @FXML
    private Label lblPlayerXp;

    @FXML
    private Label lblPlayerLevel;

    private static Akun currentPlayer;

    public void setCurrentPlayer(Akun player) {
        currentPlayer = player;
        lblPlayerName.setText("Name: " + currentPlayer.getUsername());
        lblPlayerXp.setText("XP: " + currentPlayer.getXp());
        lblPlayerLevel.setText("Level: " + currentPlayer.getLevel());
    }

    public static Akun getCurrentPlayer() {
        return currentPlayer;
    }

    public void openListComputer(ObservableList<Computer> data) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listComputer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("List Computer");
            stage.setScene(scene);

            ListComputerController controller = fxmlLoader.getController();
            controller.setComputerData(data);

            Stage currentStage = (Stage) btnStage1.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (currentPlayer != null) {
            setCurrentPlayer(currentPlayer);
        }

        btnStage1.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(1));
        });

        btnStage2.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(2));
        });

        btnStage3.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(3));
        });

        btnStage4.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(4));
        });

        btnStage5.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(5));
        });

        btnStage6.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(6));
        });

        btnStage7.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(7));
        });

        btnStageBoss.setOnAction(actionEvent -> {
            openListComputer(getComputerDataForStage(8));
        });

        btnLogout.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuUser.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(scene);

                Stage currentStage = (Stage) btnLogout.getScene().getWindow();
                currentStage.close();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static ObservableList<Computer> getComputerDataForStage(int stage) {
        ObservableList<Computer> computers = FXCollections.observableArrayList();
        String query = "SELECT * FROM com WHERE stage = ?";

        try (Connection connection = Koneksi.DBConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, stage);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Computer computer = new Computer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("health"),
                        resultSet.getInt("damage"),
                        resultSet.getInt("exp"),
                        resultSet.getInt("stage")
                );
                computers.add(computer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computers;
    }
}