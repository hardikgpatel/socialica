package com.test.socketchat.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.socketchat.R;

import java.util.ArrayList;

/**
 * Created by lcom151-one on 2/27/2018.
 */

public class CounteryAdapter extends BaseAdapter {

    private ArrayList<String> arrayCountry;
    private Context context;

    public CounteryAdapter(ArrayList<String> arrayCountry,Context context) {
        this.arrayCountry = arrayCountry;
        this.context=context;
    }

    @Override
    public int getCount() {
        return arrayCountry.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCountry.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.row_country, parent, false);
        TextView tvCountry=row.findViewById(R.id.tv_country);
        tvCountry.setText(arrayCountry.get(position));
        return row;
    }
}
