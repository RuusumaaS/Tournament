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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;

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
        
        createTableTab(tableTab,this.league);
        
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
        
        primaryStage.setTitle("League");
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
    
    public void setLeague(League league){
        this.league = league;
    }
    
    public void tableTitles(GridPane pane,ArrayList<String> tableContents){
        int column = 0;
        for(String str : tableContents){
            pane.add(new Label(str),column,0);
            ++column;
        }
        
    }
    
    public void createTableTab(Tab tab, League league){
        Label topLabel = new Label("League table:");
        GridPane table = new GridPane();
        table.setHgap(10);
        table.setVgap(10);
        league.sortLeagueTable();
        
        ArrayList<String> tableContents = league.getTableContents();
        tableTitles(table,tableContents);
        
        int positionNum = 1;
        
        for(Team team : league.getTeams()){
            int column = 0;
            for(String str : tableContents){
                table.add(new Label(team.getSpecificStat(str).toString()),column,positionNum);
                ++column;
            }
            ++positionNum;
        }
        tab.setContent(table);
    }
    
}
