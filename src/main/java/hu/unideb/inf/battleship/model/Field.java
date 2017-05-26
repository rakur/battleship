/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model;

import hu.unideb.inf.battleship.controller.InvalidPlacingPositionException;
import hu.unideb.inf.battleship.controller.InvalidShootingPositionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The representation of the playing area and additional methods to simulate
 * proper behavior.
 *
 * @author Rakur
 */
public class Field {
    
    /**
     * The logger of the object. 
     */
    private static Logger logger = LoggerFactory.getLogger(Field.class);
    
    /**
    * The 10x10 block playing area of a game. 
    */
    private CellType[][] map;
    /**
     * Fills up the field with {@link hu.unideb.inf.battleship.model.CellType#WATER}.
     */
    public Field() {
        map = new CellType[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = CellType.WATER;
            }
        }
    }
    /**
     * Getter method for the map.
     * @return the map
     */
    public CellType[][] getMap() {
        return map;
    }
    /**
     * Setter method for the map.
     * 
     * @param map the map to set
     */
    public void setMap(CellType[][] map) {
        this.map = map;
    }
    /**
     * Checks if all ships has been sunk on the field.
     * 
     * @return true if there are no more cell that contain any type of ship,
     * false otherwise
     */
    public boolean isAllBoatsSunken() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[j][i] != CellType.WATER
                        && map[j][i] != CellType.HIT
                        && map[j][i] != CellType.MISS) {
                    return false;
                }
            }
        }
        logger.info("All boats have been sunk");
        return true;
        
    }
    /**
     * Checks if the player can place the ship on the current location.
     * 
     * @param x rowID of the cell
     * @param y colID of the cell
     * @param length length of the current ship
     * @param vertical the position of the ship
     * @return true if the actual ship is no longer than the remaining space,
     * and there is no collision with another already placed ship, 
     * false otherwise
     */
    private boolean canPlace(int x, int y, int length, boolean vertical) {
        if (vertical) {
            if (y + length > map.length) {
                logger.warn("Not enough space to place");
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (map[y + i][x] != CellType.WATER) {
                    logger.warn("Cannot place on another ship");
                    return false;
                }
            }
        } else {
            if (x + length > map[0].length) {
                logger.warn("Not enough space to place");
                return false;
            }

            for (int i = 0; i < length; i++) {
                if (map[y][x + i] != CellType.WATER) {
                    logger.warn("Cannot place on another ship");
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Places the ship on the field.
     * 
     * @param shipType the type of the current ship
     * @param x the rowID of the cell
     * @param y the colID of the cell
     * @param vertical the position of the ship
     * @throws InvalidPlacingPositionException when the ship cannot be placed on the current position
     */
    public void place(ShipType shipType, int x, int y, boolean vertical) throws InvalidPlacingPositionException {
        int length;
        CellType cellType;
        switch (shipType) {
            case CARRIER:
                length = 5;
                cellType = CellType.CARRIER;
                break;
            case BATTLESHIP:
                length = 4;
                cellType = CellType.BATTLESHIP;
                break;
            case CRUISER:
                length = 3;
                cellType = CellType.CRUISER;
                break;
            case SUBMARINE:
                length = 3;
                cellType = CellType.SUBMARINE;
                break;
            case DESTROYER:
                length = 2;
                cellType = CellType.DESTROYER;
                break;
            default:
                length = 2;
                cellType = CellType.DESTROYER;
        }
        if (!this.canPlace(x, y, length, vertical)) {
            throw new InvalidPlacingPositionException();
        }
        if (vertical) {
            for (int i = 0; i < length; i++) {
                this.map[y + i][x] = cellType;
            }
        } else {
            for (int i = 0; i < length; i++) {
                this.map[y][x + i] = cellType;
            }
        }
        logger.info("{} has been placed", shipType);
    }
    /**
     * Checks if the choosen cell is shotable.
     * 
     * @param x the rowID of the cell
     * @param y the colID of the cell
     * @return true if the cell has not been shot at yet false otherwise
     */
    private boolean canShoot(int x, int y) {
        return map[y][x] != CellType.HIT && map[y][x] != CellType.MISS;
    }
    /**
     * Shoots at the choosen cell, changes the cell whether it was a ship or not.
     * 
     * @param x the rowID of the cell
     * @param y the colID of the cell
     * @return true if the cell contains a ship and changes the type to hit, false otherwise and changes the type to miss
     * @throws InvalidShootingPositionException when the choosen cell cannot be shot at
     */
    public boolean shoot(int x, int y) throws InvalidShootingPositionException {
        if (!this.canShoot(x, y)) {
            logger.warn("Can not shoot on {}{} position", x,y);
            throw new InvalidShootingPositionException();
        }
        if (map[y][x] == CellType.WATER) {
            map[y][x] = CellType.MISS;
            logger.info("The shot missed!");
            return false;

        } else {
            map[y][x] = CellType.HIT;
            logger.info("The shot hit!");
            return true;
        }
    }

}
