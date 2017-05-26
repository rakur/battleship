/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.controller;

import hu.unideb.inf.battleship.model.CellType;
import hu.unideb.inf.battleship.model.Game;
import hu.unideb.inf.battleship.model.GameService;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The controller of the playing field.
 *
 * @author Rakur
 */
public class GameFieldController implements Initializable {

    @FXML
    private Canvas p1Board;

    @FXML
    private Canvas p2Board;

    @FXML
    private Label state;

    @FXML
    private Label info;

    @FXML
    private Button save;

    @FXML
    private Button load;

    @FXML
    private void handleClickOne(MouseEvent e) {
        int x = (int) e.getX() / 60;
        int y = (int) e.getY() / 60;
        switch (game.getGameState()) {
            case PLAYER_ONE_PLACING:
                try {
                    info.setText("");
                    game.placeShip(x, y, (e.getButton() == MouseButton.PRIMARY));

                } catch (InvalidPlacingPositionException ex) {
                    info.setText("Unable to place at position: " + (x + 1) + " - " + (y + 1));
                }

                render();
                break;
            case PLAYER_TWO_SHOOTING:
                if (e.getButton() == MouseButton.PRIMARY) {
                    try {
                        info.setText("");
                        game.shoot(x, y);

                    } catch (InvalidShootingPositionException ex) {
                        info.setText("Unable to shoot at position: " + (x + 1) + " - " + (y + 1));
                    }
                }
                render();
                break;
            case PLAYER_ONE_SHOOTING:
                info.setText("Shoot on the other board");
                break;
            case PLAYER_TWO_PLACING:
                info.setText("Place on the other board");
                break;
        }
    }

    @FXML
    private void handleClickTwo(MouseEvent e) {
        int x = (int) e.getX() / 60;
        int y = (int) e.getY() / 60;
        switch (game.getGameState()) {
            case PLAYER_TWO_PLACING:
                try {
                    info.setText("");
                    game.placeShip(x, y, (e.getButton() == MouseButton.PRIMARY));
                    

                } catch (InvalidPlacingPositionException ex) {
                    info.setText("Unable to place at position: " + (x+1) + " - " + (y+1));
                }

                render();
                break;
            case PLAYER_ONE_SHOOTING:
                if (e.getButton() == MouseButton.PRIMARY) {
                    try {
                        info.setText("");
                        game.shoot(x, y);
                        
                    } catch (InvalidShootingPositionException ex) {
                        info.setText("Unable to shoot at position: " + (x+1) + " - " + (y+1));
                    }
                }
                render();
                break;
            case PLAYER_TWO_SHOOTING:
                info.setText("Shoot on the other board");
                break;
            case PLAYER_ONE_PLACING:
                info.setText("Place on the other board");
                break;
        }
    }

    @FXML
    private void handleSave() {
        gs.saveGame(this.game, FILENAME);
    }

    @FXML
    private void handleLoad() {
        this.game = gs.loadGame(FILENAME);
        render();
        info.setText("");
    }

    private Game game;
    private GameService gs;
    private GraphicsContext gcPlayer1;
    private GraphicsContext gcPlayer2;
    private static final String FILENAME = "save.json";

