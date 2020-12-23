package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class SuccessRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_rating);

        RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
        final ImageView img = (ImageView) findViewById(R.id.imgGoodDay);
        ImageView btnCcontinue = (ImageView)findViewById(R.id.imgContinue);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                img.setImageResource(R.drawable.goodday);
                img.setVisibility(View.VISIBLE);

            }
        });

        btnCcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopping = new Intent(SuccessRating.this,ShoppingScreen.class);
                startActivity(shopping);
            }
        });






    }
}
