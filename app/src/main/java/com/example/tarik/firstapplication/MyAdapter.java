package com.example.tarik.firstapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public class MyAdapter extends BaseAdapter {
    Context c;
    ArrayList<Confession> items = new ArrayList<>();

    public MyAdapter(Context c, ArrayList<Confession> items) {
        this.c = c;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        } else{
            row = convertView;
        }

        TextView to = (TextView) row.findViewById(R.id.item_to);
        TextView des = (TextView) row.findViewById(R.id.item_des);
        TextView time = (TextView) row.findViewById(R.id.item_time);
        TextView id = (TextView) row.findViewById(R.id.item_id);

        id.setText("" + items.get(position).getId());

        to.setText("TO: " + items.get(position).getTo());
        des.setText(items.get(position).getDes());

        Timestamp timestamp = items.get(position).getTimestamp();
        Date date = new Date(timestamp.getTime());

        String str_date = DateFormat.format("hh:mm dd-MM", date).toString();


        CardView card = (CardView) row.findViewById(R.id.list_card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confession bean = items.get(position);
                Intent in = new Intent(c, ShowConfessionActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("confess", bean);
                c.startActivity(in);
            }
        });



        time.setText(str_date);

        return row;
    }
}
