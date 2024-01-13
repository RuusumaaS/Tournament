/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roseland.tournament;

import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author asus
 */
public class Match {
    
    private int matchId;
    private Team homeTeam;
    private Team awayTeam;
    private Team winner;
    private boolean extraTime;
    private int awayGoals;
    private int homeGoals;
    private boolean gameOver;
    
    public Match(Team homeTeam, Team awayTeam,Map<String,Double> statistics, boolean extratime){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.winner = null;
       this.gameOver = false;
    }
    
   
    public int getMatchId(){
        return this.matchId;
    }
    
    public Team getHomeTeam(){
        return this.homeTeam;
    }
    public Team getAwayTeam(){
        return this.awayTeam;
    }
    
    public void givePoints(){
        if(winner==null){
            this.homeTeam.addToSpecificStat("Points", 1.0);
            this.awayTeam.addToSpecificStat("Points", 1.0);
        }
        else if(winner.equals(homeTeam)){
            if(extraTime ==true){
                homeTeam.addToSpecificStat("Points", 2.0);
                awayTeam.addToSpecificStat("Points", 1.0);
            }
            else{
                homeTeam.addToSpecificStat("Points", 3.0);
            }
            
        }
        else if(winner.equals(awayTeam)){
            if(extraTime ==true){
                awayTeam.addToSpecificStat("Points", 2.0);
                homeTeam.addToSpecificStat("Points", 1.0);
            }
            else{
                awayTeam.addToSpecificStat("Points", 3.0);
            }  
        }
    }
    
    public void defineExtraTime(boolean wentToET){
        this.extraTime = wentToET;
    }
    
    public void setMatchId(int id){
        this.matchId = id;
    }
    
    public int getAwayGoals(){
        return this.awayGoals;
    }
    
    public void setAwayGoals(int goals){
        this.awayGoals = goals;
        this.awayTeam.addToSpecificStat("Goals Scored", Double.valueOf(goals));
    }
    
    public int getHomeGoals(){
        return this.homeGoals;
    }
    
    public void setHomeGoals(int goals){
        this.homeGoals = goals;
        this.homeTeam.addToSpecificStat("Goals Scored", Double.valueOf(goals));
    }
    
    public void endTheGame(){
        this.gameOver = true;
       
    }
    public void decideWinner(){
        if(this.awayGoals > this.homeGoals){
            this.winner = this.awayTeam;
        }
        else if(this.awayGoals < this.homeGoals){
            this.winner = this.homeTeam;
        }
    }
    
    public void setStatistics(Map<String,Double> homeStats, Map<String,Double> awayStats){
        if(this.winner == null){
            this.awayTeam.addToSpecificStat("Draws",1.0);
            this.homeTeam.addToSpecificStat("Draws",1.0);
        }
        else if(this.winner == this.homeTeam){
            this.awayTeam.addToSpecificStat("Losses", 1.0);
            this.homeTeam.addToSpecificStat("Wins", 1.0);
        }
        else{
            this.awayTeam.addToSpecificStat("Losses", 1.0);
            this.homeTeam.addToSpecificStat("Wins", 1.0);
        }
        this.awayTeam.addToSpecificStat("Games", 1.0);
        this.homeTeam.addToSpecificStat("Games", 1.0);
        for(Map.Entry<String,Double> entry : homeStats.entrySet()){
            this.getHomeTeam().addToSpecificStat(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String,Double> entry : awayStats.entrySet()){
            this.getAwayTeam().addToSpecificStat(entry.getKey(), entry.getValue());
        }
        
    }
    
    @Override
    public String toString(){
        if(this.gameOver == true){
            return this.homeTeam.getName() + " - " + this.awayTeam.getName() + ": " 
                    + this.homeGoals + " - " + this.awayGoals;
        }
        else{
            return this.homeTeam.getName() + " - " + this.awayTeam.getName();
        }
    }
    
    public void setResult(int homeGoals, int awayGoals){
        if(!this.gameOver){
            this.setAwayGoals(awayGoals);
            this.setHomeGoals(homeGoals);
            this.setConcededGoals();
            this.decideWinner();
            this.givePoints();
            this.endTheGame();
        
        }
        
    }
    
    public void setConcededGoals(){
        this.awayTeam.addToSpecificStat("Goals Conceded", Double.valueOf(this.homeGoals));
        this.homeTeam.addToSpecificStat("Goals Conceded", Double.valueOf(this.awayGoals));
        
        this.awayTeam.updateGoalDifference();
        this.homeTeam.updateGoalDifference();
    }
    
}
