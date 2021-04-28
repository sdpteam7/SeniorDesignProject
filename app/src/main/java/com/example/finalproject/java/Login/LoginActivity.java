package com.example.finalproject.java.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.java.Database.RetrofitInstance;
import com.example.finalproject.java.Database.StatusPostApi;
import com.example.finalproject.java.Istructor.InstructorActivity;
import com.example.finalproject.java.SignUp.SignUpActivity;
import com.example.finalproject.java.Database.JsonPlaceHolderApi;
import com.example.finalproject.java.Student.StudentActivity;
import com.example.finalproject.java.types.Constants;
import com.example.finalproject.java.types.Status_ID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Constants implements LoginContractor.View {

    LoginPresenter loginPresenter;
    private FirebaseAuth firebaseAuth;
    List<Statuses> statuses;
    boolean emailver = false;
    String userName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statuses = new ArrayList<>();
        setContentView(R.layout.activity_sign_in);
        loginPresenter = new LoginPresenter(this);
        EditText username = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        TextView gosignup = findViewById(R.id.signup);
        firebaseAuth = FirebaseAuth.getInstance();

//        Intent intent1 = new Intent(getApplicationContext(), StudentActivity.class); //for testing
//        startActivity(intent1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass;
                user = username.getText().toString();
                pass = password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(user,pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(task.isSuccessful())
                                     {

                                         Log.d("SignInStatus", "createUserWithEmail:success");
                                         FirebaseUser user = firebaseAuth.getCurrentUser();
                                         userName = user.getDisplayName();
                                         if (user.isEmailVerified())
                                         {
                                             emailver = true;
                                         }
                                         else {
                                             Log.w("EmailStatus", "Verify your email");
                                             Toast.makeText(LoginActivity.this, "Please check your inbox and complete registration", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                     else {
                                         // If sign in fails, display a message to the user.
                                         Log.w("SignInStatus", "createUserWithEmail:failure", task.getException());
                                         Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                 Toast.LENGTH_SHORT).show();
                                     }
                            }
                        });

                JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.CreateInstance(baseUrl).create(JsonPlaceHolderApi.class);
                Call<List<StatusPostApi>> call = jsonPlaceHolderApi.getPost();
                call.enqueue(new Callback<List<StatusPostApi>>() {
                    @Override
                    public void onResponse(Call<List<StatusPostApi>> call, Response<List<StatusPostApi>> response) {
                        Log.i("ResponseGet", String.valueOf(response.code()));
                        List<StatusPostApi> statuses = response.body();

                        int statId = 3;

                        for (StatusPostApi status: statuses) {
                            if(status.getMail().equals(user)) {
                                Log.i("StatusId", status.getStatusId());
                                statId = Integer.parseInt(status.getStatusId());

                            }
                        }

                        Intent intent1;  // for testing
                        if(emailver) {
                            if (Status_ID.Teacher == statId) {
                                intent1 = new Intent(getApplicationContext(), InstructorActivity.class);
                                intent1.putExtra("mail", user);
                                intent1.putExtra("name", userName);
                                startActivity(intent1);
                            } else if (Status_ID.Student == statId) {
                                intent1 = new Intent(getApplicationContext(), StudentActivity.class);
                                intent1.putExtra("mail", user);
                                intent1.putExtra("name", userName);
                                startActivity(intent1);
                            } else
                                Toast.makeText(LoginActivity.this, "Xəta baş verdi", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Please check your inbox and complete registration", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<List<StatusPostApi>> call, Throwable t) {
                        Log.i("ResponseGet",t.getMessage());
                    }
                });




            }
        });
        gosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signupActivity);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser curUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public void OnSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}