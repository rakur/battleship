/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model;

/**
 * The enumeration of the possible states of the game.
 *
 * @author Rakur
 */
public enum GameState {
    /**
     * The first player is placing their ships.
     */
    PLAYER_ONE_PLACING("Player One Placing"),
    /**
     * The second player is placing their ships.
     */
    PLAYER_TWO_PLACING("Player Two Placing"),
    /**
     * The first player is shooting.
     */
    PLAYER_ONE_SHOOTING("Player One Shooting"),
    /**
     * The second player is placing their ships.
     */
    PLAYER_TWO_SHOOTING("Player Two Shooting"),
    /**
     * The first player won.
     */
    PLAYER_ONE_WON("Player One Won"),
    /**
     * The second player won.
     */
    PLAYER_TWO_WON("Player Two Won");
    /**
     * A string that will contain the current state processable by the players.
     */
    private final String text;
    /**
     * The constructor that will give value to the text.
     * 
     * @param text
     * The current game state.
     */
    GameState(String text) {
        this.text = text;
    }
    /**
     * Returns a string representation of the game state.
     * 
     * @return the current game state.
     */
    @Override
    public String toString() {
        return text;
    }
}
