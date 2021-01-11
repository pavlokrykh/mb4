package ua.kpi.comsys.iv7214.moviesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<Movie> mData;
    public int deleteMode = 0;


    public Adaptery(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie, parent, false);

        return new MyViewHolder(v);
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(deleteMode==0) {
            holder.delete.setVisibility(View.INVISIBLE);
        } else {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        holder.id.setText(mData.get(position).getImdbID());
        holder.name.setText(mData.get(position).getTitle());
        holder.year.setText(mData.get(position).getYear());

        String poster = mData.get(position).getPoster().split("\\.")[0].toLowerCase();
        int resID = getResId(poster, R.drawable.class);

        Glide.with(mContext).load(resID).into(holder.image);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent intent = new Intent(mContext, Info.class);
                intent.putExtra("image", poster);//mData.get(position).getPoster());
                intent.putExtra("name", mData.get(position).getTitle());
                intent.putExtra("year", mData.get(position).getYear());
                intent.putExtra("imdbID", mData.get(position).getImdbID());
                //new
                intent.putExtra("rated", mData.get(position).getRated());
                intent.putExtra("released", mData.get(position).getReleased());
                intent.putExtra("runtime", mData.get(position).getRuntime());
                intent.putExtra("genre", mData.get(position).getGenre());
                intent.putExtra("director", mData.get(position).getDirector());
                intent.putExtra("writer", mData.get(position).getWriter());
                intent.putExtra("actors", mData.get(position).getActors());

                intent.putExtra("plot", mData.get(position).getPlot());
                intent.putExtra("language", mData.get(position).getLanguage());
                intent.putExtra("country", mData.get(position).getCountry());
                intent.putExtra("awards", mData.get(position).getAwards());
                intent.putExtra("imdbRating", mData.get(position).getImdbRating());
                intent.putExtra("imdbVotes", mData.get(position).getImdbVotes());
                intent.putExtra("type", mData.get(position).getType());
                intent.putExtra("production", mData.get(position).getProduction());
                ((Activity) mContext).startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filterList(ArrayList<Movie> filteredList){
        mData = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView year;
        Button delete;
        ImageView image;
        RelativeLayout parentLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idText);
            name = itemView.findViewById(R.id.nameText);
            year = itemView.findViewById(R.id.yearView);
            delete = itemView.findViewById(R.id.deleteButton);
            image = itemView.findViewById(R.id.imageView2);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }



}
