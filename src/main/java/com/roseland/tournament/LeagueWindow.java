/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.roseland.tournament;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author asus
 */
public class LeagueWindow extends Application {
    
    private League league;
    
    @Override
    public void start(Stage primaryStage) {
        
        TabPane tabPane = new TabPane();

        Tab tableTab = new Tab("Leaguetable");
        Tab resultsTab = new Tab("Results");
        Tab fixturesTab = new Tab("Fixtures");
        Tab statisticsTab = new Tab("Team statistics");
        Tab playGameTab = new Tab("Play next match");
        
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        tabPane.getTabs().addAll(tableTab,playGameTab,fixturesTab,resultsTab,statisticsTab);
        
        StackPane root = new StackPane();
        root.getChildren().add(tabPane);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
    
    public void setLeague(League league){
        this.league = league;
    }
}
