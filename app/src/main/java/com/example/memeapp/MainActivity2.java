package com.example.memeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.memeapp.adapter.MemeAdapter;
import com.example.memeapp.adapter.MemeContactModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView rv;
    MemeAdapter adapter;
    ArrayList<MemeContactModel> data = new ArrayList<>();
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rv = (RecyclerView) findViewById(R.id.rv);
        String url = "https://api.imgflip.com/get_memes";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int n = response.getJSONObject("data").getJSONArray("memes").length();
                    for(int i=1;i<n;i++){
                        String id = response.getJSONObject("data").getJSONArray("memes").getJSONObject(i).getString("id");
                        String title = response.getJSONObject("data").getJSONArray("memes").getJSONObject(i).getString("name");
                        String meme_url = response.getJSONObject("data").getJSONArray("memes").getJSONObject(i).getString("url");
                        data.add(new MemeContactModel(id,title,meme_url));
                    }
                    adapter = new MemeAdapter(MainActivity2.this,data);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(MainActivity2.this));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }
}