package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GameGrid gg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gg = new GameGrid(this);

        gg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gg.onClick(motionEvent.getX(), motionEvent.getY());
                return false;
            }
        });

        setContentView(gg);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev){
//        Toast.makeText(this, "Test", Toast.LENGTH_LONG).show();
//        return false;
//    }
//
//
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev){
//        Toast.makeText(this, "Test", Toast.LENGTH_LONG).show();
//        gg.update();
//        return false;
//    }
}