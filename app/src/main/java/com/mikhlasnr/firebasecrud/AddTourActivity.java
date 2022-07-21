package com.mikhlasnr.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhlasnr.firebasecrud.models.ModelTour;

public class AddTourActivity extends AppCompatActivity {
    EditText
            ed_add_wisata_imgurl,
            ed_add_wisata_title,
            ed_add_wisata_location,
            ed_add_wisata_rating,
            ed_add_wisata_description;


    Button btn_add_tour;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        ed_add_wisata_imgurl = findViewById(R.id.ed_add_wisata_imgurl);
        ed_add_wisata_title = findViewById(R.id.ed_add_wisata_title);
        ed_add_wisata_location = findViewById(R.id.ed_add_wisata_location);
        ed_add_wisata_rating = findViewById(R.id.ed_add_wisata_rating);
        ed_add_wisata_description = findViewById(R.id.ed_add_wisata_description);

        btn_add_tour = findViewById(R.id.btn_add_tour);

        btn_add_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getImgUrl = ed_add_wisata_imgurl.getText().toString();
                String getTitle = ed_add_wisata_title.getText().toString();
                String getLocation = ed_add_wisata_location.getText().toString();
                String getRating = ed_add_wisata_rating.getText().toString();
                String getDescription = ed_add_wisata_description.getText().toString();

                if (getImgUrl.isEmpty()) ed_add_wisata_imgurl.setError("Image Url Cant Empty");
                else if (getTitle.isEmpty()) ed_add_wisata_imgurl.setError("Title Cant Empty");
                else if (getLocation.isEmpty()) ed_add_wisata_imgurl.setError("Location Cant Empty");
                else if (getRating.isEmpty()) ed_add_wisata_imgurl.setError("Rating Cant Empty");
                else if (getDescription.isEmpty()) ed_add_wisata_imgurl.setError("Description Cant Empty");
                else {
                    db.child("Tour")
                            .push()
                            .setValue(new ModelTour(getImgUrl,getTitle,getLocation,getRating,getDescription))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddTourActivity.this,"Add New Tour Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddTourActivity.this, MainActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddTourActivity.this,"Add New Tour Faill", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}