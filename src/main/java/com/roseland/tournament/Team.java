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
public class Team {
       private String name;
    private Map<String, Double> statistics;
    
    
    public Team(String name, Map<String, Double> stats){
        this.statistics = stats;
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public Map<String,Double> getStatistics(){
        return statistics;
    }
    public Double getPoints(){
        return this.getSpecificStat("points");
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
    
    public void addToSpecificStat(String statName, Double stat){
        if(statistics.containsKey(statName)){
            Double newValue = statistics.get(statName)+ stat;
            statistics.put(statName,newValue);
        }
        else{
            System.out.println("Statistic not tracked.");
        }
    }
    
    public Double calculateGoalDifference(){
        return this.getSpecificStat("scoredgoals")-this.getSpecificStat("concededgoals");
    }
}


