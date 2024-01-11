/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roseland.tournament;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author asus
 */
public class League {
    private String name;
    private ArrayList<Team> teams; 
    private ArrayList<Match> matches;
    private List<Comparator<Team>> tableRules;
    private Map<String, Integer> matchRules; 
    
    public League(String name, ArrayList<Team> teams, ArrayList<Match> matches,List<Comparator<Team>> tableRules,
            Map<String, Integer> matchRules){
        this.name = name;
        this.teams = teams;
        this.matches = matches;
        this.tableRules = tableRules;
        this.matchRules = matchRules;
    }
    
    public void sortLeagueTable(){
        
        Comparator<Team> comparator = tableRules.stream()
                .reduce(Comparator::thenComparing)
                .orElseThrow();
        Collections.sort(this.teams, comparator);
        
    }
    
    public ArrayList<Team> getTeams(){
        return this.teams;
    }
    
    public ArrayList<Match> getMatches(){
        return this.matches;
    }
}
