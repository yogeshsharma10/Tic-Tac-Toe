package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    private GameBoardView gameBoardView;
    @SuppressLint("StaticFieldLeak")
    public static TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        gameBoardView = findViewById(R.id.gameBoardView);
    }

    public void resetGame(View view) {
        gameBoardView.resetGame();
    }

}