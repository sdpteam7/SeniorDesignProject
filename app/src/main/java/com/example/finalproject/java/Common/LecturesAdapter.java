package com.example.finalproject.java.Common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

public class LecturesAdapter extends RecyclerView.Adapter<LecturesAdapter.ViewHolder> {


    public LecturesAdapter(Context context, String[] lectures, String[] lecturesLinks) {
        this.context = context;
        this.lectures = lectures;
        this.lecturesLinks = lecturesLinks;
    }

    Context context;
    String[] lectures;
    String[] lecturesLinks;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lectures_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lecture.setText(lectures[position]);
        holder.lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lecturesLinks[position]));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lecturesLinks.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lecture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lecture = itemView.findViewById(R.id.textView);
            lecture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("lectureTest",lecture.getText().toString());
                    //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                    //context.startActivity(browserIntent);
                }
            });
        }
    }
}
