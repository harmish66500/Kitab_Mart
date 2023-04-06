package com.example.kitabmart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kitabmart.Models.Categories;
import com.example.kitabmart.Models.CategoryModel;
import com.example.kitabmart.network.NetworkClient;
import com.example.kitabmart.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    List< Categories > list;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        findViewById(R.id.search_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this,SearchActivity.class));
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        list = new ArrayList<>();

        Dialog Dialog = new Dialog(CategoryActivity.this);
        Dialog.setContentView(R.layout.loading_dialog);
        Dialog.setTitle("Loading..");
        Dialog.setCancelable(false);
        Dialog.show();
        /*alertDialog.setTitle("Loading..");
        alertDialog.setMessage("Please wait...");
        alertDialog.setCancelable(false);
        alertDialog.show();*/
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call< CategoryModel > categoryModelCall = networkService.getCategory();
        categoryModelCall.enqueue(new Callback<CategoryModel>()
        {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                Dialog.dismiss();
                if (response != null && response.body() != null) {
                    list = response.body().getCategories();
                    rv.setAdapter(new Myadapter());
                }
                else{

                    Toast.makeText(CategoryActivity.this, "data failed", Toast.LENGTH_SHORT).show();

                }
                list = response.body().getCategories();
                rv.setAdapter(new Myadapter());
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Dialog.dismiss();
                Toast.makeText(CategoryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
    {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ViewHolder(LayoutInflater.from(CategoryActivity.this).inflate(R.layout.category_container,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Glide.with(CategoryActivity.this).load(list.get(position).getImageView()).into(holder.ImageView);
            holder.textView.setText(list.get(position).getName());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CategoryActivity.this,kitabmartListActivity.class);
                    intent.putExtra("id",list.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("ImageView", list.get(position).getImageView() + "");
                    intent.putExtra("name",list.get(position).getName());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class  ViewHolder extends RecyclerView.ViewHolder{

            TextView textView;
            RelativeLayout container;
            ImageView ImageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.txt);
                container = itemView.findViewById(R.id.container);
                ImageView = itemView.findViewById(R.id.ImageView);
            }
        }
    }

}