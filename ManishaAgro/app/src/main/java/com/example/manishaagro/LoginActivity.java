package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manishaagro.utils.EmployeeType;
import com.example.manishaagro.utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.manishaagro.R.id.cirLoginButton;
import static com.example.manishaagro.R.layout.activity_login;
import static com.example.manishaagro.utils.Constants.INVALID_CREDENTIALS;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.VALID_CREDENTIALS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button ButtonCirLogin;
    EditText userNameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        ButtonCirLogin = findViewById(cirLoginButton);
        userNameText = findViewById(R.id.editTextUserName);
        passwordText = findViewById(R.id.editTextPassword);
        ButtonCirLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent loginIntent = null;
        if (v.getId() == cirLoginButton) {
            final String employeeNameText = userNameText.getText().toString().trim();
            final String employeePasswordText = passwordText.getText().toString().trim();
            if (!validateLoginCredentials(employeeNameText, employeePasswordText)) {
                Toast.makeText(LoginActivity.this, INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(LoginActivity.this, VALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
            String employeeType = Utilities.getDesignation(employeeNameText);
            if (employeeType != null) {
                System.out.println("Designation of user is " + employeeType);
                if (employeeType.equals(EmployeeType.EMPLOYEE.name())) {
                    loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                    loginIntent.putExtra(LOGIN_EMPLOYEE, employeeNameText);

                } else if (employeeType.equals(EmployeeType.MANAGER.name())) {
                    loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                    loginIntent.putExtra(LOGIN_MANAGER, employeeNameText);

                }
            }
            startActivity(loginIntent);
        }
        userNameText.setText("");
        passwordText.setText("");
    }

    private boolean validateLoginCredentials(String employeeNameText, String employeePasswordText) {
        return (employeeNameText.equalsIgnoreCase("Ramesh") && employeePasswordText.equals("ramesh123")) ||
                (employeeNameText.equalsIgnoreCase("Suresh") && employeePasswordText.equals("suresh123"))||
                (employeeNameText.equalsIgnoreCase("Roshan") && employeePasswordText.equals("Roshan123"));
    }
/* String link = "http://activexsolutions.com/php/GetEmpIdAndDesignation.php?username=" + employeeNameText +
                "&password=" + employeePasswordText;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream stream = conn.getInputStream();
            InputStreamReader isReader = new InputStreamReader(stream);

            //put output stream into a string
            BufferedReader br = new BufferedReader(isReader);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                result.append(line);
            }
            br.close();
        } catch (IOException ioexception) {
            System.out.println("Exception occurred : " + ioexception);
        }
        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();*/
}

