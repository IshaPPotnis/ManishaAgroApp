package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.manishaagro.R.id.cirLoginButton;
import static com.example.manishaagro.R.id.text_signup;
import static com.example.manishaagro.R.layout.activity_login;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signUpText;
    Button ButtonCirLogin;
    EditText emlText, passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        signUpText = findViewById(text_signup);
        ButtonCirLogin = findViewById(cirLoginButton);
        emlText = findViewById(R.id.editTextEmail);
        passText = findViewById(R.id.editTextPassword);
        signUpText.setOnClickListener(this);
        ButtonCirLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent loginIntent = null;
        switch (v.getId()) {
            case text_signup:
                loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                break;
            case cirLoginButton:
                final String employeeNameText = emlText.getText().toString().trim();
                final String employeePasswordText = passText.getText().toString().trim();
                if (employeeNameText.equals("Ramesh") && employeePasswordText.equals("ramesh123")) {
                    loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                    loginIntent.putExtra("Login Employee", emlText.getText().toString().trim());
                } else if (employeeNameText.equals("Suresh") && employeePasswordText.equals("suresh123")) {
                    loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                    loginIntent.putExtra("Login Manager", emlText.getText().toString().trim());
                }
                break;
        }
        startActivity(loginIntent);
        finish();
    }
}
