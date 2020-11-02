package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manishaagro.employee.EmployeeActivity;
import com.example.manishaagro.manager.ManagerActivity;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.utils.EmployeeType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.R.id.cirLoginButton;
import static com.example.manishaagro.R.layout.activity_login;
import static com.example.manishaagro.utils.Constants.EMPI_USER;
import static com.example.manishaagro.utils.Constants.INVALID_CREDENTIALS;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.PASSING_DATA;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button ButtonCirLogin;
    EditText userNameText, passwordText;
    ApiInterface apiInterface;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);

        connectionDetector=new ConnectionDetector();
        ButtonCirLogin = findViewById(cirLoginButton);
        userNameText = findViewById(R.id.editTextUserName);
        passwordText = findViewById(R.id.editTextPassword);
        ButtonCirLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == cirLoginButton) {
            if(connectionDetector.isConnected(LoginActivity.this))
            {
                getEmpIDAndDesignation();
            }
            else
            {
                Toast.makeText(LoginActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }



        }
    }

    private void getEmpIDAndDesignation() {
        final String employeeNameText = userNameText.getText().toString().trim();
        final String employeePasswordText = passwordText.getText().toString().trim();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProfileModel> empIdDesignationModelCall = apiInterface.getEmpIdDesignation(PASSING_DATA, employeeNameText, employeePasswordText);
        empIdDesignationModelCall.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                final String designation = response.body().getDesignation();
                final String empId = response.body().getEmpId();

                if (value.equals("1")) {
                    Intent loginIntent;
                    if (EmployeeType.MANAGER.name().equalsIgnoreCase(designation)) {
                        loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                        loginIntent.putExtra(LOGIN_MANAGER, employeeNameText);
                    } else {
                        loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                        loginIntent.putExtra(LOGIN_EMPLOYEE, employeeNameText);

                    }
                    loginIntent.putExtra(EMPI_USER, empId);
                    startActivity(loginIntent);
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else if (value.equals("0")) {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

                if(connectionDetector.isConnected(LoginActivity.this))
                {
                    Toast.makeText(LoginActivity.this, INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }




            }
        });
    }



}


