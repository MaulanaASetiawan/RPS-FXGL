package org.example.rpsfxgl;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListComputerController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnFight;

    @FXML
    private TableColumn<Computer, Integer> colDamage;

    @FXML
    private TableColumn<Computer, Integer> colHealth;

    @FXML
    private TableColumn<Computer, String> colName;

    @FXML
    private TableColumn<Computer, Integer> colXp;

    @FXML
    private TableView<Computer> tableView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(new PropertyValueFactory<>("comName"));
        colHealth.setCellValueFactory(new PropertyValueFactory<>("comHp"));
        colDamage.setCellValueFactory(new PropertyValueFactory<>("comDamage"));
        colXp.setCellValueFactory(new PropertyValueFactory<>("comXp"));

        btnBack.setOnAction(actionEvent -> {
            backToArena();
        });

        btnFight.setOnAction(actionEvent -> {
            Computer computer = tableView.getSelectionModel().getSelectedItem();
            if (computer != null) {
                openFightForm(computer, Arena.getCurrentPlayer());
            }
        });

    }

    public void setComputerData(ObservableList<Computer> data) {
        tableView.setItems(data);
    }

    private void openFightForm(Computer computer, Akun player) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("singleplayer.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Fight");
            stage.setScene(scene);

            FightSinglePlayer controller = loader.getController();
            controller.setFightInfo(computer, player);

            Stage currentStage = (Stage) btnFight.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backToArena() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("arena.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Arena");
            stage.setScene(scene);

            Arena.getCurrentPlayer();

            Stage currentStage = (Stage) btnBack.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
