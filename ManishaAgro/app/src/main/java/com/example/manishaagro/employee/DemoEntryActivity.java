package com.example.manishaagro.employee;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.utils.CROP_HEALTH;
import com.example.manishaagro.utils.DEMO_TYPE;
import com.example.manishaagro.utils.USAGE_TYPE;
import com.example.manishaagro.utils.Utilities;
import com.example.manishaagro.utils.Validator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class DemoEntryActivity extends AppCompatActivity implements View.OnClickListener {
    private Calendar myCalendar = Calendar.getInstance();
    ConnectionDetector connectionDetector;
    ProgressBar progressBar;
    RelativeLayout badRel;
    String employeeID = "";
    String penName="";
    String penDate="";
    String penCon="";

    String visitIds = "";
    public String farmerFollowUpDate = "";
    public int followYN = 2;
    public int demosYN = 2;
    ApiInterface apiInterface;
    Toolbar visitDemoToolbar;
    CardView FollowUpIsRequired, visitDemoReq;
    EditText editTextDemoName;
    EditText editTextCrops;
    EditText editTextWaterQuantity;
    EditText editTextWaterAddition;
    EditText editTextAdditions;
    EditText editTextFollowUpDate;
    EditText cropBadEdit;
    EditText cropGrowthEdit;
    RadioGroup radioGroupFollowUp, demoGroup;
    RadioButton radioYes, radioNo, demoYes, demoNo;
    AutoCompleteTextView autoCompleteDemoTy;
    AutoCompleteTextView autoCTXUsage;
    AutoCompleteTextView autoCTXCropHealth;
    AutoCompleteTextView autoCompleteFarmerName;
    ImageView AutoCTXImage;
    ImageView autoCTXUsageImg;
    ImageView autoCTXCropHealthImg;
    ImageView autoCTXFarmerImg;
    String publicFarmernm="";
    Button saveDemo;
    public ArrayList<TripModel> farmerNameData = new ArrayList<>();
    public ArrayList<String> farmerNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_entry);
        connectionDetector = new ConnectionDetector();
        visitDemoToolbar = findViewById(R.id.toolbarDemo);
        progressBar = findViewById(R.id.progress);
        setSupportActionBar(visitDemoToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        badRel=findViewById(R.id.cropBadReasonRel);
        cropBadEdit=findViewById(R.id.editTextCropBadReason);
        cropGrowthEdit=findViewById(R.id.editTextCropGrowth);
        visitDemoReq = findViewById(R.id.visitCardDemo);
        autoCompleteFarmerName = findViewById(R.id.autoCompleteFarmerName);
        autoCTXFarmerImg = findViewById(R.id.autoTextFarmerNameImg);
        FollowUpIsRequired = findViewById(R.id.visitCardFollowupRequired);
        editTextDemoName = findViewById(R.id.editTextDemoName);
        editTextCrops = findViewById(R.id.editTextCrop);
        editTextWaterQuantity = findViewById(R.id.editTextWaterQty);
        editTextWaterAddition = findViewById(R.id.editTextWaterAddition);
        editTextAdditions = findViewById(R.id.editTextAdditions);
        editTextFollowUpDate = findViewById(R.id.editTextFollowupDate);
        radioGroupFollowUp = findViewById(R.id.RadioGropFollowup);
        radioYes = findViewById(R.id.radioButtonYes);
        radioNo = findViewById(R.id.radioButtonNo);
        demoGroup = findViewById(R.id.RadioGroupDemoReq);
        demoYes = findViewById(R.id.radioButtonDemoReqYes);
        demoNo = findViewById(R.id.radioButtonDemoReqNo);
        autoCompleteDemoTy = findViewById(R.id.autoCompleteDemoType);
        AutoCTXImage = findViewById(R.id.autoTextImg);
        autoCTXCropHealth = findViewById(R.id.autoCompleteCropHealth);
        autoCTXUsage = findViewById(R.id.autoCompleteUsageType);
        autoCTXCropHealthImg = findViewById(R.id.autoTextCropHealthImg);
        autoCTXUsageImg = findViewById(R.id.autoTextUsageTypeImg);
        saveDemo = findViewById(R.id.SubmitDemo);
        Intent intent = getIntent();

        String keyForCompare = intent.getStringExtra("emp_visit_pen");
     //   String keyForCompare = intent.getStringExtra("emp_visit_pen");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("emp_visit_pen_check")) {
            penName = intent.getStringExtra("pendingvisit_customer_name");
            penDate = intent.getStringExtra("pendingvisit_date");
            penCon = intent.getStringExtra("pendingvisit_contact");
            visitIds=intent.getStringExtra("pendingvisit_customer_visitid");
            employeeID = intent.getStringExtra("penvisit_empid");
            autoCompleteFarmerName.setText(penName);
        }
        else
        {
            employeeID = intent.getStringExtra("visitedEmployeeDemoEntry");
            publicFarmernm=intent.getStringExtra("visitedEmployeeDemoEntryFarmernm");
        }




        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTextFollowUpDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextFollowUpDate.setFocusableInTouchMode(false);
        editTextFollowUpDate.setFocusable(false);
        editTextFollowUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DemoEntryActivity.this, R.style.DialogTheme, datePickerListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        List<CROP_HEALTH> enumListCrop = Arrays.asList(CROP_HEALTH.values());
        final ArrayAdapter<CROP_HEALTH> adapterCropHealth = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enumListCrop);
        autoCTXCropHealth.setAdapter(adapterCropHealth);
        autoCTXCropHealth.setEnabled(false);

        autoCTXCropHealth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String healthauto=autoCTXCropHealth.getText().toString().trim();

                if(healthauto.equals("BAD"))
                {
                    badRel.setVisibility(View.VISIBLE);
                }
                else
                {
                    badRel.setVisibility(View.GONE);
                }

                Log.v("Crop Health ", "health value" + healthauto);




            }
        });

        autoCTXCropHealth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCTXCropHealth.setFocusable(false);
                autoCTXCropHealth.setEnabled(false);


                return false;
            }
        });



        autoCTXCropHealthImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCTXCropHealth.setEnabled(true);
                autoCTXCropHealth.showDropDown();

            }
        });





        List<USAGE_TYPE> enumListUsage = Arrays.asList(USAGE_TYPE.values());
        final ArrayAdapter<USAGE_TYPE> adapterUsage = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enumListUsage);
        autoCTXUsage.setAdapter(adapterUsage);
        autoCTXUsage.setEnabled(false);

        autoCTXUsage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCTXUsage.setFocusable(false);
                autoCTXUsage.setEnabled(false);
                return false;
            }
        });

        autoCTXUsageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCTXUsage.setEnabled(true);
                autoCTXUsage.showDropDown();

            }
        });
        List<DEMO_TYPE> enumList = Arrays.asList(DEMO_TYPE.values());
        final ArrayAdapter<DEMO_TYPE> adapterDemoType = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enumList);
        autoCompleteDemoTy.setAdapter(adapterDemoType);
        autoCompleteDemoTy.setEnabled(false);

        autoCompleteDemoTy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCompleteDemoTy.setFocusable(false);
                autoCompleteDemoTy.setEnabled(false);
                return false;
            }
        });

        AutoCTXImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteDemoTy.setEnabled(true);
                autoCompleteDemoTy.showDropDown();
            }
        });

