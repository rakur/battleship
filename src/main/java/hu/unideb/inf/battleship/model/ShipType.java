/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model;

/**
 * The enumeration of the different ship types.
 * 
 * @author Rakur
 */
public enum ShipType {
    /**
     * The ship with five health.
     */
    CARRIER,
    /**
     * The ship with four health.
     */
    BATTLESHIP,
    /**
     * One of the ships with three health.
     */
    CRUISER,
    /**
     * The other ship with three health.
     */
    SUBMARINE,
    /**
     * The ship with two health.
     */
    DESTROYER
}
