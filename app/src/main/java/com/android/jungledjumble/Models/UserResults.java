package com.android.jungledjumble.Models;

public class UserResults {
    private int level;
    private int points;
    private int rewards;
    private String choices;
    private String correct_choices;
    private String time;
    private String concentration;

    public UserResults(int level, int points, int rewards, String choices,String correct_choices, String time, String concentration){
        this.level = level;
        this.points = points;
        this.rewards = rewards;
        this.choices = choices;
        this.correct_choices = correct_choices;
        this.time = time;
        this.concentration = concentration;
    }

    public UserResults(){}

    public int getLevel() { return level; }
    public void setLevel(int level) {
        this.level = level;
    }
    public void updateLevel() {
        this.level += 1;
    }

    public int getPoints() { return points; }
    public void setPoints(int points) {
        this.points = points;
    }
    public int updatePoints() {
        this.points += 1;
        return this.points;
    }

    public int getRewards() { return rewards; }
    public void setRewards(int rewards) {
        this.rewards = rewards;
    }
    public int updateRewards(int rewards) {
        this.rewards += rewards;
        return this.rewards;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public void updateChoices(String choice){this.choices += choice;}

    public String getCorrect_choices() {
        return correct_choices;
    }

    public void setCorrect_choices(String correct_choices) { this.correct_choices = correct_choices; }

    public void updateCorrect_choices(String correct_choice){this.correct_choices += correct_choice;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    @Override
    public String toString() {
        return "User{" +
                "level='" + String.valueOf (level) + '\'' +
                ", points='" + String.valueOf (points) + '\'' +
                ", rewards='" + String.valueOf (rewards) + '\'' +
                ", choices='" +choices + '\'' +
                ", correct choices='" +correct_choices + '\'' +
                '}';
    }



}
