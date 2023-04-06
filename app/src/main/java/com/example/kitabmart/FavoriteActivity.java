package com.example.kitabmart;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kitabmart.Models.kitabmartModel;
import com.example.kitabmart.database.DatabaseHalper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView fv;
    List< kitabmartModel > list;
    //TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        //total = findViewById(R.id.total);
        list = new ArrayList<>();
        fv = findViewById(R.id.fv);
        fv.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
        fv.setHasFixedSize(true);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // Toast.makeText(CartActivity.this, "" + databaseHalper.getData(), Toast.LENGTH_SHORT).show();
        getData1();
    }
    void getData1(){
        DatabaseHalper databaseHalper = new DatabaseHalper(FavoriteActivity.this);
        Cursor cursor = databaseHalper.getData1();
        if(cursor.moveToFirst())
        {
            do{
                list.add(new kitabmartModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(6),cursor.getString(6),cursor.getString(5),cursor.getString(6),cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        if(list != null && list.size()>0)
            fv.setAdapter(new Myadapter());
        //getTotal(list);
        //Toast.makeText(CartActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
    {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ViewHolder(LayoutInflater.from(FavoriteActivity.this).inflate(R.layout.kitabmartcontainer,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            //Glide.with(CartActivity.this).load(list.get(position).getPhoto()).into(holder.ImageView);
            holder.textView.setText(list.get(position).getTitalName());
            holder.author.setText(list.get(position).getAuthor());
            holder.price.setText(list.get(position).getPrice());
            // holder.type.setText(list.get(position).getType());
            Glide.with(FavoriteActivity.this).load(list.get(position).getPhoto()).into(holder.ImageView);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class  ViewHolder extends RecyclerView.ViewHolder{

            TextView textView,author,price,type;
            LinearLayout container;
            android.widget.ImageView ImageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.tname);
                container = itemView.findViewById(R.id.container_main);
                ImageView = itemView.findViewById(R.id.image);
                author = itemView.findViewById(R.id.author);
                price = itemView.findViewById(R.id.price);
                type = itemView.findViewById(R.id.type);
            }
        }
    }
 /*   void getTotal(List<kitabmartModel> list)
    {
        long price = 0;
        for(int i = 0;i < list.size() ; i++ )
        {
            price += Integer.valueOf(list.get(i).getPrice());
        }
        total.setText("₹"+price+"");
    }
*/
}


