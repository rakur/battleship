/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model.test;

import hu.unideb.inf.battleship.model.Game;
import hu.unideb.inf.battleship.controller.InvalidPlacingPositionException;
import hu.unideb.inf.battleship.controller.InvalidShootingPositionException;
import hu.unideb.inf.battleship.model.CellType;
import hu.unideb.inf.battleship.model.Field;
import hu.unideb.inf.battleship.model.GameState;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Rakur
 */
public class GameTest {
    @Test
    public void shootWaterTest() throws InvalidShootingPositionException {
        Game gameTest = new Game();
        Assert.assertFalse(gameTest.shoot(0, 0));
        
    }
    @Test(expected=InvalidShootingPositionException.class)
    public void shootingExceptionTest() throws InvalidShootingPositionException {
        Game gameTest = new Game();
        gameTest.shoot(0, 0);
        gameTest.shoot(0, 0);
    }
    @Test
    public void placeShipTest() throws InvalidPlacingPositionException {
        Game gameTest = new Game();
        gameTest.placeShip(0, 0, true);
        Assert.assertTrue(gameTest.getFieldOne().getMap()[0][0]==CellType.CARRIER);
        Assert.assertTrue(gameTest.getFieldOne().getMap()[4][0]==CellType.CARRIER);
        Assert.assertFalse(gameTest.getFieldOne().getMap()[0][1]==CellType.CARRIER);
        Assert.assertTrue(gameTest.getFieldOne().getMap()[0][1]==CellType.WATER);
    }
    @Test (expected=InvalidPlacingPositionException.class)
    public void placingExceptionTest() throws InvalidPlacingPositionException {
        Game gameTest = new Game();
        gameTest.placeShip(0,0,true);
        gameTest.placeShip(0,0,true);
    }
    @Test (expected=InvalidPlacingPositionException.class)
    public void notEnoughSpaceTest() throws InvalidPlacingPositionException {
        Game gameTest = new Game();
        gameTest.placeShip(8,8,true);
    }
    @Test
    public void placingAllShipstest() throws InvalidPlacingPositionException {
        Game gameTest = new Game();
        gameTest.placeShip(0,0,true);
        gameTest.placeShip(1,0,true);
        gameTest.placeShip(2,0,true);
        gameTest.placeShip(3,0,true);   
        gameTest.placeShip(4,0,false);
        Assert.assertTrue(gameTest.getPlayerOneUnplacedShips().isEmpty());
    }
    @Test
    public void playerTwoPlacing() throws InvalidPlacingPositionException {
        Game gameTest = new Game();
        gameTest.setGameState(GameState.PLAYER_TWO_PLACING);
        gameTest.placeShip(0,0,false);
        Assert.assertTrue(gameTest.getFieldTwo().getMap()[0][0]==CellType.CARRIER);
    }
    @Test
    public void fullTest() throws InvalidPlacingPositionException, InvalidShootingPositionException {
        Game gameTest = new Game();
        Field field = new Field();
        Assert.assertTrue(gameTest.getGameState()==GameState.PLAYER_ONE_PLACING);
        gameTest.placeShip(0,0,true);
        gameTest.placeShip(1,0,true);
        gameTest.placeShip(2,0,true);
        gameTest.placeShip(3,0,true);
        Assert.assertFalse(gameTest.getPlayerOneUnplacedShips().isEmpty());
        gameTest.placeShip(4,0,true);
        Assert.assertTrue(gameTest.getGameState()==GameState.PLAYER_TWO_PLACING);
        gameTest.placeShip(0,0,true);
        gameTest.placeShip(1,0,true);
        gameTest.placeShip(2,0,true);
        gameTest.placeShip(3,0,true);   
        Assert.assertFalse(gameTest.getPlayerTwoUnplacedShips().isEmpty());
        gameTest.placeShip(4,0,true);
        Assert.assertTrue(gameTest.getPlayerTwoUnplacedShips().isEmpty());
        Assert.assertTrue(gameTest.getGameState()==GameState.PLAYER_ONE_SHOOTING);
        gameTest.shoot(0, 0);
        Assert.assertFalse(gameTest.getFieldTwo().isAllBoatsSunken());
        Assert.assertTrue(gameTest.getGameState()==GameState.PLAYER_TWO_SHOOTING);
        gameTest.shoot(0, 0);
        Assert.assertFalse(gameTest.getFieldOne().isAllBoatsSunken());
        gameTest.setFieldTwo(field);
        Assert.assertTrue(gameTest.getFieldTwo().isAllBoatsSunken());
        gameTest.shoot(0,0);
        Assert.assertTrue(gameTest.getGameState()==GameState.PLAYER_ONE_WON);
        
    }
}
