package com.example.finalproject.java.Student;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.io.File;


public class SubmitActivity extends AppCompatActivity {
    Button button2, button;
    Uri pdf = null;
    TextView fileNameTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit);
        button2 = findViewById(R.id.button2);
        button = findViewById(R.id.button);
        fileNameTv = findViewById(R.id.fileNameTv);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent,"Select PDF file"),1122);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdf == null){
                    Toast.makeText(SubmitActivity.this,
                            "upload file",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SubmitActivity.this,
                            "Fayl göndərildi",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        switch (requestCode) {
            case 1122:
                if (resultCode == RESULT_OK) {
                    // pdf=data.getData();
                    // Toast.makeText(this, "" + data.getDataString().substring(data.getDataString().lastIndexOf("/")+1),
                    //                 Toast.LENGTH_SHORT).show();
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    pdf = uri;
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = SubmitActivity.this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Toast.makeText(this, "" + displayName,
                                        Toast.LENGTH_SHORT).show();
                                fileNameTv.setText(displayName);
                                fileNameTv.setVisibility(View.VISIBLE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Toast.makeText(this, "" + displayName,
                                Toast.LENGTH_SHORT).show();
                        fileNameTv.setText(displayName);
                        fileNameTv.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}