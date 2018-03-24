package com.example.jiangmingyu.movies;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultSet extends AppCompatActivity {

    private final String url_base = "http://ec2-13-59-67-98.us-east-2.compute.amazonaws.com:8080/fabflix/servlet/Search";
    String keywords;
    int offset = 0;
    int nextOffset = 0;
    int prevOffset = 0;
    private List<Map<String, ArrayList<String>>> list = new ArrayList<>();
    @BindView(R.id.result_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.next_page) FloatingActionButton next;
    @BindView(R.id.pre_page) FloatingActionButton prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        offset = 0;
        Bundle bundle = getIntent().getExtras();
        keywords = bundle.getString(MainActivity.REQ_CODE);
        getMovieList();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() < 10) {
                    Toast.makeText(ResultSet.this, "You have reached the end", Toast.LENGTH_SHORT).show();
                    return;
                }
                offset = offset + 10;
                getMovieList();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset = offset - 10;
                if (offset < 0) {
                    Toast.makeText(ResultSet.this, "You have reached the start", Toast.LENGTH_SHORT).show();
                    offset = 0;
                }
                getMovieList();
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && next.getVisibility() == View.VISIBLE) {
                    next.hide();
                    prev.hide();
                } else if (dy < 0 && next.getVisibility() != View.VISIBLE) {
                    next.show();
                    prev.show();
                }
            }
        });

    }

    private void getMovieList() {
        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);
        final Context context = this;


        String url = url_base + "?title=" + keywords + "&offset=" + offset;
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ResultSet.this, "get Successful", Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                        list = gson.fromJson(response, new TypeToken<List<Map<String, ArrayList<String>>>>(){}.getType());
                        if (list == null || list.size() == 0) {
                            Toast.makeText(ResultSet.this, "No such movie! Please search again!", Toast.LENGTH_LONG).show();
                        }
                        displayData(list);
                        Log.d("response", list.toString());
                        //callback.onSuccess(response);


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试", "headers----------------" + headers);
                    return headers;
                }else {
                    return super.getHeaders();
                }
            }

            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(postRequest);
        return ;
    }

    private void displayData(List<Map<String, ArrayList<String>>> list) {

        Log.d("recycle", list.toString());
        if (list == null || list.size() == 0) {
            Toast.makeText(ResultSet.this, "No such movie! Please search again!", Toast.LENGTH_LONG).show();
        }


        LinearLayoutManager lmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmanager);
        ResultSetAdapter adapter = new ResultSetAdapter(list);
        recyclerView.setAdapter(adapter);

    }
}
