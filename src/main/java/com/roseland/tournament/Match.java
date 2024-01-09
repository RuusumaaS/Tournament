/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roseland.tournament;

import java.util.Map;

/**
 *
 * @author asus
 */
public class Match {
    private Map<String,Double> statistics;
    private int matchId;
    private Team homeTeam;
    private Team awayTeam;
    private Team winner;
    private boolean extraTime;
    
    public Match(int matchId,Team homeTeam, Team awayTeam,Map<String,Double> statistics, boolean extratime){
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.statistics = statistics;
        if(statistics.get("awaygoals") < this.statistics.get("homegoals")){
            winner = homeTeam;
        }
        else if(statistics.get("awaygoals") > this.statistics.get("homegoals")){
            winner = awayTeam;
        }
        else{
            winner = null;
        }
    }
    
    public Map<String,Double> getStatistics(){
        return this.statistics;
    }
   
    public int getMatchId(){
        return this.matchId;
    }
    
    public Double getSpecificStat(String stat){
        if(statistics.containsKey(stat)){
            return statistics.get(stat);
        }
        else{
            //If a specific statistic is not found, we return -1.5 since non of
            //the stats can be be both negative and fraction.
            return -1.5;
        }
    }
    
    public Team getHomeTeam(){
        return this.homeTeam;
    }
    public Team getawayTeam(){
        return this.awayTeam;
    }
    
    public void givePoints(){
        if(winner==null){
            this.homeTeam.addToSpecificStat("points", 1.0);
            this.awayTeam.addToSpecificStat("points", 1.0);
        }
        else if(winner.equals(homeTeam)){
            if(extraTime ==true){
                homeTeam.addToSpecificStat("points", 2.0);
                awayTeam.addToSpecificStat("points", 1.0);
            }
            else{
                homeTeam.addToSpecificStat("points", 3.0);
            }
            
        }
        else if(winner.equals(awayTeam)){
            if(extraTime ==true){
                awayTeam.addToSpecificStat("points", 2.0);
                homeTeam.addToSpecificStat("points", 1.0);
            }
            else{
                awayTeam.addToSpecificStat("points", 3.0);
            }  
        }
    }
}
