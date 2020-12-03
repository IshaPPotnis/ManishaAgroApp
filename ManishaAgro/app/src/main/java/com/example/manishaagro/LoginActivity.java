package com.example.manishaagro;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.manishaagro.employee.EmployeeActivity;
import com.example.manishaagro.manager.ManagerActivity;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.utils.EmployeeType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.DBHelper.COLUMN_DESIGNATION;
import static com.example.manishaagro.DBHelper.COLUMN_EMPI_ID;
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
    String SM = "SALES MANAGER";
    String TM = "TERRITORY MANAGER";
    String ASM = "ASM";
    String SR_ASM = "SR ASM";
    String SO = "SALES OFFICER";
    String GM = "GENERAL MANAGER";
    String MNGD = "MANAGING DIRECTOR";
    String OEXT = "OFFICE EXECUTIVE";

    String HRM = "HR MANAGER";
    DBHelper dbHelpers;
    AlertDialog alertDialog1;
    CharSequence[] values = {"Manager", "Employee"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        dbHelpers=new DBHelper(this);
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
                getEmpIdAndDesignationLocally();

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
       finish();
       moveTaskToBack(true);
    }

    private void getEmpIdAndDesignationLocally() {
        final String employeeNameText = userNameText.getText().toString();
        final String employeePasswordText = passwordText.getText().toString();

        Log.v("Nuser", "userame" + employeeNameText);
        Log.v("Npass", "pass" + employeePasswordText);

        Cursor cursor1 = dbHelpers.getIDDesig(employeeNameText, employeePasswordText);
        cursor1.moveToFirst();
        Log.d("Count", String.valueOf(cursor1.getCount()));
        if (cursor1.getCount() > 0)
        {
         final String empId = cursor1.getString(cursor1.getColumnIndex(COLUMN_EMPI_ID));
            final String designation = cursor1.getString(cursor1.getColumnIndex(COLUMN_DESIGNATION));

            Intent loginIntent;
            if (EmployeeType.MANAGER.name().equalsIgnoreCase(designation) || SM.equals(designation) || TM.equals(designation) || ASM.equals(designation)
                    || SR_ASM.equals(designation) || SO.equals(designation) || GM.equals(designation) ||
                    MNGD.equals(designation) || OEXT.equals(designation) || HRM.equals(designation)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                builder.setTitle("Do you want to login as?");
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:

                                    Intent loginIntent;
                                    loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                                    loginIntent.putExtra(LOGIN_MANAGER, employeeNameText);
                                    loginIntent.putExtra(EMPI_USER, empId);
                                    startActivity(loginIntent);
                                    finish();

                                break;
                            case 1:

                                  //  Intent loginIntent;
                                    loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                                    loginIntent.putExtra(LOGIN_EMPLOYEE, employeeNameText);
                                    loginIntent.putExtra(EMPI_USER, empId);
                                    startActivity(loginIntent);
                                    finish();

                                break;
                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();
            } else {
                loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                loginIntent.putExtra(LOGIN_EMPLOYEE, employeeNameText);
                loginIntent.putExtra(EMPI_USER, empId);
                startActivity(loginIntent);
                finish();

            }




        }

    }

    private void getEmpIDAndDesignation()
    {
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
                    if (EmployeeType.MANAGER.name().equalsIgnoreCase(designation) || SM.equals(designation) || TM.equals(designation) || ASM.equals(designation)
                            || SR_ASM.equals(designation) || SO.equals(designation) || GM.equals(designation) ||
                            MNGD.equals(designation) || OEXT.equals(designation) || HRM.equals(designation)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                        builder.setTitle("Do you want to login as?");
                        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int item) {
                                switch (item) {
                                    case 0:
                                        if (connectionDetector.isConnected(LoginActivity.this)) {
                                            Intent loginIntent;
                                            loginIntent = new Intent(LoginActivity.this, ManagerActivity.class);
                                            loginIntent.putExtra(LOGIN_MANAGER, employeeNameText);
                                            loginIntent.putExtra(EMPI_USER, empId);
                                            startActivity(loginIntent);
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 1:
                                        if (connectionDetector.isConnected(LoginActivity.this)) {
                                            Intent loginIntent;
                                            loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                                            loginIntent.putExtra(LOGIN_EMPLOYEE, employeeNameText);
                                            loginIntent.putExtra(EMPI_USER, empId);
                                            startActivity(loginIntent);
                                            finish();

                                        } 
                                        else 
                                        {
                                            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                }
                                alertDialog1.dismiss();
                            }
                        });
                        alertDialog1 = builder.create();
                        alertDialog1.show();
                    } else {
                        loginIntent = new Intent(LoginActivity.this, EmployeeActivity.class);
                        loginIntent.putExtra(LOGIN_EMPLOYEE, employeeNameText);
                        loginIntent.putExtra(EMPI_USER, empId);
                        startActivity(loginIntent);
                        finish();
                    }
                } else if (value.equals("0")) {
                    System.out.println("Cannot login");
                    Toast.makeText(LoginActivity.this, INACTIVATED_USER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                if (connectionDetector.isConnected(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, "Server Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}