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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;


/**
 *
 * @author asus
 */
public class TeamsWindow extends Application {
    
    private static final int upperLimit = 20; 
    private static final int lowerLimit = 3;
    
    private String name;
    private Map<String,Integer> matchPoints;
    private List<Comparator<Team>> tableRules;
    private ArrayList<String> statsToCollect;
    private int mutualMatches;
    
    
    @Override
    public void start(Stage primaryStage) {
        
        Label addTeamsLabel = new Label("Write team name and then press the button");
        TextField teamName = new TextField();
        ArrayList<String> teamNames = new ArrayList();
        Label teamsListed = new Label();
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(teamsListed);
        scroll.setPrefSize(300, 200);
        
        Button add = new Button("Add a team");
        add.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event){
                if(!teamName.getText().equals("") && !teamNames.contains(teamName.getText())&&
                        teamNames.size() < upperLimit){
                    teamNames.add(teamName.getText());
                    teamsListed.setText(teamsListed.getText()+teamName.getText()+"\n");
                    teamName.setText("");
                }
            }
        });
        
        Button btn = new Button("Create the league!'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if(teamNames.size() >= lowerLimit){
                    boolean hasET;
                    if(matchPoints.containsKey("Draw")){
                        hasET = false;
                    }
                    else{
                        hasET = true;
                    }
                    
                    League league = new League(getLeagueName(),teamNames,tableRules,
                            matchPoints,statsToCollect,mutualMatches,hasET);
                    openLeagueWindow(league);
                    primaryStage.close();
                }
                
            }
        });
        
        GridPane root = new GridPane();
        root.setHgap(20);
        root.setVgap(20);
        root.add(addTeamsLabel,0,0,2,1);
        root.add(teamName,2,0,1,1);
        root.add(add,3,0);
        root.add(scroll,1,1,2,2);
        root.add(btn,3,1);
        
        Scene scene = new Scene(root, 600, 500);
        
        primaryStage.setTitle("Add the teams");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public void setArguments(String leagueName,Map<String,Integer> matchRules
            ,List<Comparator<Team>> tableRules, ArrayList<String> statsToCollect, int matches){
        this.name = leagueName;
        this.matchPoints = matchRules;
        this.tableRules = tableRules;
        this.statsToCollect = statsToCollect;
        this.mutualMatches = matches;
    }
     
    public String getLeagueName(){
        return this.name;
    }
    
    public void openLeagueWindow(League league){
        LeagueWindow leagueWindow = new LeagueWindow();
        leagueWindow.setLeague(league);
        leagueWindow.start(new Stage());
    }
}
