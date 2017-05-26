/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.controller;

/**
 * Thrown when the ship can not be placed on the position.
 * 
 * @author Rakur
 */
public class InvalidPlacingPositionException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE ="Unable to place at current position";
/**
 * The constructor of the exception.
 */    
    public InvalidPlacingPositionException() {
        super (DEFAULT_ERROR_MESSAGE);
    }
}
