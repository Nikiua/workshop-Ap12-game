package org.example.demo6;


import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.ResultSet;
import java.sql.Time;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private VBox layout;
    private Label statusLabel;
    private Label timerLabel;
    private Button[][] buttons;
    private boolean playerTurn;
    private String username;
    private Timer time;
    private int timeLeft;
    private boolean endOfGame;

    public Game(String username) {
        this.username = username;
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        statusLabel = new Label("Welcome " + username + "!\n your turn!");
        timerLabel = new Label();
        timerLabel.setFont(new Font(20));
        buttons = new Button[3][3];
        playerTurn = true;
        endOfGame = false;
        timeLeft = 60;
        GridPane grid = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setFont(new Font(30));
                buttons[i][j].setMinSize(100, 100);
                buttons[i][j].setOnAction(e -> handleButtonClick((Button) e.getSource()));
                grid.add(buttons[i][j], j, i);
            }
        }
        layout.getChildren().addAll(statusLabel, grid, timerLabel);
        startTimer();
    }

    private void startTimer() {
        time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (timeLeft > 0) {
                        timeLeft--;
                        timerLabel.setText("Time left: " + timeLeft + " seconds");
                    } else {
                        if (!endOfGame) {
                            endOfGame = true;
                            endGame("Time's up!YOU LOST!!");
                        }
                        time.cancel();
                    }
                });
            }

        }, 1000, 1000);

    }

    private void handleButtonClick(Button button) {
        if (endOfGame || !button.getText().isEmpty()) {
            return;
        }
        if (playerTurn) {
            button.setText("X");
            playerTurn = false;
            statusLabel.setText("Robot's turn!");
            if (checkWin("X")) {
                endGame("It's a drw");
            } else {
                robotMove();
            }
        }
    }

    private void robotMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().isEmpty());
        buttons[row][col].setText("O");
        playerTurn = true;
        statusLabel.setText(playerTurn + "'s turn");
        if (checkWin("O")) {
            endGame("Robot wins!!");
        } else if (isBoardFull()) {
            endGame("It's a draw");
        }
    }

    private boolean checkWin(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) {
                return true;
            }
            if (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol)) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) {
            return true;
        }
        if (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol)) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void endGame(String message) {
        endOfGame = true;
        time.cancel();
        statusLabel.setText(message);
    }

    public VBox getLayout() {
        return layout;
    }

}

