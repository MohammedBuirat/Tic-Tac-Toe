package com.example.minmaxgui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

public class HelloController {

    static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    private static final int MaxScore = 5;
    int depth = 10;
    int turn = 1;

    int MiniMax(int depth, int turn) {
        if (GameWasEnded()) {
            return 0;
        }
        char c = GetWinner();
        if (c == 'O') {
            return depth;
        }
        if (c == 'X') {
            return (-1 * depth);
        }
        if (turn == 2) {
            int finalScore = -100000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (IsValidMove(i, j)) {
                        board[i][j] = 'O';
                        int score = MiniMax(depth - 1, 1);
                        finalScore = Math.max(score, finalScore);
                        board[i][j] = ' ';
                    }
                }
            }
            return finalScore;
        } else {
            int finalScore = 100000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (IsValidMove(i, j)) {
                        board[i][j] = 'X';
                        int score = MiniMax(depth - 1, 2);
                        finalScore = Math.min(score, finalScore);
                        board[i][j] = ' ';
                    }
                }
            }
            return finalScore;
        }
    }

    public void PreformBotMove() {
        int finalScore = -100000000;
        int ii = 0;
        int jj = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (IsValidMove(i, j)) {
                    board[i][j] = 'O';
                    int score = MiniMax(depth - 1, 1);
                    if (score > finalScore) {
                        finalScore = score;
                        ii = i;
                        jj = j;
                    }
                    board[i][j] = ' ';
                }
            }
        }
        board[ii][jj] = 'O';
        Button button = buttons.get(ii).get(jj);
        button.setText("O");
        button.setDisable(true);
        CheckForWinning();
        if (GameWasEnded()) {
            NextButton.setDisable(false);
            ResultLabel.setText("Draw");
            ResultLabel.setVisible(true);
        }
    }

    public boolean IsValidMove(int i, int j) {
        return board[i][j] == ' ';
    }

    private void SetButtonsColor(char c, Button b1, Button b2, Button b3) {
        if (c == 'X') {
            b1.setStyle("-fx-background-color: " + toRgbString(Color.LIGHTGREEN) + "; -fx-text-fill: #ffffff;");
            b2.setStyle("-fx-background-color: " + toRgbString(Color.LIGHTGREEN) + "; -fx-text-fill: #ffffff;");
            b3.setStyle("-fx-background-color: " + toRgbString(Color.LIGHTGREEN) + "; -fx-text-fill: #ffffff;");
        } else {
            b1.setStyle("-fx-background-color: " + toRgbString(Color.RED) + "; -fx-text-fill: #ffffff;");
            b2.setStyle("-fx-background-color: " + toRgbString(Color.GREEN) + "; -fx-text-fill: #ffffff;");
            b3.setStyle("-fx-background-color: " + toRgbString(Color.GREEN) + "; -fx-text-fill: #ffffff;");
        }
    }

    public char GetWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                return board[i][0];
            }
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j] != ' ') {
                return board[0][j];
            }
        }
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ')) {
            return board[1][1];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
            return board[1][1];
        }
        return ' ';
    }

    public boolean GameWasEnded() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void ClearBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private List<List<Button>> buttons;
    @FXML
    private Button Button1;

    @FXML
    private Button Button2;

    @FXML
    private Button Button3;

    @FXML
    private Button Button4;

    @FXML
    private Button Button5;

    @FXML
    private Button Button6;

    @FXML
    private Button Button7;

    @FXML
    private Button Button8;

    @FXML
    private Button Button9;

    @FXML
    private Button NextButton;

    @FXML
    private Text OScore;

    @FXML
    private Button RestartButton;

    @FXML
    private Label ResultLabel;

    @FXML
    private Text XScore;

    @FXML
    public void initialize() {
        Random random = new Random();
        turn = random.nextInt(2) + 1;
        buttons = new ArrayList<>();
        List<Button> b1 = new ArrayList<>();
        List<Button> b2 = new ArrayList<>();
        List<Button> b3 = new ArrayList<>();
        b1.add(Button1);
        b1.add(Button2);
        b1.add(Button3);
        b2.add(Button4);
        b2.add(Button5);
        b2.add(Button6);
        b3.add(Button7);
        b3.add(Button8);
        b3.add(Button9);
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        ClearBoard();
        ResetBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                var b = buttons.get(i).get(j);
                int finalI = i;
                int finalJ = j;
                b.setOnMouseClicked(event -> handleButtonClick((Button) event.getSource(), finalI, finalJ));
            }
        }
        if (turn == 2) {
            PreformBotMove();
        }
    }

    private void handleButtonClick(Button button, int i, int j) {
        button.setDisable(true);
        button.setText("X");
        board[i][j] = 'X';
        CheckForWinning();

        if (GameWasEnded()) {
            NextButton.setDisable(false);
            ResultLabel.setText("Draw");
            ResultLabel.setVisible(true);
        } else {
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                try {
                    PreformBotMove();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }
    }


    private void CheckForWinning() {
        int newScore = 0;
        char c = GetWinner();
        String winnerName = "";

        if (c == 'X') {
            String temp = XScore.getText();
            newScore = Integer.parseInt(temp) + 1;
            XScore.setText(String.valueOf(newScore));
            ResultLabel.setVisible(true);
            ResultLabel.setText("X is the winner");
            winnerName = "X";
        } else if (c == 'O') {
            String temp = OScore.getText();
            newScore = Integer.parseInt(temp) + 1;
            OScore.setText(String.valueOf(newScore));
            ResultLabel.setVisible(true);
            ResultLabel.setText("O is the winner");
            winnerName = "O";
        } else if (GameWasEnded()) {
            ResultLabel.setText("Draw");
        }
        if (newScore == MaxScore) {
            showWinnerPopup(winnerName);
        } else if (newScore != 0) {
            NextButton.setDisable(false);
            for (var buttonList : buttons) {
                for (var button : buttonList) {
                    button.setDisable(true);
                }
            }
        }
    }

    private void showWinnerPopup(String winnerName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(winnerName + " has won the game!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        }
    }

    @FXML
    protected void RestartButtonClick() {
        OScore.setText("0");
        XScore.setText("0");
        ResetBoard();
    }

    @FXML
    protected void NextButton() {
        ResetBoard();
        Random random = new Random();
        turn = random.nextInt(2) + 1;
        if (turn == 2) {
            PreformBotMove();
        }
    }

    void ResetBoard() {
        resetButtonsColors();
        for (var buttonList : buttons) {
            for (var button : buttonList) {
                button.setText(" ");
                button.setDisable(false);
            }
        }
        ResultLabel.setVisible(false);
        NextButton.setDisable(true);
        ClearBoard();
    }

    public static void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < board[i].length - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < board.length - 1) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    private String toRgbString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void resetButtonsColors() {
        for (var list : buttons) {
            for (var button : list) {
                button.setStyle(""); // Set to default style
            }
        }
    }
}