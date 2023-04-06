package com.example.kitabmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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

public class SearchActivity extends AppCompatActivity {

    List< Categories > list;
    RecyclerView rv;
    SearchView searchView;
    Myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        searchView = findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        list = new ArrayList<>();
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call< CategoryModel > categoryModelCall = networkService.getCategory();
        categoryModelCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {

                if (response != null && response.body() != null) {
                    list = response.body().getCategories();
                    adapter = new Myadapter(list);
                    rv.setAdapter(adapter);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adapter.filter(newText);
                            return false;
                        }
                    });
                } else {
                    Toast.makeText(SearchActivity.this, "data failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
        List<Categories> temp = new ArrayList<>();
        List<Categories> searchList = new ArrayList<>();


        Myadapter(List<Categories> list) {
            temp.addAll(list);
            searchList.addAll(list);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.category_container, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(SearchActivity.this).load(searchList.get(position).getImageView()).into(holder.ImageView);
            holder.textView.setText(searchList.get(position).getName());

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, kitabmartListActivity.class);
                    intent.putExtra("id", searchList.get(holder.getAdapterPosition()).getId());
                    startActivity(intent);
                }
            });
        }

        void filter(String text) {
            searchList.clear();
            if (text != "") {
                for (Categories c : temp) {
                    if (c.getName().toLowerCase().contains(text.toLowerCase())) {
                        searchList.add(c);
                    }
                }
            } else {
                searchList.addAll(temp);
            }
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return searchList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            RelativeLayout container;
            android.widget.ImageView ImageView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.txt);
                container = itemView.findViewById(R.id.container);
                ImageView = itemView.findViewById(R.id.ImageView);
            }
        }
    }

}