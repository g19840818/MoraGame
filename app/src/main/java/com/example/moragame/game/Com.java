package com.example.moragame.game;

import android.view.View;

import java.util.Random;

public class Com extends Player{
private OnActionListener listener;
    public void AI(){
        try{
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMora(getRandomMora());
        listener.onAction(GameState.PLAY_ROUND);
    }
    public Com(OnActionListener listener){
        this.listener = listener;
    }


    public static Mora getRandomMora(){
        int index=new Random().nextInt(Mora.PAPER.ordinal()+1);
        if (index==0){
            return Mora.SCISSOR;
        }else if(index==1){
            return Mora.ROCK;
        }else {
            return Mora.PAPER;
        }
    }
}
