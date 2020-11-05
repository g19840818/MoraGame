package com.example.moragame.game;

public enum WinState {
    PLAY_WIN,
    COM_WIN,
    EVEN,
    IDLE;
    public static WinState getWinState(Mora player,Mora com){
        if(player==com){
            return WinState.EVEN;
        }
        if ((player==Mora.SCISSOR && com==Mora.PAPER)||com==Mora.NONE){
            return WinState.PLAY_WIN;
        }
        if ((player==Mora.PAPER && com==Mora.SCISSOR)||player==Mora.NONE){
            return WinState.COM_WIN;
        }
        if (player.ordinal()>com.ordinal()){
            return WinState.PLAY_WIN;
        }
        return WinState.COM_WIN;
    }
}
