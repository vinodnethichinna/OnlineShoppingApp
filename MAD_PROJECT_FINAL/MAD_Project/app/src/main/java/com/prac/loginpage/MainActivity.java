package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

   GoogleSignInClient googleSignInClient;
    private static final String TAG = "Sign-in Error";
    int RC_SIGN_IN = 0;
    private CallbackManager callbackManager;
    private LoginButton fbLoginBtn;
    private static final String EMAIL = "email";

    static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this);

        final String filename="ItemsDB.csv";
        DummyDataLoad objCsv=new DummyDataLoad(filename);
        try{
            objCsv.imageCSV(this);
        }
        catch (IOException e)
        {
            Log.d("Exception","Problem",e.getCause());
        }




        //logo icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_app_logo_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final EditText emailAddress = (EditText) findViewById(R.id.nameTxt);
        final ImageView imgDevop = (ImageView)findViewById(R.id.devopImg);
        Button btnRegister = (Button)findViewById(R.id.registerBtn);


        //email login
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        final EditText passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        CheckBox hidePassChkBox = (CheckBox) findViewById(R.id.hidePassChk);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }

        });



        //Google sign-in integration
       GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        SignInButton googleSignIn = findViewById(R.id.googleSignIn);

        //facebook login
        fbLoginBtn = findViewById(R.id.fbSignIn);
        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                AccessToken accessToken = loginResult.getAccessToken();
            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException error)
            {

            }
        });
           //devop click listener
        imgDevop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SQLiteDevopActivity.class);
                startActivity(intent);

            }
        });

        //Login with Email address
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userTxt=(EditText)findViewById(R.id.nameTxt);
                EditText userPassword=(EditText)findViewById(R.id.passwordTxt);
                SQLiteHelper dbm=new SQLiteHelper(MainActivity.this);
                if(userTxt.getText().toString().equals("")|| userPassword.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this,"Credentials cannot be empty!!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(dbm.checkUser(userTxt.getText().toString(),userPassword.getText().toString())){
                        Toast.makeText(MainActivity.this,"User found",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this,ShoppingScreen.class));
                    }
                    else{
                        Toast.makeText(MainActivity.this,"User NOT found",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        //checkbox for hide/unhide password
        hidePassChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    //Show password
                    passwordTxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //Hide password
                    passwordTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //google sign-in integration begin
           googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(MainActivity.this, AccountInfoActivity.class);
            startActivity(intent);
            //finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null)
        {
            startActivity(new Intent(MainActivity.this, AccountInfoActivity.class));
        }
        super.onStart();
    }

    //google sign-in integration end

    }
