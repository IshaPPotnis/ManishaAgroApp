package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manishaagro.model.EmpIdDesignationModel;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.utils.EmployeeType;
import com.example.manishaagro.utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.R.id.cirLoginButton;
import static com.example.manishaagro.R.layout.activity_login;
import static com.example.manishaagro.utils.Constants.CHECK_USER;
import static com.example.manishaagro.utils.Constants.INVALID_CREDENTIALS;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.PASSING_DATA;
import static com.example.manishaagro.utils.Constants.VALID_CREDENTIALS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button ButtonCirLogin;
    EditText userNameText, passwordText;
    ApiInterface apiInterface;
    ProfileModel profileModel;

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
    public void onClick(final View v) {

        if (v.getId() == cirLoginButton) {
            getEmpIDAndDesignation(PASSING_DATA);




         /*   Toast.makeText(LoginActivity.this, VALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
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
            startActivity(loginIntent);*/
        }

    }

    private void  getEmpIDAndDesignation(final String key)
    {
        final String employeeNameText = userNameText.getText().toString().trim();
        final String employeePasswordText = passwordText.getText().toString().trim();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProfileModel> empIdDesignationModelCall=apiInterface.getEmpIdDesignation(key,employeeNameText,employeePasswordText);
        empIdDesignationModelCall.enqueue(new Callback<ProfileModel>()
        {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                String value = response.body().getValue();

                String message = response.body().getMassage();
                final String Resdesignation = response.body().getDesignation();
                final String ResempId = response.body().getEmpId();

                if (value.equals("1"))
                {
                    //String employeeType = Utilities.getDesignation(employeeNameText);
                    String employeeType=CHECK_USER;
                    Intent loginIntent=null;
                    if (employeeType.equalsIgnoreCase(Resdesignation))
                    {
                        loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                        loginIntent.putExtra(LOGIN_MANAGER, employeeNameText);
                        loginIntent.putExtra("empi_user",ResempId);
                        startActivity(loginIntent);
                    }
                    else
                    {
                        loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                        loginIntent.putExtra(LOGIN_EMPLOYEE,employeeNameText);
                        loginIntent.putExtra("empi_user",ResempId);
                        startActivity(loginIntent);

                    }
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();

                }
                else if(value.equals("0"))
                {
                   /* if (!validateLoginCredentials(employeeNameText, employeePasswordText)) {
                        Toast.makeText(LoginActivity.this, INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
                        return;
                    }*/

                   Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();


                }


            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
               // Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

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

