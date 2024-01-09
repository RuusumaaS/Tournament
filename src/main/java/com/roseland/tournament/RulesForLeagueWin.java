/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.roseland.tournament;

import java.util.Comparator;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 *
 * @author asus
 */
public class RulesForLeagueWin extends Application {
    
    private String name;
    private Vector<Comparator<Team>> rules;
    
    
    
    
    @Override
    public void start(Stage primaryStage) {
        
        Label info = new Label("Here you choose rules and additional statistics you "
            + "want to give to your league.");
        
        //Here we make labels and textfield for points user wants to give from wins and
        //draws. Loss during regular time will always give 0 points.
        Label win = new Label("Points from a win:");
        Label draw = new Label("Points from a draw:");
        Label extraTimeWin = new Label("Points from a win during extratime");
        Label extraTimeLoss = new Label("Points from a loss during extratime");
        
        TextField winPoints = new TextField("3");
        TextField drawPoints = new TextField("1");
        TextField extraTimeWinPoints = new TextField("2");
        TextField extraTimeLossPoints = new TextField("1");
        
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Use a regular expression to allow only numeric input
            if (Pattern.matches("\\d*", newText)) {
                return change; // Accept the change
            } else {
                return null; // Reject the change
            }
        };

        // Apply the TextFormatter to the TextField
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        
        winPoints.setTextFormatter(textFormatter);
        drawPoints.setTextFormatter(textFormatter);
        extraTimeWinPoints.setTextFormatter(textFormatter);
        extraTimeLossPoints.setTextFormatter(textFormatter);
        
        
        //Checkbox to make sure if user wants extratimes or not. Textfields for
        //extratimepoints and draw will be disabled depending if user wants extratimes or not.
        CheckBox extraTime = new CheckBox("Mark checked if you wish your"
                + " games to have an extra time");
        extraTime.setSelected(false);
        extraTime.setOnAction(event -> {
            boolean selected = extraTime.isSelected();
            drawPoints.setDisable(selected);
            extraTimeWinPoints.setDisable(!selected);
            extraTimeLossPoints.setDisable(!selected);
        });
        
        drawPoints.setDisable(extraTime.isSelected());
        extraTimeWinPoints.setDisable(!extraTime.isSelected());
        extraTimeLossPoints.setDisable(!extraTime.isSelected());
        
        //Gridpane for pointlabels and textfields.
        GridPane pointsGrid = new GridPane();
        pointsGrid.add(win, 0, 0);
        pointsGrid.add(winPoints,1,0);
        pointsGrid.add(draw,0,1);
        pointsGrid.add(drawPoints,1,1);
        pointsGrid.add(extraTimeWin,2,0);
        pointsGrid.add(extraTimeWinPoints,3,0);
        pointsGrid.add(extraTimeLoss,2,1);
        pointsGrid.add(extraTimeLossPoints,3,1);
        
        Label statisticInfo = new Label("Now choose if you want to add "
                + "other statistics than just points and goals. \nAll statistics include "
                + "total attempts and succesful ones. "
                + "For example: total shots and shots on target.");
        
        CheckBox shots = new CheckBox("Shots");
        CheckBox passes = new CheckBox("Passes");
        CheckBox tackles = new CheckBox("Tackles");
        
        Vector<CheckBox> statBoxes = new Vector<>();
        statBoxes.add(shots);
        statBoxes.add(passes);
        statBoxes.add(tackles);
        
        GridPane statGrid = new GridPane();
        
        int column = 0;
        int row = 0;
        
        //There will be 3 columns in this grid.
        for(CheckBox e : statBoxes){
            statGrid.add(e,column, row);
            ++column;
            int index = statBoxes.indexOf(e);
            if(index % 3 == 0 && index != 0){
                ++row;
                column = 0;
            }
        }
        row = 0;
        column = 0;
        
        RadioButton rule1 = new RadioButton("Points,Wins,Goals,GoalDifference");
        RadioButton rule2 = new RadioButton("Points,Goals,GoalDifference,Wins");
        
        ToggleGroup radios = new ToggleGroup();
        rule1.setToggleGroup(radios);
        rule2.setToggleGroup(radios);
        rule1.setSelected(true);
        
        Button btn = new Button();
        btn.setText("Next");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Map<String,Integer> matchRules = new HashMap<>();
                Map<String,Double> statsToCollect = new HashMap<>();
                Vector<Comparator<Team>> tableRules = new Vector<>();
                if(extraTime.isSelected()){
                    matchRules.put("extraTimeWin",Integer.valueOf(extraTimeWinPoints.getText()));
                    matchRules.put("extraTimeLoss",Integer.valueOf(extraTimeLossPoints.getText()));
                }
                else{
                    matchRules.put("draw",Integer.parseInt(draw.getText()));
                }
                String selectedTableRules = radios.selectedToggleProperty().toString();
                
                
            }
        });
        
        
        
        GridPane root = new GridPane();
        root.setVgap(20);
        root.setHgap(20);
        
        
        root.add(info,column,row,3,1);
        ++row;
        root.add(extraTime,column,row);
        ++row;
        root.add(pointsGrid,column,row,1,2);
        row = row+2;
        
        root.add(statisticInfo,column,row,2,1);
        ++row;
        root.add(statGrid,column,row);
        ++row;
        
        root.add(btn,column,row);
        
        Scene scene = new Scene(root, 600, 500);
        
        primaryStage.setTitle("Rules");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void setLeagueName(String leagueName){
        this.name = leagueName;
    }
     
    public String getLeagueName(){
        return this.name;
     }
    private void openTeamsWindow(String leagueName) {
        TeamsWindow teamsWindow = new TeamsWindow();

        // Pass parameters to the second window (if needed)
        teamsWindow.setLeagueName(leagueName);

        // Show the second window
        teamsWindow.start(new Stage());
    }
    
    public Vector<Comparator<Team>> createTableRules(Vector<String> rules){
        Vector<Comparator<Team>> tableRules = new Vector<>();
         
        return tableRules;
    }
}