    private Alert alert;
    private ButtonType menu;
    private ButtonType rematch;
    /**
     * Shows everything that should be visible in the current gamestate.
     */
    public void render() {
        refreshStateLabel();
        clearBoards();
        switch (game.getGameState()) {
            case PLAYER_ONE_PLACING:
                drawPlayerOneShips();
                break;
            case PLAYER_TWO_PLACING:
                drawPlayerTwoShips();
                break;
            case PLAYER_ONE_SHOOTING:
                drawPlayerOneShots();
                drawPlayerTwoShots();
                break;
            case PLAYER_TWO_SHOOTING:
                drawPlayerOneShots();
                drawPlayerTwoShots();
                break;
            case PLAYER_ONE_WON:
                alert.setHeaderText("Player One Won");
                Optional<ButtonType> resultOne = alert.showAndWait();
                if (resultOne.get() == rematch) {
                    game = new Game();
                    render();
                } else {
                    Stage stage;
                    Parent root;
                    stage = (Stage) info.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        root = null;
                    }

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                break;
            case PLAYER_TWO_WON:
                alert.setHeaderText("Player Two Won");
                Optional<ButtonType> resultTwo = alert.showAndWait();
                if (resultTwo.get() == rematch) {
                    game = new Game();
                    render();
                } else {
                    Stage stage;
                    Parent root;
                    stage = (Stage) info.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        root = null;
                    }

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                break;
        }
    }
    /**
     * Inform the players about the current gamestate.
     */
    public void refreshStateLabel() {
        state.setText(game.getGameState().toString());
    }
    /**
     * Draws player one's ships on their board.
     */
    public void drawPlayerOneShips() {
        gcPlayer1.setFill(Color.GRAY);
        CellType[][] map = game.getFieldOne().getMap();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[j][i] != CellType.WATER) {
                    gcPlayer1.fillRect(i * 60, j * 60, 59, 59);
                }
            }
        }
    }
    /**
     * Draws player two's ships on ther board.
     */
    public void drawPlayerTwoShips() {
        gcPlayer2.setFill(Color.GRAY);
        CellType[][] map = game.getFieldTwo().getMap();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[j][i] != CellType.WATER) {
                    gcPlayer2.fillRect(i * 60, j * 60, 59, 59);
                }
            }
        }
    }
    /**
     * Draw player one's shots on player two's board.
     */
    public void drawPlayerOneShots() {
        CellType[][] map = game.getFieldOne().getMap();
        gcPlayer1.setStroke(Color.CORAL);
        gcPlayer1.setFill(Color.WHITE);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[j][i] == CellType.HIT) {
                    gcPlayer1.strokeLine((i * 60) + 10, (j * 60) + 10, (i * 60) + 49, (j * 60) + 49);
                    gcPlayer1.strokeLine((i * 60) + 49, (j * 60) + 10, (i * 60) + 10, (j * 60) + 49);
                }
                if (map[j][i] == CellType.MISS) {
                    gcPlayer1.fillOval((i * 60) + 15, (j * 60) + 15, 29, 29);
                }
            }
        }
    }
    /**
     * Draws player two's shots on player one's board.
     */
    public void drawPlayerTwoShots() {
        CellType[][] map = game.getFieldTwo().getMap();
        gcPlayer2.setStroke(Color.CORAL);
        gcPlayer2.setFill(Color.WHITE);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[j][i] == CellType.HIT) {
                    gcPlayer2.strokeLine((i * 60) + 10, (j * 60) + 10, (i * 60) + 49, (j * 60) + 49);
                    gcPlayer2.strokeLine((i * 60) + 49, (j * 60) + 10, (i * 60) + 10, (j * 60) + 49);
                }
                if (map[j][i] == CellType.MISS) {
                    gcPlayer2.fillOval((i * 60) + 15, (j * 60) + 15, 29, 29);
                }
            }
        }
    }
    /**
     * Clears the board of all drawings.
     */
    public void clearBoards() {
        gcPlayer1.setFill(Color.rgb(70, 80, 255));
        gcPlayer2.setFill(Color.rgb(70, 80, 255));
        gcPlayer1.fillRect(0, 0, p1Board.getWidth(), p1Board.getHeight());
        gcPlayer2.fillRect(0, 0, p2Board.getWidth(), p2Board.getHeight());
        gcPlayer1.setStroke(Color.WHITE);
        gcPlayer1.setLineWidth(3);
        gcPlayer2.setStroke(Color.WHITE);
        gcPlayer2.setLineWidth(3);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gcPlayer1.strokeRect(i * 60, j * 60, 59, 59);
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gcPlayer2.strokeRect(i * 60, j * 60, 59, 59);
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gs = new GameService();
        game = new Game();
        info.setText("");
        gcPlayer1 = p1Board.getGraphicsContext2D();
        gcPlayer2 = p2Board.getGraphicsContext2D();
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Game Over!");
        alert.setContentText("What to do next?");
        rematch = new ButtonType("Rematch");
        menu = new ButtonType("Menu", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(rematch, menu);
        render();
    }

}
