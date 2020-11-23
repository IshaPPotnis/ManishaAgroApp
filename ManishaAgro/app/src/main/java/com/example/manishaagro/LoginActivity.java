package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import static com.example.manishaagro.utils.Constants.INACTIVATED_USER;
import static com.example.manishaagro.utils.Constants.INVALID_CREDENTIALS;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.PASSING_DATA;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button ButtonCirLogin;
    EditText userNameText, passwordText;
    ApiInterface apiInterface;
    TextView showPwdImgref;
    ConnectionDetector connectionDetector;
    String SM="SALES MANAGER";
    String TM="TERRITORY MANAGER";
    String ASM="ASM";
    String SR_ASM="SR ASM";
    String SO="SALES OFFICER";
    String GM="GENERAL MANAGER";
    String MNGD="MANAGING DIRECTOR";
    String OEXT="OFFICE EXECUTIVE";
    String MDO="MDO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        connectionDetector = new ConnectionDetector();
        ButtonCirLogin = findViewById(cirLoginButton);
        userNameText = findViewById(R.id.editTextUserName);
        passwordText = findViewById(R.id.editTextPassword);
        showPwdImgref = findViewById(R.id.showPwdImg);
        showPwdImgref.setOnClickListener(this);
        ButtonCirLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == cirLoginButton) {
            if (connectionDetector.isConnected(LoginActivity.this)) {
                getEmpIDAndDesignation();
            } else {
                Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }

        if (v.getId() == R.id.showPwdImg) {
            if (passwordText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                showPwdImgref.setText(R.string.pwd_hide_button);
                passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                showPwdImgref.setText(R.string.pwd_show_button);
                passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                assert response.body() != null;
                String value = response.body().getValue();
                final String designation = response.body().getDesignation();
                final String empId = response.body().getEmpId();
                if (value.equals("1")) {
                    Intent loginIntent;
                    if (EmployeeType.MANAGER.name().equalsIgnoreCase(designation)||SM.equals(designation)||TM.equals(designation)||ASM.equals(designation)
                    ||SR_ASM.equals(designation)||SO.equals(designation)||GM.equals(designation)||MNGD.equals(designation)||OEXT.equals(designation)||MDO.equals(designation)) {
                        loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                        loginIntent.putExtra(LOGIN_MANAGER, employeeNameText);
                    } else {
                        loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                        loginIntent.putExtra(LOGIN_EMPLOYEE, employeeNameText);
                    }
                    loginIntent.putExtra(EMPI_USER, empId);
                    startActivity(loginIntent);
                    finish();
                } else if (value.equals("0")) {
                    System.out.println("Cannot login");
                    Toast.makeText(LoginActivity.this, INACTIVATED_USER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                if (connectionDetector.isConnected(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}