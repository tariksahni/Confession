package com.example.tarik.firstapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ListView list;
    FloatingActionButton fab;
    MyAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private final String GET_URL = "https://izhaar.herokuapp.com/messages";

    ArrayList<Confession> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        adapter = new MyAdapter(getApplicationContext(), items);
        list.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addConfession = new Intent(getApplicationContext(), AddConfessionActivity.class);
                startActivity(addConfession);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);

        fetchData();


    }

    private void refresh() {
        fetchData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    protected void fetchData() {

        items.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");
                    for (int i = array.length() - 1; i >= 0; i--) {
                        Confession bean = new Confession();
                        JSONObject ob = array.getJSONObject(i);

                        bean.setId((Integer) ob.get("id"));
                        bean.setTo((String) ob.get("toc"));
                        bean.setFrom((String) ob.get("fromc"));
                        bean.setDes((String) ob.get("des"));
                        String time = (String) ob.get("timestamp");


                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSS");
                        Date parsedDate = (Date) dateFormat.parse(time);
                        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());


                        bean.setTimestamp(timestamp);

                        items.add(bean);
                        adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
//        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);


    }

}