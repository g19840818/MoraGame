package com.example.moragame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moragame.game.Com;
import com.example.moragame.game.GameState;
import com.example.moragame.game.Mora;
import com.example.moragame.game.Player;
import com.example.moragame.game.WinState;
import com.example.moragame.game.OnActionListener;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnActionListener, Runnable {

    private ImageButton scissorsIbn;
    private ImageButton rockIbn;
    private ImageButton paperIbn;
    private Button startBtn;
    private Button quitBtn;
    private ImageView comImg;
    private final String TAG = "MainActivity";
    private Player player;
    private Com com;
    private GameState gameState;
    private int gameMillisecond;
    private int targetMillisecond;
    private boolean gameCountDownFinsh;
    private Handler gameTimer;
    private TextView countText;
    boolean gameOver;
    boolean gaming;

    public void findView() {
        scissorsIbn = findViewById(R.id.scissors_ibn);
        rockIbn = findViewById(R.id.rock_ibn);
        paperIbn = findViewById(R.id.paper_ibn);
        startBtn = findViewById(R.id.start_btn);
        quitBtn = findViewById(R.id.quit_btn);
        comImg = findViewById(R.id.computer_img);
        scissorsIbn.setOnClickListener(this);
        rockIbn.setOnClickListener(this);
        paperIbn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
    }

    public void initGame() {
        player = new Player();
        com = new Com(this);
        gameState = GameState.INIT_GAME;
        gameOver=false;
        gaming=false;
    }

    public void startGame() {
        if (gameOver){
            initGame();
        }
        onAction(GameState.COMPUTER_ROUND);
        gameMillisecond = 0;
        targetMillisecond = 1000;
        gameCountDownFinsh = false;
    }

    public void onAction(GameState state) {
        gameState = state;
        switch (state) {
            case START_GAME:
                startGame();
                break;
            case COMPUTER_ROUND:
                com.AI();
                break;
            case PLAY_ROUND:
                startGameCountDown();
                break;
            case CHECK_WIN_STATE:
                WinState winState = WinState.getWinState(
                        player.getMora(), com.getMora()
                );
                if (winState==WinState.COM_WIN){
                    player.setLife(player.getLife()-1);
                    Log.d(TAG,String.valueOf(player.getLife()));
                    if (player.getLife()==0){
                        gameOver=true;
                        return;
                    }
                }
                Log.d(TAG, winState.toString());
                onAction(GameState.START_GAME);
                break;
        }
    }

    private void startGameCountDown() {
        comImg.setImageResource(Mora.getMoraResId(com.getMora()));
        gameTimer = new Handler(Looper.myLooper());
        gameTimer.post(this);
    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initGame();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scissors_ibn:
                player.setMora(Mora.SCISSOR);
                onAction(GameState.CHECK_WIN_STATE);
                break;

            case R.id.rock_ibn:
                player.setMora(Mora.ROCK);
                onAction(GameState.CHECK_WIN_STATE);
                break;

            case R.id.paper_ibn:
                player.setMora(Mora.PAPER);
                onAction(GameState.CHECK_WIN_STATE);
                break;
            case R.id.start_btn:
                onAction(GameState.START_GAME);
                Log.d(TAG, getResources().getString(R.string.start));
                break;
            case R.id.quit_btn:
                Log.d(TAG, getResources().getString(R.string.quit));
                break;
        }
    }

    @Override
    public void run() {
        if (gameCountDownFinsh) {
            return;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameMillisecond+=10;
        if(gameMillisecond>targetMillisecond){
            gameMillisecond=targetMillisecond;
            gameCountDownFinsh=true;
            player.setMora(Mora.NONE);
            onAction(GameState.CHECK_WIN_STATE);
        }
        int sec=(targetMillisecond-gameMillisecond)/1000;
        int ms=(targetMillisecond-gameMillisecond)%1000;
        String time=String.format("%d:%03d",sec,ms);
        countText.setText(time);
        gameTimer.postDelayed(this,0);
    }

}
