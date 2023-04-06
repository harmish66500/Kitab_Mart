package com.example.kitabmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.kitabmart.Models.kitabmartModel;
import com.example.kitabmart.database.DatabaseHalper;

public class kitabmartDetailActivity extends AppCompatActivity {
        LinearLayout main;
        ImageView imageView;
        TextView tname, price, type, author, sine, des,hadder;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_kitabmart_detail);

            hadder = findViewById(R.id.hadder);
            if(getIntent() != null)
                hadder.setText(getIntent().getStringExtra("tname"));

            findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            findViewById(R.id.mycart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(kitabmartDetailActivity.this,CartActivity.class));
                }
            });
findViewById(R.id.pdf).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent  =new Intent(kitabmartDetailActivity.this,pdf_view.class);
        intent.putExtra("URL",getIntent().getStringExtra("pdf"));
        startActivity(intent);
    }
});
            findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHalper databaseHalper  = new DatabaseHalper(getApplicationContext());
                    kitabmartModel kitabmartModel = new kitabmartModel();
                    kitabmartModel.setTitalName(getIntent().getStringExtra("tname"));
                    kitabmartModel.setAuthor(getIntent().getStringExtra("author"));
                    kitabmartModel.setType(getIntent().getStringExtra("type"));
                    kitabmartModel.setSine(getIntent().getStringExtra("sine"));
                    kitabmartModel.setPrice(getIntent().getStringExtra("price"));
                    kitabmartModel.setPhoto(getIntent().getStringExtra("photo"));
                    //kitabmartModel.setDescription(getIntent().getStringExtra("des"));
                    databaseHalper.insert(kitabmartModel);
                    Toast.makeText(getApplicationContext(),"Item Added to Cart..",Toast.LENGTH_SHORT).show();
                }

            });


            findViewById(R.id.favorite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHalper databaseHalper  = new DatabaseHalper(getApplicationContext());
                    kitabmartModel kitabmartModel = new kitabmartModel();
                    kitabmartModel.setTitalName(getIntent().getStringExtra("tname"));
                    kitabmartModel.setAuthor(getIntent().getStringExtra("author"));
                    kitabmartModel.setType(getIntent().getStringExtra("type"));
                    kitabmartModel.setSine(getIntent().getStringExtra("sine"));
                    kitabmartModel.setPrice(getIntent().getStringExtra("price"));
                    kitabmartModel.setPhoto(getIntent().getStringExtra("photo"));
                    //kitabmartModel.setDescription(getIntent().getStringExtra("des"));
                    databaseHalper.insert1(kitabmartModel);
                    Toast.makeText(getApplicationContext(),"Item Added to Favorite..",Toast.LENGTH_SHORT).show();
                }

            });

            imageView = findViewById(R.id.image);
            main = findViewById(R.id.container_main);
            tname = findViewById(R.id.tname);
            price = findViewById(R.id.price);
            sine = findViewById(R.id.sine);
            author = findViewById(R.id.author);
            des = findViewById(R.id.des);
            type = findViewById(R.id.type);


            Glide.with(kitabmartDetailActivity.this).load(getIntent().getStringExtra("photo")).into(imageView);
           tname.setText(getIntent().getStringExtra("tname"));
           type.setText(getIntent().getStringExtra("type"));
           author.setText(getIntent().getStringExtra("author"));
           price.setText(getIntent().getStringExtra("price"));
           sine.setText(getIntent().getStringExtra("sine"));
           des.setText(getIntent().getStringExtra("des"));



        }
    }



