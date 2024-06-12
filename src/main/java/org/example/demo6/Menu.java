package org.example.demo6;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Menu {
    private VBox layout;
    private TextField textField;
    private Button button;

    public Menu() {
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Enter your username please!");
        textField = new TextField();
        button = new Button("start!");

        button.setOnAction(e -> {
            String username = textField.getText();
            if (!username.isEmpty()) {
                Game.showGameScreen(username);
            }
        });
        layout.getChildren().addAll(nameLabel, textField, button);
    }

    public VBox getLayout() {
        return layout;
    }
}
