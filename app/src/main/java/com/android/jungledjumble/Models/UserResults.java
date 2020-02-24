package com.android.jungledjumble.Models;

public class UserResults {
    private int level;
    private int points;
    private int rewards;

    public UserResults(int level, int points, int rewards){
        this.level = level;
        this.points = points;
        this.rewards = rewards;
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

    @Override
    public String toString() {
        return "User{" +
                "level='" + String.valueOf (level) + '\'' +
                ", points='" + String.valueOf (points) + '\'' +
                ", rewards='" + String.valueOf (rewards) + '\'' +
                '}';
    }
}
