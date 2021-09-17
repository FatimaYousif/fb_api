package com.example.fb_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class User extends AppCompatActivity {

    ImageView img;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent i=getIntent();

        img=(ImageView)findViewById(R.id.img);
        textView=(TextView)findViewById(R.id.name);

        //displaying the logged user's profile image and the username


        textView.setText("username:\n"+ i.getStringExtra("name"));
        Picasso.get().load(i.getStringExtra("img")).into(img);


    }
}