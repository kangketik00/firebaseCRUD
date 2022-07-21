package com.mikhlasnr.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhlasnr.firebasecrud.models.ModelTour;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn_goto_activity_add_tour;
    AdapterTour adapterTour;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelTour> listTour;
    RecyclerView tour_collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTourCollection();
        btn_goto_activity_add_tour = findViewById(R.id.btn_goto_activity_add_tour);
        btn_goto_activity_add_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTourActivity.class));
            }
        });

        tour_collection = findViewById(R.id.tour_collection);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        tour_collection.setLayoutManager(layoutManager);
        tour_collection.setItemAnimator(new DefaultItemAnimator());
        showTourCollection();


    }

    private void showTourCollection(){
            database.child("Tour").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listTour = new ArrayList<>();
                    for(DataSnapshot item : snapshot.getChildren()){
                        ModelTour tour = item.getValue(ModelTour.class);
                        tour.setKey(item.getKey());
                        listTour.add(tour);
                    }
                    adapterTour = new AdapterTour(listTour, MainActivity.this);
                    tour_collection.setAdapter(adapterTour);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
}