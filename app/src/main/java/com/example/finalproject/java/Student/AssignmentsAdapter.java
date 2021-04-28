package com.example.finalproject.java.Student;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.java.Database.AssignmentApi;

import java.util.List;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentViewHolder> {
    Context applicationContext;
    List<AssignmentApi> posts;

    public AssignmentsAdapter(Context applicationContext, List<AssignmentApi> posts) {
        this.applicationContext = applicationContext;
        this.posts = posts;

    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_list_layout,null);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {

        AssignmentApi post = posts.get(position);

                   String content = "";
                    content += "Tapşırığın adı: " + post.getName() + "\n";
                    content += "Son gün: " + post.getDeadline() + "\n";
                    content += "Max qiymət: " + post.getPoint() + "\n";
                    content += "Tapşırıq faylı: " + post.getFileName() + "\n\n";

                    holder.resultTv.setText(content);


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(applicationContext,SubmitActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            applicationContext.startActivity(intent);
                        }
                    });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{
        TextView resultTv;
        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            resultTv=itemView.findViewById(R.id.resultTv);
        }
    }
}
