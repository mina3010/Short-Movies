package com.example.fish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fish.model.Video;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE=1;
    RecyclerView recyclerView;
    MoviesAdapter mAdapter;
    List<Video>all_videos=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.move_RV);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MoviesAdapter(this,all_videos);
        recyclerView.setAdapter(mAdapter);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        getJsonData();
    }

    private void getJsonData() {
        String URL ="https://raw.githubusercontent.com/bikashthapa01/myvideos-android-app/master/data.json";
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray categories = response.getJSONArray("categories");
                                    JSONObject categoriesData = categories.getJSONObject(0);
                                    JSONArray videos = categoriesData.getJSONArray("videos");

                                    for(int i=0 ; i<videos.length();i++){
                                        JSONObject video =videos.getJSONObject(i);
                                        Video v= new Video();

                                        v.setTitle(video.getString("title"));
                                        v.setAuthor(video.getString("subtitle"));
                                        v.setDescription(video.getString("description"));
                                        v.setImageUrl(video.getString("thumb"));
//                                        String q = URLEncoder.encode("q","utf-8");



                                        v.setVideoUrl(video.getString("sources").trim().replaceAll("\\\\", ""));


//                                       replaceAll("\\\\", "");
                                        Log.d("TAG1","onResponse: "+v.getVideoUrl());
                                        all_videos.add(v);
                                        mAdapter.notifyDataSetChanged();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // handle response
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type","application/json; charset=utf-8");


                        return headers;
                    }
                };


//                try {
//                    JSONArray categories = response.getJSONArray("categories");
//                    JSONObject categoriesData = categories.getJSONObject(0);
//                    JSONArray videos = categoriesData.getJSONArray("videos");
//
//                    for(int i=0 ; i<videos.length();i++){
//                        JSONObject video =videos.getJSONObject(i);
//                        Video v= new Video();
//
//                        v.setTitle(video.getString("title"));
//                        v.setAuthor(video.getString("subtitle"));
//                        v.setDescription(video.getString("description"));
//                        v.setImageUrl(video.getString("thumb"));
//                        String q = URLEncoder.encode("q","utf-8");
//
//                        v.setVideoUrl(video.getString("sources").trim().replace("[","").replace("]",""));
//
//                        Log.d("TAG","onResponse: "+v.getVideoUrl());
//                        all_videos.add(v);
//                        mAdapter.notifyDataSetChanged();
//
//                    }
//                } catch (JSONException | UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }



        requestQueue.add(objectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CODE && resultCode == RESULT_OK) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new MoviesAdapter(this, all_videos);
            recyclerView.setAdapter(mAdapter);
        }

    }
}