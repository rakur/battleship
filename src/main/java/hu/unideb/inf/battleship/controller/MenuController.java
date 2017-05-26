/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.battleship.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The controller of the main menu.
 *
 * @author Rakur
 */
public class MenuController implements Initializable{
    
    @FXML
    private Button play;
    
    @FXML
    private Button about;
    
    @FXML
    private Button exit;

    @FXML
    private void handlePlay() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) play.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameField.fxml"));
        root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleAbout() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) about.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/About.fxml"));
        root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleExit() {
        Stage stage = (Stage) exit.getScene().getWindow();
        
        stage.close();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