autoCompleteFarmerName.setText(publicFarmernm);
        autoCompleteFarmerName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCompleteFarmerName.setEnabled(true);
                autoCompleteFarmerName.setFocusable(true);

                return false;
            }
        });

        autoCTXFarmerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteFarmerName.setEnabled(true);
                autoCompleteFarmerName.showDropDown();
            }
        });
        autoCompleteFarmerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
            }
        });

        demoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup demoRadiogroup, int democheckedId) {
                if (democheckedId == R.id.radioButtonDemoReqYes) {
                    demosYN = 1;
                    visitDemoReq.setVisibility(View.VISIBLE);
                } else if (democheckedId == R.id.radioButtonDemoReqNo) {
                    demosYN = 0;
                    visitDemoReq.setVisibility(View.GONE);
                }

            }
        });

        radioGroupFollowUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonYes) {
                    followYN = 1;
                    FollowUpIsRequired.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioButtonNo) {
                    followYN = 0;
                    FollowUpIsRequired.setVisibility(View.GONE);
                }
            }
        });
        if (connectionDetector.isConnected(DemoEntryActivity.this)) {
            getFarmerNameInDemo();
        } else {
            Toast.makeText(DemoEntryActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        saveDemo.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getFarmerNameInDemo() {
        Log.v("fnamelist1", "fnmaelis" + employeeID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<TripModel>> callFarmerNameList = apiInterface.getFarmerNameList("getFarmerN@meLists", employeeID);
        callFarmerNameList.enqueue(new Callback<ArrayList<TripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TripModel>> call, Response<ArrayList<TripModel>> response) {
                assert response.body() != null;
                farmerNameData.clear();
                farmerNameData.addAll(response.body());
                Log.v("Runcheck1", "user1" + farmerNameData);
                farmerNameList = new ArrayList<>();
                for (int i = 0; i < farmerNameData.size(); i++) {
                    String lat = farmerNameData.get(i).getVisitedCustomerName();
                    farmerNameList.add(lat);
                }
                final ArrayAdapter<String> adpAllFarmerName = new ArrayAdapter<>(DemoEntryActivity.this, android.R.layout.simple_list_item_1, farmerNameList);
                autoCompleteFarmerName.setAdapter(adpAllFarmerName);
                autoCompleteFarmerName.setEnabled(true);
                Log.v("Runcheck2", "user1" + farmerNameList);
            }

            @Override
            public void onFailure(Call<ArrayList<TripModel>> call, Throwable t) {
                if (connectionDetector.isConnected(DemoEntryActivity.this)) {
                    System.out.println("DemoEntryActivity : Is Connected");
                    // Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DemoEntryActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SubmitDemoEntry() {
        final String farmercropGrowth=cropGrowthEdit.getText().toString().trim();
        final String farmerCropBadReson=cropBadEdit.getText().toString().trim();
        final String farmerNameText = autoCompleteFarmerName.getText().toString().trim();
        final String farmerDemoName = editTextDemoName.getText().toString().trim();
        final String farmerDemoType = autoCompleteDemoTy.getText().toString().trim();
        final String farmerCrops = editTextCrops.getText().toString().trim();
        final String farmerCropHealth = autoCTXCropHealth.getText().toString().trim();
        final String farmerUsageType = autoCTXUsage.getText().toString().trim();
        final String farmerWaterQty = editTextWaterQuantity.getText().toString().trim();
        final String farmerWaterAdditions = editTextWaterAddition.getText().toString().trim();
        final String farmerAdditions = editTextAdditions.getText().toString().trim();
        final int farmerFallowUp = followYN;
        final int demoVisit = demosYN;

        if ((demoYes.isChecked() || demoNo.isChecked()) && (radioYes.isChecked() || radioNo.isChecked())) {
            if (radioYes.isChecked()) {
                farmerFollowUpDate = editTextFollowUpDate.getText().toString().trim();
            } else if (radioNo.isChecked()) {
                farmerFollowUpDate = "";
            }

            if (demoYes.isChecked()) {

                if (farmerCropHealth.equals("BAD"))
                {
                    if(farmerCropBadReson.equals(""))
                    {
                        Toast.makeText(DemoEntryActivity.this,"Enter Crop Bad Reason",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        progressBar.setVisibility(View.VISIBLE);
                        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                        Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry("Visited@CustomerDemoEntries",
                                employeeID, farmerNameText, farmerDemoType, farmerCrops, farmerCropHealth, farmerDemoName,
                                farmerUsageType,farmerCropBadReson,farmerWaterQty, farmerWaterAdditions, farmerAdditions,farmercropGrowth,farmerFallowUp, farmerFollowUpDate, demoVisit);
                        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                            @Override
                            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                                assert response.body() != null;
                                String value = response.body().getValue();
                                String message = response.body().getMassage();
                                visitIds = String.valueOf(response.body().getVisitid());
                                if (value.equals("1")) {
                                    if (!Validator.isValidName(farmerNameText)) {
                                        Utilities.showAlertDialog(DemoEntryActivity.this,
                                                "Invalid Farmer Name (Either is Empty or includes digits)");
                                    } else if (!Validator.isValidName(farmerCrops)) {
                                        Utilities.showAlertDialog(DemoEntryActivity.this,
                                                "Invalid crop Name");
                                    } else if (!Validator.isValidName(farmerAdditions)) {
                                        Utilities.showAlertDialog(DemoEntryActivity.this,
                                                "Invalid additions");
                                    }else {
                                        progressBar.setVisibility(View.GONE);
                                        autoCompleteFarmerName.setText("");
                                        editTextDemoName.setText("");
                                        autoCompleteDemoTy.setText("");
                                        editTextCrops.setText("");
                                        autoCTXCropHealth.setText("");
                                        autoCTXUsage.setText("");
                                        editTextWaterQuantity.setText("");
                                        editTextWaterAddition.setText("");
                                        editTextAdditions.setText("");
                                        editTextFollowUpDate.setText("");
                                        Intent demoIntent = new Intent(DemoEntryActivity.this, ProductActivity.class);
                                        demoIntent.putExtra("visitedEmployeeProductAct", employeeID);
                                        demoIntent.putExtra("visitedEmployeeProductActVisitID", visitIds);
                                        demoIntent.putExtra("farmerName", farmerNameText);
                                        demoIntent.putExtra("CustVisitEmpId&Visitid", "CustEmployeeId&Visitid");
                                        startActivity(demoIntent);
                                        finish();
                                    }
                                } else if (value.equals("0")) {
                                    Toast.makeText(DemoEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TripModel> call, Throwable t) {
                                if (connectionDetector.isConnected(DemoEntryActivity.this)) {
                                    Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    //    int eid= Integer.parseInt(employeeID);
                                    //  Toast.makeText(DemoEntryActivity.this, "No Internet Connection saved offline data", Toast.LENGTH_LONG).show();
                                    String StrVisitData="Visited@CustomerDemoEntries"+","+employeeID+","+farmerNameText+","+farmerDemoType+","+farmerCrops+","+farmerCropHealth+","+farmerDemoName+","+
                                            farmerUsageType+","+farmerCropBadReson+","+farmerWaterQty+","+farmerWaterAdditions+","+farmerAdditions+","+farmercropGrowth+","+farmerFallowUp+","+farmerFollowUpDate+","+demoVisit;
                                    String VisitDataDir="VisitDemoEntryDir.txt";
                                    generateNoteOnSD(DemoEntryActivity.this,VisitDataDir,StrVisitData);
                                    autoCompleteFarmerName.setText("");
                                    editTextDemoName.setText("");
                                    autoCompleteDemoTy.setText("");
                                    editTextCrops.setText("");
                                    autoCTXCropHealth.setText("");
                                    autoCTXUsage.setText("");
                                    editTextWaterQuantity.setText("");
                                    editTextWaterAddition.setText("");
                                    editTextAdditions.setText("");
                                }
                            }
                        });
                    }

                }
                else
                {


                    progressBar.setVisibility(View.VISIBLE);
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry("Visited@CustomerDemoEntries",
                            employeeID, farmerNameText, farmerDemoType, farmerCrops, farmerCropHealth, farmerDemoName,
                            farmerUsageType,farmerCropBadReson,farmerWaterQty, farmerWaterAdditions, farmerAdditions,farmercropGrowth,farmerFallowUp, farmerFollowUpDate, demoVisit);
                    empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                        @Override
                        public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                            assert response.body() != null;
                            String value = response.body().getValue();
                            String message = response.body().getMassage();
                            visitIds = String.valueOf(response.body().getVisitid());
                            if (value.equals("1")) {
                                if (!Validator.isValidName(farmerNameText)) {
                                    Utilities.showAlertDialog(DemoEntryActivity.this,
                                            "Invalid Farmer Name (Either is Empty or includes digits)");
                                } else if (!Validator.isValidName(farmerCrops)) {
                                    Utilities.showAlertDialog(DemoEntryActivity.this,
                                            "Invalid crop Name");
                                } else if (!Validator.isValidName(farmerAdditions)) {
                                    Utilities.showAlertDialog(DemoEntryActivity.this,
                                            "Invalid additions");
                                }else {
                                    progressBar.setVisibility(View.GONE);
                                    autoCompleteFarmerName.setText("");
                                    editTextDemoName.setText("");
                                    autoCompleteDemoTy.setText("");
                                    editTextCrops.setText("");
                                    autoCTXCropHealth.setText("");
                                    autoCTXUsage.setText("");
                                    editTextWaterQuantity.setText("");
                                    editTextWaterAddition.setText("");
                                    editTextAdditions.setText("");
                                    editTextFollowUpDate.setText("");
                                    Intent demoIntent = new Intent(DemoEntryActivity.this, ProductActivity.class);
                                    demoIntent.putExtra("visitedEmployeeProductAct", employeeID);
                                    demoIntent.putExtra("visitedEmployeeProductActVisitID", visitIds);
                                    demoIntent.putExtra("farmerName", farmerNameText);
                                    demoIntent.putExtra("CustVisitEmpId&Visitid", "CustEmployeeId&Visitid");
                                    startActivity(demoIntent);
                                    finish();
                                }
                            } else if (value.equals("0")) {
                                Toast.makeText(DemoEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TripModel> call, Throwable t) {
                            if (connectionDetector.isConnected(DemoEntryActivity.this)) {
                                Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                //    int eid= Integer.parseInt(employeeID);
                                //  Toast.makeText(DemoEntryActivity.this, "No Internet Connection saved offline data", Toast.LENGTH_LONG).show();
                                String StrVisitData="Visited@CustomerDemoEntries"+","+employeeID+","+farmerNameText+","+farmerDemoType+","+farmerCrops+","+farmerCropHealth+","+farmerDemoName+","+
                                        farmerUsageType+","+farmerCropBadReson+","+farmerWaterQty+","+farmerWaterAdditions+","+farmerAdditions+","+farmercropGrowth+","+farmerFallowUp+","+farmerFollowUpDate+","+demoVisit;
                                String VisitDataDir="VisitDemoEntryDir.txt";
                                generateNoteOnSD(DemoEntryActivity.this,VisitDataDir,StrVisitData);
                                autoCompleteFarmerName.setText("");
                                editTextDemoName.setText("");
                                autoCompleteDemoTy.setText("");
                                editTextCrops.setText("");
                                autoCTXCropHealth.setText("");
                                autoCTXUsage.setText("");
                                editTextWaterQuantity.setText("");
                                editTextWaterAddition.setText("");
                                editTextAdditions.setText("");
                            }
                        }
                    });
                }



            } else if (demoNo.isChecked()) {
                progressBar.setVisibility(View.VISIBLE);
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry("Visited@CustomerDemoEntries",
                        employeeID, farmerNameText, farmerDemoType, farmerCrops, farmerCropHealth, farmerDemoName, farmerUsageType,farmerCropBadReson,farmerWaterQty, farmerWaterAdditions, farmerAdditions, farmercropGrowth,farmerFallowUp, farmerFollowUpDate, demoVisit);
                empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                    @Override
                    public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                        assert response.body() != null;
                        String value = response.body().getValue();
                        String message = response.body().getMassage();
                        visitIds = String.valueOf(response.body().getVisitid());
                        if (value.equals("1")) {
                            progressBar.setVisibility(View.GONE);
                            autoCompleteFarmerName.setText("");
                            editTextDemoName.setText("");
                            autoCompleteDemoTy.setText("");
                            editTextCrops.setText("");
                            autoCTXCropHealth.setText("");
                            autoCTXUsage.setText("");
                            editTextWaterQuantity.setText("");
                            editTextAdditions.setText("");
                            Intent demoIntent = new Intent(DemoEntryActivity.this, ProductActivity.class);
                            demoIntent.putExtra("visitedEmployeeProductAct", employeeID);
                            demoIntent.putExtra("visitedEmployeeProductActVisitID", visitIds);
                            demoIntent.putExtra("farmerName", farmerNameText);
                            demoIntent.putExtra("CustVisitEmpId&Visitid", "CustEmployeeId&Visitid");
                            startActivity(demoIntent);
                            finish();
                        } else if (value.equals("0")) {
                            Toast.makeText(DemoEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TripModel> call, Throwable t) {
                        if (connectionDetector.isConnected(DemoEntryActivity.this)) {
                            Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                         //   int eid= Integer.parseInt(employeeID);
                          //  Toast.makeText(DemoEntryActivity.this, "No Internet Connection offline saved data", Toast.LENGTH_LONG).show();
                            String StrVisitData="Visited@CustomerDemoEntries"+","+employeeID+","+farmerNameText+","+farmerDemoType+","+farmerCrops+","+farmerCropHealth+","+farmerDemoName+","+farmerUsageType+","+farmerCropBadReson+","+farmerWaterQty+","+
                                    farmerWaterAdditions+","+farmerAdditions+","+farmercropGrowth+","+farmerFallowUp+","+farmerFollowUpDate+","+demoVisit;
                            String VisitDataDir="VisitDemoEntryDir.txt";
                            generateNoteOnSD(DemoEntryActivity.this,VisitDataDir,StrVisitData);
                            autoCompleteFarmerName.setText("");
                            editTextDemoName.setText("");
                            autoCompleteDemoTy.setText("");
                            editTextCrops.setText("");
                            autoCTXCropHealth.setText("");
                            autoCTXUsage.setText("");
                            editTextWaterQuantity.setText("");
                            editTextAdditions.setText("");
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SubmitDemo) {
            if (connectionDetector.isConnected(DemoEntryActivity.this)) {
                final String farmerName1 = autoCompleteFarmerName.getText().toString().trim();
                if (!Validator.isValidName(farmerName1)) {
                    Utilities.showAlertDialog(DemoEntryActivity.this, "Invalid Farmer Name");
                } else {
                    SubmitDemoEntry();
                }
            } else {
                Toast.makeText(DemoEntryActivity.this, "No Internet Connection now in offline", Toast.LENGTH_LONG).show();
                SubmitDemoEntry();
            }
        }
    }


    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "ManishaAgroData");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append(sBody);
            writer.append("\n");
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}