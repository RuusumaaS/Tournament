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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author asus
 */
public class LeagueWindow extends Application {
    
    private League league;
    private Tab tableTab;
    private Tab resultsTab;
    private Tab fixturesTab;
    private Tab playGameTab;
    private Tab statisticsTab;
    
    @Override
    public void start(Stage primaryStage) {
        
        TabPane tabPane = new TabPane();

        this.tableTab = new Tab("Leaguetable");
        createTableTab(this.tableTab,this.league);
        
        this.resultsTab = new Tab("Results");
        
        this.fixturesTab = new Tab("Fixtures");
        
        this.playGameTab = new Tab("Next match");
        createPlayTab(this.playGameTab,this.league);
        
        tabPane.getTabs().addAll(this.tableTab,this.playGameTab,this.fixturesTab,this.resultsTab);
        
        if(!this.league.getAdditionalStats().isEmpty()){
            this.statisticsTab = new Tab("Statistics");
            createStatisticsTab(this.statisticsTab,this.league);
            tabPane.getTabs().add(this.statisticsTab);
        }
        
        StackPane root = new StackPane();
        root.getChildren().add(tabPane);
        
        Scene scene = new Scene(root, 700, 550);
        
        primaryStage.setTitle("League");
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
    
    public void setLeague(League league){
        this.league = league;
    }
    
    public void tableTitles(GridPane pane,ArrayList<String> tableContents){
        int column = 0;
        pane.add(new Label("Position"),column,0);
        ++column;
        
        pane.add(new Label("Team"),column,0);
        ++column;
        for(String str : tableContents){
            pane.add(new Label(str),column,0);
            ++column;
        }
        
    }
    
    public void createTableTab(Tab tab, League league){
        
        Label topLabel = new Label("League table:");
        topLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        GridPane table = createBasicGrid();
        BorderPane border = new BorderPane();
        border.setCenter(table);
        
        league.sortLeagueTable();
        if(league.getWinner() == null){
            Label bottomLabel = new Label("Winner: -");
            bottomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        }
        else{
            Label bottomLabel = new Label("Winner: " + this.league.getWinner().getName());
            bottomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        }
        
        
        
        ArrayList<String> tableContents = league.getTableContents();
        tableTitles(table,tableContents);
        
        int positionNum = 1;
        
        for(Team team : league.getTeams()){
            int column = 0;
            table.add(new Label(Integer.toString(positionNum)),column,positionNum);
            ++column;
            table.add(new Label(team.getName()),column,positionNum);
            ++column;
            for(String str : tableContents){
                table.add(new Label(String.valueOf(team.getSpecificStat(str).intValue())),column,positionNum);
                ++column;
            }
            ++positionNum;
        }
        tab.setContent(table);
    }
    
    
    public void createPlayTab(Tab tab, League league){
        
        if(league.getFixtures().isEmpty()){
            Label over = new Label("The league is over and the winner is: "
                    + league.getWinner().getName());
            over.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            StackPane stack = new StackPane();
            stack.getChildren().add(over);
            tab.setContent(stack);
        }
        
        else{
            Match nextMatch = league.getNextMatch();
            ArrayList<TextField> fields = new ArrayList<>();
            Map<Label,TextField> homeFields = new LinkedHashMap<>();
            Map<Label,TextField> awayFields = new LinkedHashMap<>();


            BorderPane border = new BorderPane();

            Label gameInfo = new Label("Game number " + nextMatch.getMatchId() + ": "
                    + nextMatch.getHomeTeam().getName() + " - " + nextMatch.getAwayTeam().getName());
            gameInfo.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            border.setTop(gameInfo);


            UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();

                // Use a regular expression to allow only numeric input
                if (Pattern.matches("\\d*", newText)) {
                    return change; // Accept the change
                } else {
                    return null; // Reject the change
                }
            };

            GridPane center = createBasicGrid();

            // Apply the TextFormatter to the TextField
            TextFormatter<String> formHome = new TextFormatter<>(filter);
            TextFormatter<String> formAway = new TextFormatter<>(filter);

            Label homeLabel = new Label(nextMatch.getHomeTeam().getName()+":");
            Label awayLabel = new Label(nextMatch.getAwayTeam().getName()+":");

            Label goals = new Label("Goals");

            TextField homeGoals = new TextField();
            TextField awayGoals = new TextField();
            homeGoals.setTextFormatter(formHome);
            awayGoals.setTextFormatter(formAway);

            fields.add(homeGoals);
            fields.add(awayGoals);

            center.add(homeLabel,0,0);
            center.add(awayLabel,2,0);
            center.add(goals, 1, 1);
            center.add(homeGoals,0,1);
            center.add(awayGoals,2,1);

            int row = 2;
            for(String str : league.getAdditionalStats()){
                Label stat = new Label(str);
                TextField homeStat = new TextField();
                TextField awayStat = new TextField();

                fields.add(homeStat);
                fields.add(awayStat);
                homeFields.put(stat,homeStat);
                awayFields.put(stat, awayStat);

                center.add(homeStat,0,row);
                center.add(stat,1,row);
                center.add(awayStat,2,row);
                ++row;
            }
            CheckBox et = new CheckBox("Add extratime");
            if(!league.doGamesEndInDraw()){
                et.setVisible(false);
                et.disableProperty();
                nextMatch.setExtraTime(false);
            }
            center.add(et, 5, 1);
            
            Button end = new Button("End the game");

            end.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(fieldsFilled(fields)){
                        if(homeGoals.getText().equals(awayGoals.getText()) && league.doGamesEndInDraw()){
                            
                        }
                        else{
                            
                            nextMatch.setExtraTime(et.isSelected());
                            
                            nextMatch.setResult(Integer.valueOf(homeGoals.getText()),
                                    Integer.valueOf(awayGoals.getText()));
                            league.endMatch(nextMatch);
                            endTheMatch(nextMatch,homeFields,awayFields);
                            if(league.getFixtures().isEmpty()){
                                league.sortLeagueTable();
                                league.setWinner(league.getTeams().get(0));
                            }
                            refreshTabs();
                        }


                    }


                }
            });
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15, 15, 15, 15));
            hbox.setSpacing(10);
            hbox.getChildren().add(end);

            border.setCenter(center);
            border.setBottom(hbox);

         
            tab.setContent(border);
        }
        
    }
    
    public boolean fieldsFilled(ArrayList<TextField> fields){
        for(TextField field : fields){
            if(field.getText().equals("")){
                return false;
            }
        }
        
        return true;
    }
    
    public void createStatisticsTab(Tab tab, League league){
        GridPane grid = createBasicGrid();
        ArrayList<String> order = createStatisticsTitles(grid,league.getTeams().get(0).getStatistics());
        int column = 0;
        int row = 1;
        for(Team team : league.getTeamsAlphabetically()){
            grid.add(new Label(team.getName()), column, row);
            ++column;
            for(String str : order){
                grid.add(new Label(String.valueOf(team.getSpecificStat(str).intValue())), column, row);
                ++column;
            }
            column = 0;
            ++row;
        }
        
        tab.setContent(grid);
        
    }
    
    public ArrayList<String> createStatisticsTitles(GridPane grid,Map<String,Double> stats){
        ArrayList<String> orderOfStats = new ArrayList<>();
        grid.add(new Label("Team:"), 0, 0);
        int column = 1;
        for(Map.Entry<String,Double> entry : stats.entrySet()){
            grid.add(new Label(entry.getKey()), column, 0);
            orderOfStats.add(entry.getKey());
            ++column;
        }
        return orderOfStats;
    }
    
    public void createResultsTab(Tab tab, League league){
        
        if(league.getPlayedFixtures().isEmpty()){
            Label empty = new Label("No games has been played");
            StackPane pane = new StackPane();
            pane.getChildren().add(empty);
            tab.setContent(pane);
        }
        else{
            
            
        }
        
    }
    
    
    public void createFixturesTab(Tab tab,League league){
        if(league.getFixtures().isEmpty()){
            Label empty = new Label("No more games to be played");
            StackPane pane = new StackPane();
            pane.getChildren().add(empty);
            tab.setContent(pane);
        }
        else{
            
        }
    }
    
    public GridPane createBasicGrid(){
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(0, 10, 0, 10));
        return grid;
    }
 
    public void refreshTabs(){
        createTableTab(this.tableTab,this.league);
        createPlayTab(this.playGameTab, this.league);
        createFixturesTab(this.fixturesTab,this.league);
        createResultsTab(this.resultsTab, this.league);
        
        if(this.statisticsTab != null){
           createStatisticsTab(this.statisticsTab, this.league); 
        }
        
    }
 
    public void endTheMatch(Match match,Map<Label,TextField> homeFields, Map<Label,TextField> awayFields){
        
        Map<String,Double> homeStats = createStatMap(homeFields);
        Map<String,Double> awayStats = createStatMap(awayFields);
        match.setStatistics(homeStats, awayStats);
        
    }
    
    public Map<String,Double> createStatMap(Map<Label,TextField> stats){
        Map<String,Double> stat = new LinkedHashMap<>();
        for(Map.Entry<Label,TextField> entry : stats.entrySet()){
            stat.put(entry.getKey().getText(), Double.valueOf(entry.getValue().getText()));
        }
        
        return stat;
    }
}
