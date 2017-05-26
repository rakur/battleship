/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model.test;

import hu.unideb.inf.battleship.controller.InvalidPlacingPositionException;
import hu.unideb.inf.battleship.model.Game;
import hu.unideb.inf.battleship.model.GameService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author Rakur
 */
public class GameServiceTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Test
    public void loadGameTest() {
        Game gameTest;
        GameService gs = new GameService();
        gameTest = gs.loadGame("nonexistant.json");
        Assert.assertTrue(Arrays.deepEquals(gameTest.getFieldOne().getMap(),new Game().getFieldOne().getMap()));
    }
    @Test
    public void saveGameTest() throws IOException, InvalidPlacingPositionException {
        File file = testFolder.newFile("test.json");
        Game gameTest;
        Game gameTest2 = new Game();
        GameService gs = new GameService();
        gameTest2.placeShip(0, 0, true);
        gs.saveGame(gameTest2, file.toString());
        gameTest = gs.loadGame(file.toString());
        Assert.assertTrue(Arrays.deepEquals(gameTest.getFieldOne().getMap(),gameTest2.getFieldOne().getMap()));
    }

}
