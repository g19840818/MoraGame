package com.example.moragame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.moragame.game.Com;
import com.example.moragame.game.Player;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton scissorsIbn;
    private ImageButton rockIbn;
    private ImageButton paperIbn;
    private Button startBtn;
    private Button quitBtn;
    private final String TAG = "MainActivity";
    Player player;
    Com com;


    public void findView() {
        scissorsIbn = findViewById(R.id.scissors_ibn);
        rockIbn = findViewById(R.id.rock_ibn);
        paperIbn = findViewById(R.id.paper_ibn);
        startBtn = findViewById(R.id.start_btn);
        quitBtn = findViewById(R.id.quit_btn);

        scissorsIbn.setOnClickListener(this);
        rockIbn.setOnClickListener(this);
        paperIbn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
    }
    public void initGame(){
         player = new Player();
         com=new Com();
         com.AI();
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scissors_ibn:
                Log.d(TAG, getResources().getString(R.string.scissors));
                break;

            case R.id.rock_ibn:
                Log.d(TAG, getResources().getString(R.string.rock));
                break;

            case R.id.paper_ibn:
                Log.d(TAG, getResources().getString(R.string.paper));
                break;
            case R.id.start_btn:
                Log.d(TAG, getResources().getString(R.string.start));
                initGame();
                break;
            case R.id.quit_btn:
                Log.d(TAG, getResources().getString(R.string.quit));
                break;
        }
    }}
