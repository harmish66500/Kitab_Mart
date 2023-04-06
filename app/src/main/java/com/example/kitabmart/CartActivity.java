package com.example.kitabmart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kitabmart.Models.kitabmartModel;
import com.example.kitabmart.database.DatabaseHalper;
import com.example.kitabmart.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView rv;
    List< kitabmartModel > list;
    TextView total,place;
    DatabaseHalper databaseHalper;
    LinearLayout nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
            total = findViewById(R.id.total);
            nodata = findViewById(R.id.nodata);
            list = new ArrayList<>();
            place = findViewById(R.id.place);
            rv = findViewById(R.id.rv);
            rv.setLayoutManager(new LinearLayoutManager(CartActivity.this));
            rv.setHasFixedSize(true);

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                placeOrder();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getData();
    }
    void getData() {
        list.clear();
        DatabaseHalper databaseHalper = new DatabaseHalper(CartActivity.this);
        Cursor cursor = databaseHalper.getData();
        if (cursor.moveToFirst()) {
            do {
                list.add(new kitabmartModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(6), cursor.getString(6), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        total.setText("");
        if (list != null && list.size() > 0){
            rv.setAdapter(new Myadapter());
        getTotal(list);
        rv.setVisibility(View.VISIBLE);
        nodata.setVisibility(View.GONE);
        place.setEnabled(true);
        place.setAlpha(1);
    }else {
            rv.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            place.setEnabled(false);
            place.setAlpha(.5f);}
    }
    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
    {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ViewHolder(LayoutInflater.from(CartActivity.this).inflate(R.layout.cart_cotainer,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.textView.setText(list.get(position).getTitalName());
            holder.author.setText(list.get(position).getAuthor());
            holder.price.setText(list.get(position).getPrice());
            Glide.with(CartActivity.this).load(list.get(position).getPhoto()).into(holder.ImageView);
            holder.menu.setOnClickListener(new View.OnClickListener()
            {

                @Override
                    public void onClick(View v) {


                    DatabaseHalper databaseHalper = new DatabaseHalper(CartActivity.this);
                    Cursor cursor = databaseHalper.getData();

                    PopupMenu popupMenu = new PopupMenu(CartActivity.this, holder.menu);

                        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {

                                switch (menuItem.getItemId()) {
                                    case R.id.remove:
                                        Dialog dialog = new Dialog(CartActivity.this);
                                        dialog.setContentView(R.layout.delete_dialog);
                                        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                        dialog.show();
                                        dialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                databaseHalper.deleteItem(list.get(position).getId());
                                                getData();
                                                notifyDataSetChanged();
                                                getTotal(list);
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    default:
                                        return true;
                                }
                            }
                        });
                        popupMenu.show();

                    }
                });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class  ViewHolder extends RecyclerView.ViewHolder{

            TextView textView,author,price,type;
            LinearLayout container;
            android.widget.ImageView ImageView;
            ImageButton menu;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                menu = itemView.findViewById(R.id.menu);
                textView = itemView.findViewById(R.id.tname);
                container = itemView.findViewById(R.id.container_main);
                ImageView = itemView.findViewById(R.id.image);
                author = itemView.findViewById(R.id.author);
                price = itemView.findViewById(R.id.price);
                type = itemView.findViewById(R.id.type);
            }
        }
    }
    void getTotal(List<kitabmartModel> list)
    {
        long price = 0;
        for(int i = 0;i < list.size() ; i++ )
        {
            price += Integer.valueOf(list.get(i).getPrice());
        }
        total.setText("₹"+price+"");
    }
    void placeOrder() {
        Constants.list = list;
        startActivity(new Intent(CartActivity.this, PlaceOrderActivity.class).putExtra("price",total.getText().toString()));

    }
}


