/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.model;

import java.io.IOException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides saving and loading feature for the game.
 *
 * @author Rakur
 */
public class GameService {
    /**
     * The logger of the object.
     */
    private static Logger logger = LoggerFactory.getLogger(GameService.class);
    
    /**
     * Saves the game.
     * @param game the game to save.
     * @param fileName the name of the generated file.
     */
    public void saveGame(Game game, String fileName) {
        Gson gson = new Gson();
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.print(gson.toJson(game));
            writer.close();
        } catch (IOException e) {
            logger.error("Exception during saving");
        }
        logger.info("Saving Game");
    }
    /**
     * Loads the saved game if theres any, else it creates a new game.
     * 
     * @param fileName the name of the saved game.
     * @return The saved game or a new game.
     */
    public Game loadGame(String fileName) {

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        try {
            logger.info("Loading Game");
            return gson.fromJson(new InputStreamReader(new FileInputStream(fileName)), Game.class);
        } catch (IOException ex) {
            logger.error("Exception during loading");
            logger.error(ex.getClass().getName());
            logger.info("initializing a new game instead");
            return new Game();
        }
    }

}
