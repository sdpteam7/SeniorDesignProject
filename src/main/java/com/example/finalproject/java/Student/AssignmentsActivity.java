package com.example.finalproject.java.Student;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.java.Database.AssignmentApi;
import com.example.finalproject.java.Database.JsonPlaceHolderApi;
import com.example.finalproject.java.Database.RetrofitInstance;
import com.example.finalproject.java.types.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentsActivity extends AppCompatActivity {
    private TextView textViewResult;
    RecyclerView recyclerView;
    AssignmentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignments_recycler);

        textViewResult = findViewById(R.id.text_view_result);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.CreateInstance(Constants.baseUrl).create(JsonPlaceHolderApi.class);
        Call<List<AssignmentApi>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<AssignmentApi>>() {
            @Override
            public void onResponse(Call<List<AssignmentApi>> call, Response<List<AssignmentApi>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<AssignmentApi> posts = response.body();

                adapter=new AssignmentsAdapter(getApplicationContext(),posts);
                recyclerView.setAdapter(adapter);

//                for (AssignmentApi post : posts) {
//                    String content = "";
//                    content += "course: " + post.getCourseId() + "\n";
//                    content += "deadline: " + post.getDeadline() + "\n";
//                    content += "point: " + post.getPoint() + "\n";
//                    content += "name: " + post.getName() + "\n";
//                    content += "Filename: " + post.getFileName() + "\n\n";
//
//                    textViewResult.append(content);
//                }
            }

            @Override
            public void onFailure(Call<List<AssignmentApi>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}

