package com.example.moragame.game;

import com.example.moragame.R;

public enum Mora {
    SCISSOR,ROCK,PAPER,NONE;
    public static int getMoreResId(Mora mora){
        int[] resId={R.drawable.scissors,R.drawable.rock,R.drawable.paper};
        return resId[mora.ordinal()];
    }
}
