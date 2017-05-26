/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model;

/**
 * The enumeration of what a cell can represent on the {@link hu.unideb.inf.battleship.model.Field}.
 *
 * @author Rakur
 */
public enum CellType {
    /**
     * The cell represents water.
     */
    WATER,
    /**
     * The cell represents a part of the {@link hu.unideb.inf.battleship.model.ShipType#DESTROYER}.
     */
    DESTROYER,
    /**
     * The cell represents a part of {@link hu.unideb.inf.battleship.model.ShipType#CRUISER}.
     */
    CRUISER,
    /**
     * The cell represents a part of the {@link hu.unideb.inf.battleship.model.ShipType#SUBMARINE}.
     */
    SUBMARINE,
    /**
     * The cell represents a part of the {@link hu.unideb.inf.battleship.model.ShipType#BATTLESHIP}.
     */
    BATTLESHIP,
    /**
     * The cell represents a part of the {@link hu.unideb.inf.battleship.model.ShipType#CARRIER}.
     */
    CARRIER,
    /**
     * The cell was already shot at and was a ship.
     */
    HIT,
    /**
     * The cell was already shot at and it was not a ship.
     */
    MISS
}
