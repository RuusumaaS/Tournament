package com.roseland.tournament;

import javafx.application.Application;
import static javafx.application.Application.launch;
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


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        GridPane mainGrid = new GridPane();
        
        GridPane basicInfoGrid = new GridPane();
        
        Label enterLeagueLabel = new Label("League name:");
        TextField leagueNameField = new TextField("League name");
        RadioButton leagueRadioButton = new RadioButton("League");
        RadioButton tournamentRadioButton = new RadioButton("Tournament");
        Button createButton = new Button("Create a new league!");
        
        ToggleGroup radios = new ToggleGroup();
        leagueRadioButton.setToggleGroup(radios);
        tournamentRadioButton.setToggleGroup(radios);
        leagueRadioButton.setSelected(true);
        
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                if(!"".equals(leagueNameField.getText())){
                    String leagueName = leagueNameField.getText();
                    openRulesWindow(leagueName);
                    primaryStage.close();
                }
                }
            });
        
        mainGrid.add(enterLeagueLabel, 0, 0);
        mainGrid.add(leagueNameField,0,1,1,2);
        mainGrid.add(leagueRadioButton,1,0);
        mainGrid.add(tournamentRadioButton,2,0);
        mainGrid.add(createButton,3,1);
        
        Scene scene = new Scene(mainGrid, 500, 500);
        
        primaryStage.setTitle("Create a League or Tournament!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private void openRulesWindow(String leagueName) {
        RulesForLeagueWin rulesForLeagueWin = new RulesForLeagueWin();

        // Pass parameters to the second window (if needed)
        rulesForLeagueWin.setLeagueName(leagueName);

        // Show the second window
        rulesForLeagueWin.start(new Stage());
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}