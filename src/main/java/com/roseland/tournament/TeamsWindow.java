/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.roseland.tournament;

import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Map;


/**
 *
 * @author asus
 */
public class TeamsWindow extends Application {
    
    private String name;
    private Map<String,Integer> matchRules;
    private List<Comparator<Team>> tableRules;
    private Map<String,Double> statsToCollect;
    private int matches;
    
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public void setArguments(String leagueName,Map<String,Integer> matchRules
            ,List<Comparator<Team>> tableRules, Map<String,Double> statsToCollect, int matches){
        this.name = leagueName;
        this.matchRules = matchRules;
        this.tableRules = tableRules;
        this.statsToCollect = statsToCollect;
        this.matches = matches;
    }
     
    public String getLeagueName(){
         return this.name;
    }
    
    public void createLeague(){
        
    }
    
}
