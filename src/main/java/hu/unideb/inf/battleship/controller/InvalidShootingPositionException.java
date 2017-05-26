/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.controller;

/**
 * Thrown when the current position can not be shot at.
 * 
 * @author Rakur
 */
public class InvalidShootingPositionException extends Exception{
    private static final String DEFAULT_ERROR_MESSAGE ="Unable to shoot at current position";
 /**
  * The constructor of the exception.
  */   
    public InvalidShootingPositionException() {
        super (DEFAULT_ERROR_MESSAGE);
    }
}
