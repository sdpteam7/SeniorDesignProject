package com.example.finalproject.java.Istructor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.finalproject.R;
import com.example.finalproject.java.Database.AssignmentApi;
import com.example.finalproject.java.Database.JsonPlaceHolderApi;
import com.example.finalproject.java.Database.RetrofitInstance;
import com.example.finalproject.java.types.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class UploadContent extends Fragment implements NumberPicker.OnValueChangeListener{

    Spinner subjects;
    Button openDatPicker, openNumPicker, uploadFiles, addAssign;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText assignName;
    public Uri fileUri;

    private String courseId;
    private String deadline;
    private String point;
    private String name;
    private String fileName;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_content, container, false);

        subjects = view.findViewById(R.id.subjects);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.fennler, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjects.setAdapter(adapter);
        subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Log.i("SpinnerItem", item + " " + position);
                courseId = String.valueOf(position-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        openDatPicker = view.findViewById(R.id.opDatePicker);
        openDatPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                deadline = date;
                openDatPicker.setText(date);
            }
        };

        openNumPicker = view.findViewById(R.id.opNumberPicker);
        openNumPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        uploadFiles = view.findViewById(R.id.uploadFiles);
        uploadFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        assignName = view.findViewById(R.id.assignName);
        addAssign = view.findViewById(R.id.addAssign);
        addAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadContent();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void show()
    {

        final Dialog d = new Dialog(getContext());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(100);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = String.valueOf(np.getValue());
                openNumPicker.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }

    private void chooseFile()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        String[] mimetypes = {"application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
            fileUri = data.getData();
        //uploadFile();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void uploadFile() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Uploading File...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference reference = storageReference.child("files/"+randomKey);
        fileName = "files/"+randomKey;

        reference.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Log.d("OnSuccess","File Uploaded");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Log.d("OnFailure","Failed to Upload");
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: "+(int)progressPercentage+"%");
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void uploadContent()
    {
        name = assignName.getText().toString();
        uploadFile();
        AssignmentApi post = new AssignmentApi(courseId, deadline, point, name, fileName);
        JsonPlaceHolderApi jsonPlaceHolderApi =
                RetrofitInstance.CreateInstance(Constants.baseUrl).create(JsonPlaceHolderApi.class);
        Call<AssignmentApi> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<AssignmentApi>() {
            @Override
            public void onResponse(Call<AssignmentApi> call, Response<AssignmentApi> response) {
                Log.i("AssignmentPost", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<AssignmentApi> call, Throwable t) {
                Log.i("AssignmentPost", t.getMessage());
            }
        });

    }
}