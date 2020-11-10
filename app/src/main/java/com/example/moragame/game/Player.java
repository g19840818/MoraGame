package com.example.moragame.game;

public class Player {
    private Mora mora;
    private int wincount;
    private int losecount;
    private int life;
    public Player(){
        life=3;
        wincount=0;
        mora=Mora.SCISSOR;
    }
    public String getHart() {
        String hart = "";
        for (int i = 0; i < life; i++) {
            hart += "❤";
        }
        return hart;
    }
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }

    public void setMora(Mora mora) {
        this.mora = mora;
    }

    public Mora getMora() {
        return mora;
    }

    public int getWincount() {
        return wincount;
    }

    public void setWincount(int wincount) {
        this.wincount = wincount;
    }

    public int getLosecount() {
        return losecount;
    }

    public void setLosecount(int losecount) {
        this.losecount = losecount;
    }


}
