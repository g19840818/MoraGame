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
    private int round;
    private int combo;
    private int hitCombo;
    private boolean gameCountDownFinsh;
    private Handler gameTimer;
    private TextView countText;
    private TextView hartText;
    private TextView winCountText;
    private TextView hitComboText;
    private TextView hitCount;
    private TextView bigCountText;
    private TextView roundText;
    private int countSecond;

    boolean gameOver;
    boolean gaming;

    public void findView() {
        scissorsIbn = findViewById(R.id.scissors_ibn);
        rockIbn = findViewById(R.id.rock_ibn);
        paperIbn = findViewById(R.id.paper_ibn);
        startBtn = findViewById(R.id.start_btn);
        quitBtn = findViewById(R.id.quit_btn);
        comImg = findViewById(R.id.computer_img);
        hartText = findViewById(R.id.hart_text);
        countText = findViewById(R.id.count_text);
        winCountText = findViewById(R.id.win_count_text);
        bigCountText = findViewById(R.id.big_counter_text);
        hitComboText = findViewById(R.id.hit_combo_text);
        bigCountText = findViewById(R.id.big_counter_text);
        hitCount = findViewById(R.id.hit_count_text);
        roundText = findViewById(R.id.round_text);
        scissorsIbn.setOnClickListener(this);
        rockIbn.setOnClickListener(this);
        paperIbn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        bigCountText.setVisibility(View.INVISIBLE);
//        hitCount.setVisibility(View.INVISIBLE);
    }

    public void initGame() {
        player = new Player();
        com = new Com(this);
        gameState = GameState.INIT_GAME;
        gameOver = false;
        gaming = false;
        round = 0;
        hartText.setText(player.getHart());
        winCountText.setText(String.valueOf(player.getWincount()));
    }

    public void startGame() {
        if (gameOver) {
            initGame();
        }
        roundText.setText("ROUND" + ++round);
        onAction(GameState.COMPUTER_ROUND);
        gameMillisecond = 0;
        targetMillisecond = 1000;
        gameCountDownFinsh = false;
    }


    public void onAction(GameState state) {
        gameState = state;
        switch (state) {
            case INIT_GAME:
                initGame();
                bigCountText.setText(String.valueOf(countSecond));
                findViewById(R.id.grid_layout).setVisibility(View.INVISIBLE);
                bigCountText.setVisibility(View.VISIBLE);
                gameTimer = new Handler(Looper.myLooper());
                gameTimer.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        countSecond--;
                        if (countSecond == 0) {
                            gameTimer.removeCallbacks(this);
                            bigCountText.setVisibility(View.INVISIBLE);
                            findViewById(R.id.grid_layout).setVisibility(View.VISIBLE);
                            onAction(GameState.START_GAME);
                            return;
                        }
                        bigCountText.setText(String.valueOf(countSecond));
                        gameTimer.postDelayed(this, 1000);
                    }
                });
                break;
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
                if (winState == WinState.COM_WIN) {
                    combo = 0;
                    player.setLife(player.getLife() - 1);
                    hartText.setText(player.getHart());
                } else if (winState == WinState.PLAY_WIN) {
                    player.setWincount(player.getWincount() + 1);
                    winCountText.setText(String.valueOf(player.getWincount()));
                    combo++;
                    hitCount.setVisibility(View.VISIBLE);
                    hitCount.setText(combo + getResources().getString(R.string.hit));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hitCount.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }).start();
                    if (combo > hitCombo) {
                        hitCombo = combo;
                        hitComboText.setText(getResources().getString(R.string.hit_combo) + "\n" + hitCombo);
                    }
                } else if (winState == winState.EVEN) {
                    combo = 0;
                }
                hitCount.setText(combo + getResources().getString(R.string.hit));
                Log.d(TAG, String.valueOf(player.getLife()));
                if (player.getLife() == 0) {
                    gameOver = true;
                    gaming = false;
                    return;
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
        gameMillisecond += 10;
        if (gameMillisecond > targetMillisecond) {
            gameMillisecond = targetMillisecond;
            gameCountDownFinsh = true;
            player.setMora(Mora.NONE);
            onAction(GameState.CHECK_WIN_STATE);
        }
        int sec = (targetMillisecond - gameMillisecond) / 1000;
        int ms = (targetMillisecond - gameMillisecond) % 1000;
        String time = String.format("%d:%03d", sec, ms);
        countText.setText(time);
        gameTimer.postDelayed(this, 0);
    }

}
