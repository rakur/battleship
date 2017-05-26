/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model;

import hu.unideb.inf.battleship.controller.InvalidPlacingPositionException;
import hu.unideb.inf.battleship.controller.InvalidShootingPositionException;
import hu.unideb.inf.battleship.model.Field;
import hu.unideb.inf.battleship.model.ShipType;
import hu.unideb.inf.battleship.model.GameState;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *The representation of the board game of Battleship.
 * 
 * @author Rakur
 */
public class Game {
    
    /**
     * The logger of the object.
     */
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    /**
     * The playing area of the first player.
     */
    private Field fieldOne;
    /**
     * The playing area of the second player.
     */
    private Field fieldTwo;
    /**
     * The states of the game.
     */
    private GameState gameState;
    /**
     * The unplaced ships of player one.
     */
    private LinkedList<ShipType> playerOneUnplacedShips;
    /**
     * The unplaced ship of player two.
     */
    private LinkedList<ShipType> playerTwoUnplacedShips;
    /**
     * The constructor to initialize a game.
     */
    public Game() {
        this.fieldOne = new Field();
        this.fieldTwo = new Field();
        this.gameState = GameState.PLAYER_ONE_PLACING;

        this.playerOneUnplacedShips = new LinkedList<>();
        this.playerOneUnplacedShips.addLast(ShipType.CARRIER);
        this.playerOneUnplacedShips.addLast(ShipType.BATTLESHIP);
        this.playerOneUnplacedShips.addLast(ShipType.CRUISER);
        this.playerOneUnplacedShips.addLast(ShipType.SUBMARINE);
        this.playerOneUnplacedShips.addLast(ShipType.DESTROYER);

        this.playerTwoUnplacedShips = new LinkedList<>();
        this.playerTwoUnplacedShips.addLast(ShipType.CARRIER);
        this.playerTwoUnplacedShips.addLast(ShipType.BATTLESHIP);
        this.playerTwoUnplacedShips.addLast(ShipType.CRUISER);
        this.playerTwoUnplacedShips.addLast(ShipType.SUBMARINE);
        this.playerTwoUnplacedShips.addLast(ShipType.DESTROYER);
    }

    /**
     * Getter method for player one's field.
     * @return player one's field.
     */
    public Field getFieldOne() {
        return fieldOne;
    }
    /**
     * Setter method for player one's field.
     * @param fieldOne the field to set.
     */
    public void setFieldOne(Field fieldOne) {
        this.fieldOne = fieldOne;
    }
    /**
     * Getter method for player two's field.
     * @return player two's field.
     */
    public Field getFieldTwo() {
        return fieldTwo;
    }
    /**
     * Setter method for player two's field.
     * @param fieldTwo the field to set.
     */
    public void setFieldTwo(Field fieldTwo) {
        this.fieldTwo = fieldTwo;
    }
    /**
     * Getter method for the gamestate.
     * @return the current gamestate.
     */
    public GameState getGameState() {
        return gameState;
    }
    /**
     * Setter method of the gamestate.
     * @param gameState the gamestate to set.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    /**
     * Getter method for player one's unplaced ships.
     * @return a linked list containing player one's unplaced ships.
     */
    public LinkedList<ShipType> getPlayerOneUnplacedShips() {
        return playerOneUnplacedShips;
    }
    /**
     * Setter method for player one's unplaced ships.
     * @param playerOneUnplacedShips the ships to set.
     */
    public void setPlayerOneUnplacedShips(LinkedList<ShipType> playerOneUnplacedShips) {
        this.playerOneUnplacedShips = playerOneUnplacedShips;
    }
    /**
     * The getter method for player two's unplaced ships.
     * @return a linked list containing player two's unplaced ships.
     */
    public LinkedList<ShipType> getPlayerTwoUnplacedShips() {
        return playerTwoUnplacedShips;
    }
    /**
     * Setter method for player two's unplaced ships.
     * @param playerTwoUnplacedShips the ships to set.
     */
    public void setPlayerTwoUnplacedShips(LinkedList<ShipType> playerTwoUnplacedShips) {
        this.playerTwoUnplacedShips = playerTwoUnplacedShips;
    }
        /**
     * Places the current player's ship on their board.
     * 
     * @param x The rowID of the cell.
     * @param y The colID of the cell.
     * @param vertical The position of the ship.
     * @throws InvalidPlacingPositionException when the ship cannot be placed on the current position.
     */
    public void placeShip(int x, int y, boolean vertical) throws InvalidPlacingPositionException {
        if (gameState == GameState.PLAYER_ONE_PLACING) {
            fieldOne.place(playerOneUnplacedShips.getFirst(), x, y, vertical);
            playerOneUnplacedShips.removeFirst();
        } else {
            fieldTwo.place(playerTwoUnplacedShips.getFirst(), x, y, vertical);
            playerTwoUnplacedShips.removeFirst();
        }
        this.switchTurn();
    }
    /**
     * Shoots at a cell and checks if it was a ship.
     * 
     * @param x The rowID of the cell.
     * @param y The colID of the cell.
     * @return Returns true if the shot hit a ship, false otherwise.
     * @throws InvalidShootingPositionException when the current position can not be shot at.
     */
    public boolean shoot(int x, int y) throws InvalidShootingPositionException {
        boolean shootResult;
        if (gameState == GameState.PLAYER_ONE_SHOOTING) {
            shootResult = fieldTwo.shoot(x, y);
        } else {
            shootResult = fieldOne.shoot(x, y);
        }
        this.switchTurn();
        return shootResult;
    }
    /**
     * The method for switching between the gamestates.
     */
    private void switchTurn() {
        switch (this.gameState) {
            case PLAYER_ONE_PLACING:
                if (!playerOneUnplacedShips.isEmpty()) {
                    gameState = GameState.PLAYER_ONE_PLACING;
                } else {
                    if (!playerTwoUnplacedShips.isEmpty()) {
                        gameState = GameState.PLAYER_TWO_PLACING;
                    } else {
                        gameState = GameState.PLAYER_TWO_SHOOTING;
                    }
                }
                break;
            case PLAYER_TWO_PLACING:
                if (!playerTwoUnplacedShips.isEmpty()) {
                    gameState = GameState.PLAYER_TWO_PLACING;
                } else {
                    if (!playerOneUnplacedShips.isEmpty()) {
                        gameState = GameState.PLAYER_ONE_PLACING;
                    } else {
                        gameState = GameState.PLAYER_ONE_SHOOTING;
                    }
                }
                break;
            case PLAYER_ONE_SHOOTING:
                if (fieldTwo.isAllBoatsSunken()) {
                    gameState = GameState.PLAYER_ONE_WON;
                } else {
                    gameState = GameState.PLAYER_TWO_SHOOTING;
                }
                break;
            case PLAYER_TWO_SHOOTING:
                if (fieldOne.isAllBoatsSunken()) {
                    gameState = GameState.PLAYER_TWO_WON;
                } else {
                    gameState = GameState.PLAYER_ONE_SHOOTING;
                }
                break;
        }
        logger.info("Switching turn");
    }
}
