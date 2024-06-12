package org.example.demo6;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage st;

    @Override
    public void start(Stage stage) throws IOException {
        st = stage;
        showMenu();
    }

    private static void showMenu() {
        Menu menuScreen = new Menu();
        st.setScene(new Scene(menuScreen.getLayout(), 300, 200));
    }

    public static void showGameScreen(String username){
        Game gameScreen = new Game(username);
        st.setScene(new Scene(gameScreen.getLayout(), 400, 450));
        st.setTitle("X_O game");
    }
    public static void main(String[] args) {
        launch();
    }
}