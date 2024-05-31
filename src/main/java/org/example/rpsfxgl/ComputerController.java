package org.example.rpsfxgl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ComputerController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField comHp;

    @FXML
    private TextField comStage;

    @FXML
    private TextField comName;

    @FXML
    private TextField comXp;

    @FXML
    private TextField comDamage;

    @FXML
    private TableView<Computer> tableView;

    @FXML
    private TableColumn<Computer, Integer> colId;

    @FXML
    private TableColumn<Computer, String> colName;

    private ObservableList<Computer> computers;

    private void add(){
        try(Connection connection = Koneksi.DBConnect()){
            if(comHp.getText() == null || comName.getText() == null || comStage.getText() == null || comXp.getText() == null || comDamage.getText() == null){
                showAlert("Data tidak boleh kosong", Alert.AlertType.ERROR);
                return;
            }

            if(Integer.parseInt(comHp.getText()) > 100 || Integer.parseInt(comHp.getText()) < 0){
                showAlert("Health tidak boleh lebih dari 100 dan kurang dari 0", Alert.AlertType.ERROR);
                return;
            }

            if(Integer.parseInt(comDamage.getText()) < 0){
                showAlert("Damage tidak boleh lebih dari 100 dan kurang dari 0", Alert.AlertType.ERROR);
                return;
            }

            String sql = "INSERT INTO com (name, health, damage, exp, stage) VALUES(?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, comName.getText());
            statement.setInt(2, Integer.parseInt(comHp.getText()));
            statement.setInt(3, Integer.parseInt(comDamage.getText()));
            statement.setInt(4, Integer.parseInt(comXp.getText()));
            statement.setInt(5, Integer.parseInt(comStage.getText()));
            statement.executeUpdate();
            statement.close();
            showAlert("Data berhasil disimpan", Alert.AlertType.INFORMATION);
            clear();
            loadData();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void update(){
        try(Connection connection = Koneksi.DBConnect()){
            if(comHp.getText() == null || comName.getText() == null || comStage.getText() == null || comXp.getText() == null || comDamage.getText() == null){
                showAlert("Data tidak boleh kosong", Alert.AlertType.ERROR);
                return;
            }

            if(Integer.parseInt(comHp.getText()) > 100 || Integer.parseInt(comHp.getText()) < 0){
                showAlert("Health tidak boleh lebih dari 100 dan kurang dari 0", Alert.AlertType.ERROR);
                return;
            }

            if(Integer.parseInt(comDamage.getText()) < 0){
                showAlert("Damage tidak boleh lebih dari 100 dan kurang dari 0", Alert.AlertType.ERROR);
                return;
            }

            String sql = "UPDATE com SET name = ?, health = ?, damage = ?, exp = ?, stage = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, comName.getText());
            statement.setInt(2, Integer.parseInt(comHp.getText()));
            statement.setInt(3, Integer.parseInt(comDamage.getText()));
            statement.setInt(4, Integer.parseInt(comXp.getText()));
            statement.setInt(5, Integer.parseInt(comStage.getText()));
            statement.setInt(6, tableView.getSelectionModel().getSelectedItem().getId());
            statement.executeUpdate();
            statement.close();
            showAlert("Data berhasil diupdate", Alert.AlertType.INFORMATION);
            clear();
            loadData();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void delete(){
        try(Connection connection = Koneksi.DBConnect()){
            String sql = "DELETE FROM com WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tableView.getSelectionModel().getSelectedItem().getId());
            statement.executeUpdate();
            statement.close();
            showAlert("Data berhasil dihapus", Alert.AlertType.INFORMATION);
            clear();
            loadData();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadData(){
        try(Connection connection = Koneksi.DBConnect()){
            computers = FXCollections.observableArrayList();
            String sql = "SELECT * FROM com";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Computer computer = new Computer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("health"),
                        resultSet.getInt("damage"),
                        resultSet.getInt("exp"),
                        resultSet.getInt("stage"));
                computers.add(computer);
            }
            tableView.setItems(computers);
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void clear(){
        comName.clear();
        comStage.clear();
        comHp.clear();
        comXp.clear();
        comDamage.clear();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("comName"));
        loadData();

        btnAdd.setOnAction(actionEvent -> add());
        btnUpdate.setOnAction(actionEvent -> update());
        btnDelete.setOnAction(actionEvent -> delete());
        btnCancel.setOnAction(actionEvent -> clear());
    }
}
