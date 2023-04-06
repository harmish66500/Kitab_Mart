package com.example.kitabmart;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kitabmart.Models.kitabmartModel;
import com.example.kitabmart.Models.kitabmartResponseModel;
import com.example.kitabmart.network.NetworkClient;
import com.example.kitabmart.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class kitabmartListActivity extends AppCompatActivity {

    RecyclerView rv;
    List< kitabmartModel > list;
    TextView hadder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_kitabmart_list);
        hadder = findViewById(R.id.hadder);
        if(getIntent() != null)
        hadder.setText(getIntent().getStringExtra("name"));

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(kitabmartListActivity.this));
        rv.setHasFixedSize(true);
        list = new ArrayList<>();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Dialog Dialog = new Dialog(kitabmartListActivity.this);
        Dialog.setContentView(R.layout.loading_dialog);
        Dialog.setTitle("Loading..");
        Dialog.setCancelable(false);
        Dialog.show();
        /*ProgressDialog alertDialog = new ProgressDialog(kitabmartListActivity.this);
        alertDialog.setContentView(R.layout.delete_dialog);
        *//*alertDialog.setTitle("Loading..");
        alertDialog.setMessage("Please wait...");*//*
        alertDialog.setCancelable(false);
        alertDialog.show();*/
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call< com.example.kitabmart.Models.kitabmartResponseModel > call = networkService.getkitabmart(getIntent().getStringExtra("id"));
        call.enqueue(new Callback< kitabmartResponseModel >() {
            @Override
            public void onResponse(Call<kitabmartResponseModel> call, Response<kitabmartResponseModel> response) {
                Dialog.dismiss();
                if(response != null && response.body() != null )
                {
                    list = response.body().getkitabmart();
                    rv.setAdapter(new kitabmartadpter());
                }else
                {
                    Toast.makeText(kitabmartListActivity.this, "data failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<kitabmartResponseModel> call, Throwable t) {
                Toast.makeText(kitabmartListActivity.this, "data failed", Toast.LENGTH_SHORT).show();
                Dialog.dismiss();

            }
        });

    }


    class kitabmartadpter extends RecyclerView.Adapter<kitabmartadpter.ViewHolder>{


        @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(kitabmartListActivity.this).inflate(R.layout.kitabmartcontainer, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

                Glide.with(kitabmartListActivity.this).load(list.get(position).getPhoto()).into(holder.imageView);
                  holder.tname.setText(list.get(position).getTitalName());
                  holder.author.setText(list.get(position).getAuthor());
                  holder.price.setText(list.get(position).getPrice());

                  holder.main.setOnClickListener(new View.OnClickListener() {
                /*    @Override
                    public void onClick(View v) {

                  holder.menu.setOnClickListener(new View.OnClickListener(){


                        PopupMenu popupMenu = new PopupMenu(kitabmartListActivity.this, holder.menu);

                        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.cart:

Intent intent1 = new Intent(kitabmartListActivity.this, FavoriteActivity.class);//firstActivity
                                        startActivity(intent1);

                                        return true;
                                    case  R.id.favorite:
                                        Intent intent1 = new Intent(kitabmartListActivity.this, FavoriteActivity.class);//firstActivity
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu.show();

                    }
                });
*/


                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(kitabmartListActivity.this, kitabmartDetailActivity.class);
                        intent.putExtra("tname", list.get(position).getTitalName());
                        intent.putExtra("type", list.get(position).getType());
                        intent.putExtra("price", list.get(position).getPrice());
                        intent.putExtra("author", list.get(position).getAuthor());
                        intent.putExtra("sine", list.get(position).getSine());
                        intent.putExtra("des", list.get(position).getDescription());
                        intent.putExtra("photo", list.get(position).getPhoto() + "");
                        intent.putExtra("pdf", list.get(position).getPdf() + "");
                        startActivity(intent);

                    }
                });

            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                LinearLayout main;
                ImageView imageView;
                ImageButton menu;
                TextView tname, price, type, author ;

                public ViewHolder(@NonNull View view) {
                    super(view);

                    imageView = view.findViewById(R.id.image);
                    main = view.findViewById(R.id.container_main);
                   tname = view.findViewById(R.id.tname);
                   price = view.findViewById(R.id.price);
                   menu = view.findViewById(R.id.menu);
                    author = view.findViewById(R.id.author);
                   type=view.findViewById(R.id.type);

                }
            }

        }

    }
