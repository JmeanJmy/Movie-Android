package com.example.jiangmingyu.movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangmingyu on 2018/3/5.
 */

public class ResultSetAdapter extends RecyclerView.Adapter {

    private List<Map<String, ArrayList<String>>> movie = new ArrayList<Map<String, ArrayList<String>>>();
    private int coposition = 0;

    public ResultSetAdapter(List<Map<String, ArrayList<String>>> movie) {this.movie = movie;}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        return new ResultSetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Map<String, ArrayList<String>> movie_detail = movie.get(position);
        ResultSetViewHolder rsHolder = (ResultSetViewHolder) holder;
        rsHolder.movie_info.setText(movie_detail.get("id").get(0) + "  " + movie_detail.get("title").get(0)
                + "  " + movie_detail.get("year").get(0));

        rsHolder.star_genre.setText("Director:  " + movie_detail.get("director").get(0) + "\n\n" + "Stars:  " + movie_detail.get("stars").toString().replaceAll("[\\[\\]]", "") + "\n\n"
                + "Genres:  " + movie_detail.get("genres").toString().replaceAll("[\\[\\]]", ""));

    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public int getDataCount(){
        return movie.size();
    }
}
