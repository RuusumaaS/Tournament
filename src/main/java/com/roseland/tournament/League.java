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
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 *
 * @author asus
 */
public class League {
    
    public static ArrayList<String> tableContents = new ArrayList<>();
    
    static{
        tableContents.addAll(
            Arrays.asList("Games","Wins","ETWins","ETLosses","Draws",
                    "Losses","Points","Goals Scored","Goals Conceded","Goal Difference"));
    }
    
    private final String name;
    private ArrayList<Team> teams; 
    private ArrayList<Match> fixtures;
    private ArrayList<Match> playedFixtures;
    private List<Comparator<Team>> tableRules;
    private Map<String, Integer> matchPoints; 
    private final boolean hasEt;
    private ArrayList<String> additionalStats;
    private final int mutualMatches;
    private Team winner;
    
    public League(String name,List<Comparator<Team>> tableRules,
            Map<String, Integer> matchPoints, ArrayList<String> additionalStats, int mutualMatches,
            boolean hasET){
        
        this.name = name;
        this.tableRules = tableRules;
        this.matchPoints = matchPoints;
        this.hasEt = hasET;
        this.additionalStats = additionalStats;
        this.mutualMatches = mutualMatches;
        this.playedFixtures = new ArrayList();
        this.winner = null;
        
        if(hasET){
            tableContents.remove("Draws");
        }
        else{
            tableContents.remove("ETWins");
            tableContents.remove("ETLosses");
            
        }
        
        
    }
    
    public void sortLeagueTable(){
        
        Comparator<Team> comparator = getTableRules().stream()
                .reduce(Comparator::thenComparing)
                .orElseThrow();
        Collections.sort(this.teams, comparator);
        
    }
    
    public boolean doGamesEndInDraw(){
        return this.hasEt;
    }
    
    public ArrayList<String> getTableContents(){
        return tableContents;
    }
    
    public void createAllMatches(){
        ArrayList<Team> teams = getTeams();
        int numOfTeams = getTeams().size();
        
        int numOfTotalMatches = getMutualMatches()*calculateNumberOfMatches(numOfTeams);
        //Map<String,Double> stats = this.fillMatchStatsMap();
        
        ArrayList<Match> allMatches = new ArrayList<>();
        
        int countOfRound = 1;
        int everyOther = 0;
        for(int i = 0;i < getMutualMatches()*teams.size();++i){
            if(i/countOfRound == teams.size()){
                ++countOfRound;
                everyOther = 0;
            }
            
            for(int j = i-numOfTeams*(countOfRound-1)+1;j < numOfTeams; ++j){
                
                if(countOfRound % 2 ==0){
                    if(everyOther % 2 == 0){
                        Match newMatch = new Match(teams.get(i-numOfTeams*(countOfRound-1))
                                ,teams.get(j),this.doGamesEndInDraw(),this.matchPoints);
                        allMatches.add(newMatch);
                    }
                    else{
                        Match newMatch = new Match(teams.get(j)
                                ,teams.get(i-numOfTeams*(countOfRound-1)),this.doGamesEndInDraw(),this.matchPoints);
                        allMatches.add(newMatch);
                    }
                }
                else{
                    if(everyOther % 2 == 0){
                        Match newMatch = new Match(teams.get(j)
                                ,teams.get(i-numOfTeams*(countOfRound-1)),this.doGamesEndInDraw(),this.matchPoints);
                        allMatches.add(newMatch);
                    }
                    else{
                        Match newMatch = new Match(teams.get(i-numOfTeams*(countOfRound-1))
                                ,teams.get(j),this.doGamesEndInDraw(),this.matchPoints);
                        allMatches.add(newMatch);
                    }
                }
                
                ++everyOther;
            }
            
        }
        //Not a proper shuffling for a league.
        Collections.shuffle(allMatches);
        for(int i = 0; i < allMatches.size();++i){
            allMatches.get(i).setMatchId(i+1);
        }
        System.out.println("match number: "+ allMatches.size());
        this.fixtures = allMatches;
    }
    
    /*public Map<String,Double> fillMatchStatsMap(){
        Map<String,Double> collectStats = new LinkedHashMap<>();
        for(String str : this.getAdditionalStats()){
            collectStats.put(str,0.0);
        }
        return collectStats;
    }*/
    
    public void createTeams(ArrayList<String> teamNames){
        ArrayList<Team> teams = new ArrayList<>();
        Map<String,Double> stats = new LinkedHashMap<>();
        
        for(String str : tableContents){
            stats.put(str,0.0);
        }
        if(!this.getAdditionalStats().isEmpty()){
            for(String str : this.getAdditionalStats()){
                stats.put(str,0.0);
            }
        }
        for(String str : teamNames){
            Team newTeam = new Team(str,new LinkedHashMap<>(stats));
            teams.add(newTeam);
        }
        
        this.teams = teams;
        
        createAllMatches();
    }
    
    public int calculateNumberOfMatches(int numOfTeams){
        
        if(numOfTeams == 2){
            return 1;
        }
        return numOfTeams-1 + calculateNumberOfMatches(numOfTeams-1);
    }
    
    public void shuffleMatches(){
        
    }
    
    public ArrayList<Team> getTeams(){
        return this.teams;
    }
    
    public ArrayList<Match> getFixtures(){
        return this.fixtures;
    }
    
    public ArrayList<Match> getPlayedFixtures(){
        return this.playedFixtures;
    }
    
    public Match getNextMatch(){
        if(this.getFixtures().isEmpty()){
            return null;
        }
        return this.getFixtures().get(0);
    }
    
    public int getMutualMatches(){
        return this.mutualMatches;
    }
    
    public List<Comparator<Team>> getTableRules(){
        return this.tableRules;
    }
    
    public ArrayList<String> getAdditionalStats(){
        return this.additionalStats;
    }
    
    public ArrayList<Team> getTeamsAlphabetically(){
        Collections.sort(this.teams,Comparator.comparing(Team::getName));
        return this.teams;
    }
    
    public void setWinner(Team team){
        this.winner = team;
    }
    
    public Team getWinner(){
        return this.winner;
    }
    
    public void endMatch(Match match){
        match.endTheGame();
        this.playedFixtures.add(match);
        int index = this.fixtures.indexOf(match);
        this.fixtures.remove(index);
    }
    
}
