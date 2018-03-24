package com.example.jiangmingyu.movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangmingyu on 2018/3/5.
 */

public class ResultSetViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.movie_info) TextView movie_info;
    @BindView(R.id.star_genre) TextView star_genre;
    public ResultSetViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
