package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.utils.CROP_HEALTH;
import com.example.manishaagro.utils.DEMO_TYPE;
import com.example.manishaagro.utils.EmployeeType;
import com.example.manishaagro.utils.USAGE_TYPE;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static com.example.manishaagro.R.id.VisitStartSubmit;
import static com.example.manishaagro.R.id.cirLoginButton;
import static com.example.manishaagro.R.id.editTextVillage;
import static com.example.manishaagro.R.id.radioButtonYes;
import static com.example.manishaagro.utils.Constants.EMPI_USER;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.PASSING_DATA;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class CustomeVisitStartActivity extends AppCompatActivity implements View.OnClickListener {

    ApiInterface apiInterface;
    Toolbar visitStartToolbar;
    EditText editTextFarmerName,editTextFarmerAddress,editTextFarmerContact,edtxvillage,edtxTaluka,edtxDistrict;
    Button visitEntrySubmit,visitEntrySubmit1,SelfieButton,DemoButton;
   public String employeeID="";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_visit_start);
     visitStartToolbar=findViewById(R.id.toolbarvisit);
       setSupportActionBar(visitStartToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        editTextFarmerAddress=findViewById(R.id.editTextFarmerAddress);
        editTextFarmerName=findViewById(R.id.editTextFarmerName);
        editTextFarmerContact=findViewById(R.id.editTextContact);
        edtxvillage=findViewById(R.id.editTextVillage);
        edtxTaluka=findViewById(R.id.editTextTaluka);
        edtxDistrict=findViewById(R.id.editTextDistrict);









        visitEntrySubmit=findViewById(R.id.VisitStartSubmit);
        visitEntrySubmit1=findViewById(R.id.VisitStartSubmit1);
        SelfieButton=findViewById(R.id.goToSelfieActivity);
        DemoButton=findViewById(R.id.goToDemoActivity);
        Intent intent = getIntent();

        String keyForCompare = intent.getStringExtra("CheckDemoActivity");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("Customer@DemoEntry"))
        {
            employeeID = intent.getStringExtra("visitedEmployeeBackDemoEntry");
        }

        String keyCompare1=intent.getStringExtra("visitedEmpID");
        if(keyCompare1 != null && keyCompare1.equals("Emp@ID"))
        {
            employeeID = intent.getStringExtra("visitedEmployee");
        }
       String keyCompare2 =intent.getStringExtra("CheckDemoImageActivity");
        if (keyCompare2!=null && keyCompare2.equals("Demo@Customerimage"))
        {
            employeeID=intent.getStringExtra("visitedEmployeeBackFromDemoImage");
        }

       String keyCompare3 =intent.getStringExtra("CheckSelfieImageActivity");
        if(keyCompare3 != null && keyCompare3.equals("Selfie@Customerimage"))
        {
            employeeID=intent.getStringExtra("visitedEmployeeBackFromSelfie");
        }










        visitEntrySubmit.setOnClickListener(this);
        DemoButton.setOnClickListener(this);
        visitEntrySubmit1.setOnClickListener(this);
        SelfieButton.setOnClickListener(this);




    }




    @Override
    public void onClick(View v) {
        if (v.getId() == VisitStartSubmit) {

            visitEntry();

        }
        if (v.getId() == R.id.VisitStartSubmit1) {

            Intent  visitIntent = new Intent(CustomeVisitStartActivity.this, DemoImageActivity.class);
            visitIntent.putExtra("visitedEmployeeDemo", employeeID);
            startActivity(visitIntent);
            finish();

        }

        if(v.getId() == R.id.goToSelfieActivity)
        {
            Intent  visitIntent = new Intent(CustomeVisitStartActivity.this, SelfieImageActivity.class);
            visitIntent.putExtra("visitedEmployeeSelfie", employeeID);
            startActivity(visitIntent);
            finish();
        }

        if (v.getId() == R.id.goToDemoActivity)
        {
            Intent  visitIntent = new Intent(CustomeVisitStartActivity.this, DemoEntryActivity.class);
            visitIntent.putExtra("visitedEmployeeDemoEntry", employeeID);
            startActivity(visitIntent);
            finish();
        }



    }



    private void visitEntry()
    {
        final String farmerNameText = editTextFarmerName.getText().toString().trim();
        final String farmerAddressText = editTextFarmerAddress.getText().toString().trim();
        final String farmerContacts = editTextFarmerContact.getText().toString().trim();
        final String farmerVillage = edtxvillage.getText().toString().trim();
        final String farmerTaluka = edtxTaluka.getText().toString().trim();
        final String farmerDistrict = edtxDistrict.getText().toString().trim();
        Log.v("Check id emp", "emp id" + employeeID);


        if (employeeID.equals("")||farmerNameText.equals("")||farmerAddressText.equals("")||
                farmerContacts.equals("")||farmerVillage.equals("")||farmerTaluka.equals("")||farmerDistrict.equals(""))
        {
            Toast.makeText(CustomeVisitStartActivity.this,"Fields Are Empty",Toast.LENGTH_SHORT);
        }
        else
        {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(VISITED_CUSTOMER_ENTRY, employeeID,farmerNameText,farmerAddressText,farmerVillage,farmerTaluka,farmerDistrict,farmerContacts);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();


                    if(value.equals("1"))
                    {
                        editTextFarmerName.setText("");
                        editTextFarmerAddress.setText("");
                        Toast.makeText(CustomeVisitStartActivity.this,message,Toast.LENGTH_SHORT);

                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(CustomeVisitStartActivity.this,message,Toast.LENGTH_SHORT);
                    }


                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {


                    if (t instanceof SocketTimeoutException)
                    {
                        // "Connection Timeout";
                        Toast.makeText(CustomeVisitStartActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else if (t instanceof IOException)
                    {
                        // "Timeout";
                        Toast.makeText(CustomeVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Call was cancelled by user
                        if(call.isCanceled())
                        {
                            System.out.println("Call was cancelled forcefully");
                            Toast.makeText(CustomeVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //Generic error handling
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                            Toast.makeText(CustomeVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }



                }
            });
        }



    }









}
