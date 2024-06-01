package org.example.rpsfxgl;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MultiplayerController implements Initializable {
    @FXML
    private Label lblFightStatus;
    @FXML
    private Label lblStage;
    @FXML
    private Label lblPlayer1Selection;
    @FXML
    private Label lblPlayer2Selection;

    @FXML
    private Label lblPlayer1Name;
    @FXML
    private ProgressBar pbPlayer1;
    @FXML
    private Button btnRockPlayer1;
    @FXML
    private Button btnPaperPlayer1;
    @FXML
    private Button btnScissorsPlayer1;

    @FXML
    private Label lblPlayer2Name;
    @FXML
    private ProgressBar pbPlayer2;
    @FXML
    private Button btnRockPlayer2;
    @FXML
    private Button btnPaperPlayer2;
    @FXML
    private Button btnScissorsPlayer2;
    public String Player1Name = "Saragih";
    public String Player2Name = "Wilson";

    public static int Player1Choice = -1;
    public static int Player2Choice = -1;
    public static int StageCount = 0;

    ArrayList<String> Player1Data = new ArrayList<String>();
    ArrayList<String> Player2Data = new ArrayList<String>();

    private final Object lock = new Object();

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void getPlayerDataFromDB() {
        try (Connection conn = Koneksi.DBConnect()) {
            String getPlayerQuery = "SELECT Username, Health, Xp, Level, Damage FROM user";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(getPlayerQuery);

            while (resultSet.next()) {
                if (resultSet.getString(1).equals(Player1Name)) {
                    Player1Data.add(resultSet.getString(1));
                    Player1Data.add(resultSet.getString(2));
                    Player1Data.add(resultSet.getString(3));
                    Player1Data.add(resultSet.getString(4));
                    Player1Data.add(resultSet.getString(5));
                } else if (resultSet.getString(1).equals(Player2Name)) {
                    Player2Data.add(resultSet.getString(1));
                    Player2Data.add(resultSet.getString(2));
                    Player2Data.add(resultSet.getString(3));
                    Player2Data.add(resultSet.getString(4));
                    Player2Data.add(resultSet.getString(5));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startMultiplayer() {
        getPlayerDataFromDB();

        lblFightStatus.setText("Start the Fight!");
        lblPlayer1Selection.setText("Player 1");
        lblPlayer2Selection.setText("Player 2");

        lblPlayer1Name.setText(Player1Data.get(0));
        pbPlayer1.setProgress(Double.parseDouble(Player1Data.get(1)) / 100);
        lblPlayer2Name.setText(Player2Data.get(0));
        pbPlayer2.setProgress(Double.parseDouble(Player2Data.get(1)) / 100);

        StageCount = 1;
        lblStage.setText(STR."Stage \{StageCount}");

        player1Fight();
        player2Fight();
        handleFight();
    }

    public static int positiveMod(int value, int mod) {
        return ((value % mod + mod) % mod);
    }

    private void player1Fight() {
        btnRockPlayer1.setOnAction(actionEvent -> {
            synchronized (lock) {
                Player1Choice = 0;
                lock.notify();
            }
        });

        btnPaperPlayer1.setOnAction(actionEvent -> {
            synchronized (lock) {
                Player1Choice = 1;
                lock.notify();
            }
        });

        btnScissorsPlayer1.setOnAction(actionEvent -> {
            synchronized (lock) {
                Player1Choice = 2;
                lock.notify();
            }
        });
    }

    private void player2Fight() {
        btnRockPlayer2.setOnAction(actionEvent -> {
            synchronized (lock) {
                Player2Choice = 0;
                lock.notify();
            }
        });

        btnPaperPlayer2.setOnAction(actionEvent -> {
            synchronized (lock) {
                Player2Choice = 1;
                lock.notify();
            }
        });

        btnScissorsPlayer2.setOnAction(actionEvent -> {
            synchronized (lock) {
                Player2Choice = 2;
                lock.notify();
            }
        });
    }

    private void handleFight() {
        Thread fightThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (Player1Choice == -1 || Player2Choice == -1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    int result = positiveMod(Player1Choice - Player2Choice, 3);

                    if (result == 2) {
                        int player1Health = Integer.parseInt(Player1Data.get(1)) - Integer.parseInt(Player2Data.get(4));
                        System.out.println("Player 1 Health: " + player1Health);
                        Player1Data.set(1, String.valueOf(player1Health));
                        Platform.runLater(() -> pbPlayer1.setProgress(Double.parseDouble(String.valueOf(player1Health)) / 100));
                        Platform.runLater(() -> lblFightStatus.setText("Player 2 Wins!"));
                    } else if (result == 1) {
                        int player2Health = Integer.parseInt(Player2Data.get(1)) - Integer.parseInt(Player1Data.get(4));
                        System.out.println("Player 2 Health: " + player2Health);
                        Player2Data.set(1, String.valueOf(player2Health));
                        Platform.runLater(() -> pbPlayer2.setProgress(Double.parseDouble(String.valueOf(player2Health)) / 100));
                        Platform.runLater(() -> lblFightStatus.setText("Player 1 Wins!"));
                    } else if (result == 0) {
                        Platform.runLater(() -> lblFightStatus.setText("The fight was draw!"));
                    }

                    switch (Player1Choice) {
                        case 0 -> Platform.runLater(() -> lblPlayer1Selection.setText("Player 1 Choose Rock"));
                        case 1 -> Platform.runLater(() -> lblPlayer1Selection.setText("Player 1 Choose Paper"));
                        case 2 -> Platform.runLater(() -> lblPlayer1Selection.setText("Player 1 Choose Scissors"));
                    }

                    switch (Player2Choice) {
                        case 0 -> Platform.runLater(() -> lblPlayer2Selection.setText("Player 2 Choose Rock"));
                        case 1 -> Platform.runLater(() -> lblPlayer2Selection.setText("Player 2 Choose Paper"));
                        case 2 -> Platform.runLater(() -> lblPlayer2Selection.setText("Player 2 Choose Scissors"));
                    }

                    if (Integer.parseInt(Player1Data.get(1)) <= 0) {
                        Platform.runLater(() -> showAlert(Alert.AlertType.INFORMATION, "Fight Status", "Player 2 Has Won the Fight!"));
                        endGame();
                        if (StageCount == 2) {
                            break;
                        }
                    } else if (Integer.parseInt(Player2Data.get(1)) <= 0) {
                        Platform.runLater(() -> showAlert(Alert.AlertType.INFORMATION, "Fight Status", "Player 1 Has Won the Fight!"));
                        endGame();
                        if (StageCount == 2) {
                            break;
                        }
                    }

                    Player1Choice = -1;
                    Player2Choice = -1;
                }
            }
        });
        fightThread.setDaemon(true);
        fightThread.start();
    }

    private void endGame() {
        Platform.runLater(() -> {
            if (StageCount == 1) {
                Player1Data.clear();
                Player2Data.clear();
                getPlayerDataFromDB();

                lblPlayer1Name.setText(Player1Data.get(0));
                pbPlayer1.setProgress(Double.parseDouble(Player1Data.get(1)) / 100);
                lblPlayer2Name.setText(Player2Data.get(0));
                pbPlayer2.setProgress(Double.parseDouble(Player2Data.get(1)) / 100);

                StageCount = 2;
                lblStage.setText("Stage " + StageCount);

                lblFightStatus.setText("Start the Fight!");
                lblPlayer1Selection.setText("Player 1");
                lblPlayer2Selection.setText("Player 2");

                player1Fight();
                player2Fight();
                handleFight();
            } else if (StageCount == 2) {
                String winner;
                if (Integer.parseInt(Player1Data.get(1)) <= 0) {
                    winner = Player2Data.get(0);
                } else {
                    winner = Player1Data.get(0);
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText("The winner is: " + winner);
                alert.setContentText("Do you want to play again or go back to User Menu?");

                ButtonType playAgainButton = new ButtonType("Play Again");
                ButtonType userMenuButton = new ButtonType("User Menu");

                alert.getButtonTypes().setAll(playAgainButton, userMenuButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == playAgainButton) {
                        StageCount = 1;
                        endGame();
                    } else if (result.get() == userMenuButton) {
                        Stage primaryStage = (Stage) lblFightStatus.getScene().getWindow();
                        primaryStage.close();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuUser.fxml"));
                            Parent root = loader.load();
                            Stage menuUserStage = new Stage();
                            menuUserStage.setScene(new Scene(root));
                            menuUserStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startMultiplayer();
    }
}