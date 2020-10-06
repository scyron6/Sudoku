package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    private int size;
    private int difficulty;
    private int[][] board;
    private RequestQueue requestQueue;

    List<String> grid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        size = getIntent().getIntExtra("size", 9);
        difficulty = getIntent().getIntExtra("difficulty", 2);

        if (size == 9) {
            setContentView(R.layout.activity_play);
        }
        else {
            setContentView(R.layout.activity_play_four);
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        board = new int[size][size];


        initBoard();

        GridView gridView = findViewById(R.id.gridView);
        SudokuAdapter sudokuAdapter = new SudokuAdapter(grid, this);
        gridView.setAdapter(sudokuAdapter);




    }

    public void load_board() {
        String url = "http://www.cs.utep.edu/cheon/ws/sudoku/new/?size=" + Integer.toString(size) + "&level="
            + Integer.toString(difficulty);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray squares = response.getJSONArray("squares");
                    for (int i = 0; i < squares.length(); i++) {
                        JSONObject value = squares.getJSONObject(i);
                        String x = value.getString("x");
                        String y = value.getString("y");
                        String num = value.getString("value");

                        board[Integer.parseInt(x)][Integer.parseInt(y)] = Integer.parseInt(num);
                    }

                } catch (JSONException e) {
                    Log.e("upload", "JSON error", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("upload", "list error", error);
            }
        });

        requestQueue.add(request);

    }

    public void initBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid.add(Integer.toString(board[i][j]));
            }
        }

    }
}