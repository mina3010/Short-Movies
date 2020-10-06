package com.example.fish;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fish.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Video>allVideos;
    private Context context;

    public MoviesAdapter( Context context,List<Video> allVideos) {
        this.context = context;
        this.allVideos = allVideos;
    }

    @NonNull
        @Override
        public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);

            return new MoviesAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, final int position) {

            holder.title.setText(allVideos.get(position).getTitle());
            Picasso.get().setLoggingEnabled(true);
            Picasso.get().load(allVideos.get(position).getImageUrl().replace("http","https")).into(holder.videoImage);

            holder.vv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable("videoData",allVideos.get(position));
                    Intent i=new Intent(context,player.class);
                    i.putExtras(b);
                    v.getContext().startActivity(i);
                }
            });

            //holder.videoImage.setImageURI(Uri.parse(allVideos.get(position).getImageUrl()));
//            holder.bookAuthor.setText(item_books.get(position).getBookAuthor());
//
//            holder.bookImage.setImageBitmap(Images.decodeSampledBitmapFromResource(mContext.getResources(),item_books.get(position).getBookImage(),1000,1000));
//
//
//            holder.crdView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent in = new Intent(mContext, Open_PDF.class);
//                    in.putExtra("bookName", item_books.get(position).getBookName());
//                    mContext.startActivity(in);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return allVideos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title,bookAuthor;
            ImageView videoImage;
            View vv;
            //CardView crdView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
//                bookAuthor = itemView.findViewById(R.id.BookAuthor);
                videoImage = (ImageView) itemView.findViewById(R.id.movieImage);
                vv= itemView;
               // crdView = itemView.findViewById(R.id.cardView_Movie);
            }
        }
    }


