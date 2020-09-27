package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.manishaagro.R.id.cirLoginButton;
import static com.example.manishaagro.R.layout.activity_login;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button ButtonCirLogin;
    EditText emlText, passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        ButtonCirLogin = findViewById(cirLoginButton);
        emlText = findViewById(R.id.editTextEmail);
        passText = findViewById(R.id.editTextPassword);
        ButtonCirLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent loginIntent = null;
        if (v.getId() == cirLoginButton) {
            final String employeeNameText = emlText.getText().toString().trim();
            final String employeePasswordText = passText.getText().toString().trim();
            if (employeeNameText.equals("Ramesh") && employeePasswordText.equals("ramesh123")) {
                loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                loginIntent.putExtra("Login Employee", emlText.getText().toString().trim());
            } else if (employeeNameText.equals("Suresh") && employeePasswordText.equals("suresh123")) {
                loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                loginIntent.putExtra("Login Manager", emlText.getText().toString().trim());
            }
        }
        startActivity(loginIntent);
        finish();
    }
}
