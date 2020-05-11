package com.example.braingame;

public class GameLevel {
    private int start;
    private int end;
    private int level;

    public GameLevel() {

    }

    public GameLevel(int level) {
        setLevel(level);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setLevel(int level){
        if (level==1){
            start = 0;
            end = 9;
        } else if (level==2){
            start = 10;
            end = 99;
        } else if (level==3){
            start = 100;
            end = 999;
        }
        this.level = level;
    }

    public int getLevel(){
        return level;
    }
}
