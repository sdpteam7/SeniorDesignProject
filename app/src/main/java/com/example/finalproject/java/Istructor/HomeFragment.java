package com.example.finalproject.java.Istructor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.finalproject.R;

import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment{
    private static int RESULT_LOAD_IMAGE = 1;
    TextView mail;
    TextView user;
    ImageView profPicture;
    Button upload;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout_in,container,false);
        user=view.findViewById(R.id.username);
        mail=view.findViewById(R.id.email);
        user.setText(InstructorActivity.username);
        mail.setText(InstructorActivity.email);
        profPicture = view.findViewById(R.id.profile_image);
        profPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        upload = view.findViewById(R.id.uploadContent);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new UploadContent())
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Context applicationContext = InstructorActivity.getContextOfApplication();
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(applicationContext.getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            profPicture.setImageBitmap(bitmap);
        }
    }


}
