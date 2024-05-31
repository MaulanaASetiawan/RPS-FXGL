package org.example.rpsfxgl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

<<<<<<<< HEAD:src/main/java/org/example/rpsfxgl/Multiplayer.java
public class Multiplayer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Multiplayer.class.getResource("multiplayer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Destiny Clash: Deluxe Edition");
========
public class Reglog extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Reglog.class.getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Game RPS");
>>>>>>>> 5ffb036b6a2649003a45bf78bc018edcf6b7d1d9:src/main/java/org/example/rpsfxgl/Reglog.java
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}