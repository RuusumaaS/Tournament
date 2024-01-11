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
import java.util.HashMap;


/**
 *
 * @author asus
 */
public class TeamsWindow extends Application {
    
    private String name;
    private Map<String,Integer> matchPoints;
    private List<Comparator<Team>> tableRules;
    private Map<String,Double> statsToCollect;
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
                if(!teamName.getText().equals("") && !teamNames.contains(teamName.getText())){
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
                if(teamNames.size() > 2){
                    boolean hasET;
                    if(matchPoints.containsKey("Draw")){
                        hasET = false;
                    }
                    else{
                        hasET = true;
                    }
                    ArrayList<Team> teams = createTeams(teamNames,statsToCollect,hasET);
                    ArrayList<Match> matches = createAllMatches(teams,mutualMatches,statsToCollect,hasET);
                    League league = new League(getLeagueName(),teams,matches,tableRules,matchPoints);
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
            ,List<Comparator<Team>> tableRules, Map<String,Double> statsToCollect, int matches){
        this.name = leagueName;
        this.matchPoints = matchRules;
        this.tableRules = tableRules;
        this.statsToCollect = statsToCollect;
        this.mutualMatches = matches;
    }
     
    public String getLeagueName(){
        return this.name;
    }
    
    public ArrayList<Team> createTeams(ArrayList<String> teamNames,Map<String,Double> stats
        ,boolean hasET){
        ArrayList<Team> teams = new ArrayList<>();
        stats.put("Scored",0.0);
        stats.put("Conceded",0.0);
        stats.put("GamesPlayed",0.0);
        stats.put("Wins",0.0);
        stats.put("Losses",0.0);
        if(hasET){
            stats.put("ETWins", 0.0);
            stats.put("ETLosses",0.0);
        }
        else{
            stats.put("Draws",0.0);
        }
        for(String teamName : teamNames){
            Team newTeam = new Team(teamName,stats);
            teams.add(newTeam);
        }
        return teams;
    }
    
    public int calculateNumberOfMatches(int numOfTeams){
        
        if(numOfTeams == 2){
            return 1;
        }
        return numOfTeams-1 + calculateNumberOfMatches(numOfTeams-1);
    }
    
    public int getMatchesAgainstSameTeam(){
        return this.mutualMatches;
    }
    
    public Map<String,Double> fillMatchStatsMap(Map<String,Double> stats){
        Map<String,Double> collectStats = new HashMap<>();
        collectStats.put("homeGoals",0.0);
        collectStats.put("awayGoals",0.0);
        for(Map.Entry<String,Double> entry : stats.entrySet()){
            collectStats.put("home"+entry.getKey(), 0.0);
            collectStats.put("away"+entry.getKey(), 0.0);
        }
        return collectStats;
    }
    
    public ArrayList<Match> createAllMatches(ArrayList<Team> teams,int matchesAgainstEachOther
            ,Map<String,Double> stats, boolean hasET){
        int numOfTeams = teams.size();
        int numOfTotalMatches = matchesAgainstEachOther*calculateNumberOfMatches(numOfTeams);
        
        ArrayList<Match> allMatches = new ArrayList<>();
        
        int countOfRound = 1;
        int everyOther = 0;
        for(int i = 0;i < numOfTotalMatches;++i){
            if(i/countOfRound == teams.size()){
                ++countOfRound;
                everyOther = 0;
            }
            
            for(int j = i-numOfTeams*(countOfRound-1)+1;j < numOfTeams; ++j){
                
                if(countOfRound % 2 ==0){
                    if(everyOther % 2 == 0){
                        Match newMatch = new Match(i,teams.get(i-numOfTeams*(countOfRound-1))
                                ,teams.get(j),stats,hasET);
                    }
                    else{
                        Match newMatch = new Match(i,teams.get(j)
                                ,teams.get(i-numOfTeams*(countOfRound-1)),stats,hasET);
                    }
                }
                else{
                    if(everyOther % 2 == 0){
                        Match newMatch = new Match(i-numOfTeams*(countOfRound-1),teams.get(j)
                                ,teams.get(i),stats,hasET);
                    }
                    else{
                        Match newMatch = new Match(i,teams.get(i-numOfTeams*(countOfRound-1))
                                ,teams.get(j),stats,hasET);
                    }
                }
                ++everyOther;
            }
            
        }
        
        return allMatches;
    }
    
    public void openLeagueWindow(League league){
        LeagueWindow leagueWindow = new LeagueWindow();
        leagueWindow.setLeague(league);
        leagueWindow.start(new Stage());
    }
}
