package com.example.sudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private Button play;
    private int size = 9;
    private int difficulty = 2;
    private MaterialButton nine;
    private MaterialButton four;
    private MaterialButton easy;
    private MaterialButton medium;
    private MaterialButton hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.playButton);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlayActivity.class);
                intent.putExtra("size", size);
                intent.putExtra("difficulty", difficulty);

                v.getContext().startActivity(intent);
            }
        });
    }

    public void optionsMenu(View v) {
        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;

        dialogBuilder = new AlertDialog.Builder(v.getContext());
        View options_menu = LayoutInflater.from(v.getContext()).inflate(R.layout.options_menu, null);

        nine = (MaterialButton) options_menu.findViewById(R.id.nine);
        four = (MaterialButton) options_menu.findViewById(R.id.four);
        easy = (MaterialButton) options_menu.findViewById(R.id.easy);
        medium = (MaterialButton) options_menu.findViewById(R.id.medium);
        hard = (MaterialButton) options_menu.findViewById(R.id.hard);

        if (size == 9) {
            nine.setStrokeWidth(5);
            four.setStrokeWidth(0);
        }
        else {
            nine.setStrokeWidth(0);
            four.setStrokeWidth(5);
        }

        if (difficulty == 1) {
            easy.setStrokeWidth(5);
            medium.setStrokeWidth(0);
            hard.setStrokeWidth(0);
        }
        else if (difficulty == 2) {
            easy.setStrokeWidth(0);
            medium.setStrokeWidth(5);
            hard.setStrokeWidth(0);
        }
        else {
            easy.setStrokeWidth(0);
            medium.setStrokeWidth(0);
            hard.setStrokeWidth(5);
        }

        dialogBuilder.setView(options_menu);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    public void setSize(View v) {
        int pass = Integer.parseInt(v.getTag().toString());
        size = pass;
        if (size == 9) {
            nine.setStrokeWidth(5);
            four.setStrokeWidth(0);
        }
        else {
            nine.setStrokeWidth(0);
            four.setStrokeWidth(5);
        }
    }


    public void setDifficulty(View v) {
        int pass = Integer.parseInt(v.getTag().toString());
        difficulty = pass;

        if (difficulty == 1) {
            easy.setStrokeWidth(5);
            medium.setStrokeWidth(0);
            hard.setStrokeWidth(0);
        }
        else if (difficulty == 2) {
            easy.setStrokeWidth(0);
            medium.setStrokeWidth(5);
            hard.setStrokeWidth(0);
        }
        else {
            easy.setStrokeWidth(0);
            medium.setStrokeWidth(0);
            hard.setStrokeWidth(5);
        }
    }

}