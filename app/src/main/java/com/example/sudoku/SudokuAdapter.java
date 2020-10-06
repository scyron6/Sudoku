package com.example.sudoku;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

public class SudokuAdapter extends BaseAdapter {
    List<String> grid;
    Context mContext;

    public SudokuAdapter(List<String> grid, Context mContext) {
        this.grid = grid;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return grid.size();
    }

    @Override
    public Object getItem(int position) {
        return grid.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button dummy = new Button(mContext);
        dummy.setText(grid.get(position));
        return dummy;
    }
}
