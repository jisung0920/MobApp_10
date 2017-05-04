package com.example.jisung.mobapp_10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jisung on 2017-05-04.
 */

public class urlAdapter extends BaseAdapter {
    ArrayList<urlData> data = new ArrayList<urlData>();
    Context context;

    public urlAdapter( Context context,ArrayList<urlData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.url_item, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView url = (TextView) convertView.findViewById(R.id.url);
        name.setText(data.get(position).name);
        url.setText(data.get(position).url);
        return convertView;
    }
}
