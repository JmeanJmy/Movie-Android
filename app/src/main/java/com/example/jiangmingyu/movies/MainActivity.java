package com.example.jiangmingyu.movies;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search_edit_text) EditText m_search;
    public static final String REQ_CODE = "KeyWords";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        search();
    }

    private void search() {


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.movie_search_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultSet.class);
                String title = m_search.getText().toString().trim();
                if (title.equals("")) {
                    Toast.makeText(MainActivity.this, "Key words cannot be null!", Toast.LENGTH_LONG).show();
                    return;
                }
                intent.putExtra(REQ_CODE, title);
                startActivity(intent);
            }
        });

    }


}
