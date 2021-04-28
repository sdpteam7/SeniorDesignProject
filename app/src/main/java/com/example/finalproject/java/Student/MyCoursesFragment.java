package com.example.finalproject.java.Student;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.finalproject.R;
import com.example.finalproject.java.Common.AllCoursesFragment;
import com.example.finalproject.java.Common.AllLecturesFragment;
import com.example.finalproject.java.Database.EnrollPostApi;
import com.example.finalproject.java.Database.JsonPlaceHolderApi;
import com.example.finalproject.java.Database.RetrofitInstance;
import com.example.finalproject.java.Istructor.InstructorActivity;
import com.example.finalproject.java.types.Constants;
import com.example.finalproject.java.types.CoursesId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesFragment extends Fragment{

    TextView mathField, physField, azField, chemField, histField;
    TextView mathLec, physLec, azLec, chemLec, histLec;
    LinearLayout mathChooser, physChooser, azChooser, chemChooser, histChooser;
    String mail="";
    List<String> myCourses;
    public static String courseId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycourses,container,false);

        myCourses = new ArrayList<>();
        getMyCourses();

        if(StudentActivity.email==null)
            mail = InstructorActivity.email;
        else
            mail = StudentActivity.email;

        mathLec = view.findViewById(R.id.mathLec);
        mathLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseId = CoursesId.Math;
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AllLecturesFragment())
                        .commit();
            }
        });
        physLec = view.findViewById(R.id.physLec);
        physLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseId = CoursesId.Phys;
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AllLecturesFragment())
                        .commit();
            }
        });
        azLec = view.findViewById(R.id.azLec);
        azLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseId = CoursesId.Az;
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AllLecturesFragment())
                        .commit();
            }
        });
        chemLec = view.findViewById(R.id.chemLec);
        chemLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseId = CoursesId.Chem;
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AllLecturesFragment())
                        .commit();
            }
        });
        histLec = view.findViewById(R.id.histLec);
        histLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseId = CoursesId.Hist;
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AllLecturesFragment())
                        .commit();
            }
        });

        mathField = view.findViewById(R.id.mathField);
        mathField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ln = view.findViewById(R.id.mathChooser);
                if(ln.getVisibility()==View.GONE)
                    ln.setVisibility(View.VISIBLE);
                else
                    ln.setVisibility(View.GONE);
            }
        });
        physField = view.findViewById(R.id.physField);
        physField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ln = view.findViewById(R.id.physChooser);
                if(ln.getVisibility()==View.GONE)
                    ln.setVisibility(View.VISIBLE);
                else
                    ln.setVisibility(View.GONE);
            }
        });
        azField = view.findViewById(R.id.azField);
        azField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ln = view.findViewById(R.id.azChooser);
                if(ln.getVisibility()==View.GONE)
                    ln.setVisibility(View.VISIBLE);
                else
                    ln.setVisibility(View.GONE);
            }
        });
        chemField = view.findViewById(R.id.chemField);
        chemField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ln = view.findViewById(R.id.chemChooser);
                if(ln.getVisibility()==View.GONE)
                    ln.setVisibility(View.VISIBLE);
                else
                    ln.setVisibility(View.GONE);
            }
        });
        histField = view.findViewById(R.id.histField);
        histField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ln = view.findViewById(R.id.histChooser);
                if(ln.getVisibility()==View.GONE)
                    ln.setVisibility(View.VISIBLE);
                else
                    ln.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void getMyCourses()
    {
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.CreateInstance(Constants.baseUrl).create(JsonPlaceHolderApi.class);
        Call<List<EnrollPostApi>> call = jsonPlaceHolderApi.getCourses();

        call.enqueue(new Callback<List<EnrollPostApi>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<EnrollPostApi>> call, Response<List<EnrollPostApi>> response) {
                Log.i("CourseResponse", String.valueOf(response.code()));
                List<EnrollPostApi> courses = response.body();

                for (EnrollPostApi course:
                        courses) {
                    if(course.getMail().equals(mail)) {
                        myCourses.add(course.getCourseName());
                        if(course.getCourseName().equals(CoursesId.Math))
                            mathField.setVisibility(View.VISIBLE);
                        if(course.getCourseName().equals(CoursesId.Phys))
                            physField.setVisibility(View.VISIBLE);
                        if(course.getCourseName().equals(CoursesId.Az))
                            azField.setVisibility(View.VISIBLE);
                        if(course.getCourseName().equals(CoursesId.Chem))
                            chemField.setVisibility(View.VISIBLE);
                        if(course.getCourseName().equals(CoursesId.Hist))
                            histField.setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<EnrollPostApi>> call, Throwable t) {
                Log.i("CourseResponse", t.getMessage());
            }
        });
    }
}
