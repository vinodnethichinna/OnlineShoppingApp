package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonProceed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_proceed);

        ImageButton imgBtn = (ImageButton)findViewById(R.id.imgBtnPayment);
        final EditText name = (EditText)findViewById(R.id.edtTxtName);
        final EditText phone = (EditText)findViewById(R.id.edtTxtPhone);
        final EditText mail = (EditText)findViewById(R.id.edtTxtMailing);
        final EditText cardNum = (EditText)findViewById(R.id.edtTxtCardNumber);
        final EditText expiry = (EditText)findViewById(R.id.edtTxtExpiry);
        final EditText cvv = (EditText)findViewById(R.id.edtTxtCVV);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(ButtonProceed.this,SuccessRating.class);
                    startActivity(intent);
                }
                catch(Exception e)
                {
                    e.getMessage();
                }

            }
        });



    }



}
