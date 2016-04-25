package com.example.tarik.firstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Date;


public class ShowConfessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_confession);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent in = getIntent();
        Confession bean = (Confession) in.getSerializableExtra("confess");


        TextView id = (TextView) findViewById(R.id.show_id);
        TextView to = (TextView) findViewById(R.id.show_to);
        TextView from = (TextView) findViewById(R.id.show_from);
        TextView timestamp = (TextView) findViewById(R.id.show_timestamp);
        TextView des = (TextView) findViewById(R.id.show_des);

        to.setText("To: " + bean.getTo());
        from.setText("From: " + bean.getFrom());
        des.setText(bean.getDes());
        id.setText("#" + String.valueOf(bean.getId()));

        Timestamp tt = bean.getTimestamp();
        Date date = new Date(tt.getTime());

        String str_date = DateFormat.format("hh:mm dd-MM", date).toString();

        timestamp.setText(str_date);


    }
}