package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;

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
    private int location = -1;
    private int[][] board;
    private RequestQueue requestQueue;
    private TableLayout sudokuBoard;
    private Typeface typeface;

    List<String> grid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        size = getIntent().getIntExtra("size", 9);
        difficulty = getIntent().getIntExtra("difficulty", 2);
        typeface = Typeface.createFromAsset(getAssets(), "AlfaSlabOne-Regular.ttf");

        setContentView(R.layout.activity_play_four);
        sudokuBoard = findViewById(R.id.sudokuGrid);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        board = new int[size][size];


        load_board();




    }

    public void load_board() {
        String url = "http://www.cs.utep.edu/cheon/ws/sudoku/new/?size=" + size + "&level="
            + difficulty;

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

                    initBoard();

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
        // Add values from API call to grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid.add(Integer.toString(board[i][j]));
            }
        }

        //Dynamically create the board
        for (int a = 0; a < size; a++) {
            TableRow newRow = new TableRow(this);
            newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            newRow.setId(-a-3);

            //add buttons to the row
            for (int b = 0; b < size; b++) {
                Button button_00 = new Button(this);

                button_00.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));

                button_00.setId((a*size) + b);
                button_00.setTag((a*size) + b);
                button_00.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (location != -1) {
                            View parent;
                            if (location / size == 0) {
                                parent = findViewById(location / size - 3);
                            }
                            else {
                                parent = findViewById((-location / size) - 3);
                            }
                            Button previous = parent.findViewById(location);
                            String num = previous.getText().toString();
                            if (num.isEmpty()) {
                                previous.setBackgroundColor(Color.parseColor("#e3e2e7"));
                            }
                            else {
                                previous.setBackgroundColor(Color.parseColor("#247ef1"));
                            }
                        }
                        int both = Integer.parseInt(v.getTag().toString());
                        Button current = v.findViewById(both);
                        current.setBackgroundColor(Color.parseColor("#01e8f0"));
                        location = both;
                        Log.d("location", Integer.toString(location));

                    }
                });
                if (grid.get((a*size) + b).equals("0")) {
                    button_00.setTypeface(typeface);
                    button_00.setText("");
                    button_00.setBackgroundColor(Color.parseColor("#e3e2e7"));
                }
                else {
                    button_00.setBackgroundColor(Color.parseColor("#247ef1"));
                    button_00.setTextColor(Color.WHITE);
                    button_00.setTypeface(typeface);
                    button_00.setTextSize(20);
                    button_00.setText(grid.get((a*size) + b));
                }

                newRow.addView(button_00);
            }

            sudokuBoard.addView(newRow);
        }



    }

    public void setSpot(View view) {
        String pass = view.getTag().toString();

        Button button = findViewById(location);
        button.setText(pass);
        button.setTextColor(Color.WHITE);
        if (pass.equals("")) {
            button.setBackgroundColor(Color.parseColor("#e3e2e7"));
        }
        else {
            button.setTextSize(20);
            button.setBackgroundColor(Color.parseColor("#247ef1"));
        }
    }
}