package com.example.manishaagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signup_text;
    Button ButtonCirLogin;
    EditText emltext,passtext;

    private String mystring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        signup_text=findViewById(R.id.text_signup);
        ButtonCirLogin=findViewById(R.id.cirLoginButton);

        emltext=findViewById(R.id.editTextEmail);
        passtext=findViewById(R.id.editTextPassword);




        signup_text.setOnClickListener(this);
        ButtonCirLogin.setOnClickListener(this);

    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.text_signup:
                 Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            break;
            case R.id.cirLoginButton:

               final String tmpemltext= emltext.getText().toString().trim();
               final String tmppasstext=passtext.getText().toString().trim();
               if (tmpemltext.equals("Ramesh") && tmppasstext.equals("ramesh123"))
               {
                   Intent loginintent=new Intent(LoginActivity.this,EmpActivity.class);
                   loginintent.putExtra("Login Employee", emltext.getText().toString().trim());

                   startActivity(loginintent);

               }
               else if(tmpemltext.equals("Suresh") && tmppasstext.equals("suresh123"))
               {

                   Intent loginintent=new Intent(LoginActivity.this,MngrActivity.class);

                   loginintent.putExtra("Login Manager", emltext.getText().toString().trim());
                   startActivity(loginintent);
               }

            break;
        }

    }

}
