package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final SQLiteHelper dbobj = new SQLiteHelper(this);

        TextView txtLogin = (TextView)findViewById(R.id.txtViewBtmLogin);
        Button btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            EditText name = (EditText)findViewById(R.id.editTxtUserName);
            EditText password = (EditText)findViewById(R.id.editTxtPassword);
            EditText conPw = (EditText)findViewById(R.id.editTextConfirmpw);
            EditText phone = (EditText)findViewById(R.id.editTxtPhone);
            EditText age = (EditText)findViewById(R.id.editTxtAge);
            EditText email = (EditText)findViewById(R.id.editTxtEmail);




            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String userpassword = password.getText().toString();
                String userconpw = conPw.getText().toString();
                String userphone = phone.getText().toString();
                String userage = age.getText().toString();
                String useremail = email.getText().toString();
                if(userpassword.equals(userconpw))
                {
                    boolean res= dbobj.register(username,userpassword,userphone,userage,useremail);
                    if(res == true)
                    {
                        Toast.makeText(Registration.this, "Registration Success. Go ahead and Login", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(Registration.this, "Please Check your details!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(Registration.this, "Check Password Field", Toast.LENGTH_LONG).show();





            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
