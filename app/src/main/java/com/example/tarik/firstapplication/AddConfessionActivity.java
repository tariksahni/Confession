package com.example.tarik.firstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AddConfessionActivity extends AppCompatActivity {

    Button confess;
    EditText to_edit;
    EditText from_edit;
    EditText description_edit;

    private String ADD_URL = "https://izhaar.herokuapp.com/message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_confession);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        confess = (Button) findViewById(R.id.confess);
        to_edit = (EditText) findViewById(R.id.add_to);
        from_edit = (EditText) findViewById(R.id.add_from);
        description_edit = (EditText) findViewById(R.id.add_des);


        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        confess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = description_edit.getText().toString();
                final String toString = to_edit.getText().toString();
                final String fromString = from_edit.getText().toString();
                if(description.length()!=0 && toString.length()!=0) {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("toc", toString);
                    params.put("fromc", fromString);
                    params.put("des", description);
                    JSONObject obj = new JSONObject(params);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, ADD_URL, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status = response.getString("status");
                                if(status.equals("SUCCESS")){
                                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else{
                                    Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(req);
                } else {
                    Toast.makeText(getApplicationContext(), "Please add something" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}