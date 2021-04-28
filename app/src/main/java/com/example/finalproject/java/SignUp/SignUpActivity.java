package com.example.finalproject.java.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.java.Database.JsonPlaceHolderApi;
import com.example.finalproject.java.Database.Members;
import com.example.finalproject.java.Database.RetrofitInstance;
import com.example.finalproject.java.Database.StatusPostApi;
import com.example.finalproject.java.Istructor.InstructorActivity;
import com.example.finalproject.java.Login.LoginActivity;
import com.example.finalproject.java.Student.StudentActivity;
import com.example.finalproject.java.types.Constants;
import com.example.finalproject.java.types.Status_ID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends Constants implements SignUpContractor.View{

    SignUpPresenter signUpPresenter;
    private FirebaseAuth firebaseAuth;
    FirebaseUser curUser;

    DatabaseReference myRef;
    Members member;
    long maxId;
    long userStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        signUpPresenter = new SignUpPresenter(this);

        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText pass = findViewById(R.id.password);
        Button signup = findViewById(R.id.signup);
        myRef = FirebaseDatabase.getInstance().getReference().child("Members");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxId = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        member = new Members();
        SwitchCompat switcher = findViewById(R.id.switcher);
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    userStatus = Status_ID.Teacher;
                    switcher.setText("Müəllim");
                }
                else {
                    userStatus = Status_ID.Student;
                    switcher.setText("Tələbə");
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost(email.getText().toString(), String.valueOf(userStatus));



                final ProgressDialog pd = new ProgressDialog(SignUpActivity.this);
                pd.setTitle("Qeydiyyat davam edir...");
                pd.show();

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SignUpStatus", "createUserWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username.getText().toString())
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        user.sendEmailVerification();
                                                        Log.d("ProfileName", "User profile updated.");
                                                        Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                                                        startActivity(loginActivity);
                                                    }
                                                }
                                            });

                                    member.setEmail(email.getText().toString());
                                    member.setSubject(userStatus);
                                    myRef.child(String.valueOf(maxId+1)).setValue(member);

                                }else {
                                    pd.dismiss();
                                    // If sign in fails, display a message to the user.
                                    Log.w("SignUpStatus", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        curUser = firebaseAuth.getCurrentUser();
    }

    private void createPost(String mail, String statusId)
    {
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.CreateInstance(baseUrl).create(JsonPlaceHolderApi.class);
        StatusPostApi postApi =new StatusPostApi(mail, statusId);

        Call<StatusPostApi> call = jsonPlaceHolderApi.createPost(postApi);

        call.enqueue(new Callback<StatusPostApi>() {
            @Override
            public void onResponse(Call<StatusPostApi> call, Response<StatusPostApi> response) {
                Log.i("ResponsePost", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<StatusPostApi> call, Throwable t) {
                Log.i("ResponsePost", t.getMessage());
            }
        });
    }
}