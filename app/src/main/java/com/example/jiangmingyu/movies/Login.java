package com.example.jiangmingyu.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    private static final String url_base = "http://ec2-13-59-67-98.us-east-2.compute.amazonaws.com:8080/fabflix/servlet/Login";
    private final String Email = "userEmail";
    private final String Passwd = "userPasswd";
    String user_email;
    String user_password;

    @BindView(R.id.user_email) EditText email;
    @BindView(R.id.user_password) EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void login(View view){

        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);
        final Context context = this;

        user_email = email.getText().toString().trim();
        user_password = password.getText().toString().trim();

        if (user_email.equals("") || user_password.equals("")) {
            Toast.makeText(Login.this, "Please enter email or password", Toast.LENGTH_LONG).show();
            return;
        }


        String url = url_base + "?" + Email + "=" + user_email + "&" + Passwd + "=" + user_password;
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        if (response.equals("OK")) {

                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(Login.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                        }

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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Response<String> superResponse = super.parseNetworkResponse(response);
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");

                Constant.localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                Log.d("sessionid", "sessionid----------------" + Constant.localCookie);
                return superResponse;
            }

            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(postRequest);
        return;

    }
}
