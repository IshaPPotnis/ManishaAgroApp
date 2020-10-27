package com.example.manishaagro;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.manishaagro.model.ProductModel;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.utils.CROP_HEALTH;
import com.example.manishaagro.utils.DEMO_TYPE;
import com.example.manishaagro.utils.USAGE_TYPE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoEntryActivity extends AppCompatActivity implements View.OnClickListener {
    private Calendar myCalendar = Calendar.getInstance();
    String employeeID = "";
    public String farmerFollowUpDate = "";
    public int followYN = 0,demosYN=0;
    ApiInterface apiInterface;
    Toolbar visitDemoToolbar;
    CardView FollowUpIsRequired,visitDemoReq;
    EditText editTextFarmerName;
    EditText editTextDemoName;
    EditText editTextCrops;
    EditText editTextProductName;
    EditText editTextProductQuantity;
    EditText editTextWaterQuantity;
    EditText editTextAdditions;
    EditText editTextFollowUpDate;
    RadioGroup radioGroupFollowUp,demoGroup;
    RadioButton radioYes, radioNo,demoYes,demoNo;
    AutoCompleteTextView autoCompleteDemoTy;
    AutoCompleteTextView autoCTXUsage;
    AutoCompleteTextView autoCTXCropHealth;
    AutoCompleteTextView autoCompleteProduct;
    AutoCompleteTextView autoCTXPacking;
    AutoCompleteTextView autoCompleteFarmerName;

    ImageView AutoCTXImage;
    ImageView autoCTXUsageImg;
    ImageView autoCTXCropHealthImg;
    ImageView autoCTXProductImg;
    ImageView autoCTXPackingImg;
    ImageView autoCTXFarmerImg;
    Button saveDemo;

    public ArrayList<ProductModel> ProductData = new ArrayList<ProductModel>();
    public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<ProductModel> packingData = new ArrayList<ProductModel>();
    public ArrayList<String> packingList = new ArrayList<String>();
    public ArrayList<TripModel> farmerNameData = new ArrayList<TripModel>();
    public ArrayList<String> farmerNameList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_entry);
        visitDemoToolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(visitDemoToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        visitDemoReq=findViewById(R.id.visitCardDemo);
        autoCompleteProduct = findViewById(R.id.autoCompleteProductName);
        autoCTXPacking = findViewById(R.id.autoCompletePacking);
        autoCTXProductImg = findViewById(R.id.autoTextProdctImg);
        autoCTXPackingImg = findViewById(R.id.autoTextPackingImg);
        autoCompleteFarmerName = findViewById(R.id.autoCompleteFarmerName);
        autoCTXFarmerImg = findViewById(R.id.autoTextFarmerNameImg);
        FollowUpIsRequired = findViewById(R.id.visitCardFollowupRequired);
        editTextDemoName = findViewById(R.id.editTextDemoName);
        editTextCrops = findViewById(R.id.editTextCrop);
        editTextProductQuantity = findViewById(R.id.editTextProductQty);
        editTextWaterQuantity = findViewById(R.id.editTextWaterQty);
        editTextAdditions = findViewById(R.id.editTextAdditions);
        editTextFollowUpDate = findViewById(R.id.editTextFollowupDate);

        radioGroupFollowUp = findViewById(R.id.RadioGropFollowup);
        radioYes = findViewById(R.id.radioButtonYes);
        radioNo = findViewById(R.id.radioButtonNo);

        demoGroup=findViewById(R.id.RadioGroupDemoReq);
        demoYes=findViewById(R.id.radioButtonDemoReqYes);
        demoNo=findViewById(R.id.radioButtonDemoReqNo);

        autoCompleteDemoTy = findViewById(R.id.autoCompleteDemoType);
        AutoCTXImage = findViewById(R.id.autoTextImg);
        autoCTXCropHealth = findViewById(R.id.autoCompleteCropHealth);
        autoCTXUsage = findViewById(R.id.autoCompleteUsageType);
        autoCTXCropHealthImg = findViewById(R.id.autoTextCropHealthImg);
        autoCTXUsageImg = findViewById(R.id.autoTextUsageTypeImg);
        saveDemo = findViewById(R.id.SubmitDemo);
        autoCTXPacking.setEnabled(false);
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeDemoEntry");

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

        List<USAGE_TYPE> enumListUsage = Arrays.asList(USAGE_TYPE.values());
        final ArrayAdapter<USAGE_TYPE> adapterUsage = new ArrayAdapter<USAGE_TYPE>(this, android.R.layout.simple_list_item_1, enumListUsage);
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

        List<CROP_HEALTH> enumListCrop = Arrays.asList(CROP_HEALTH.values());
        final ArrayAdapter<CROP_HEALTH> adapterCropHealth = new ArrayAdapter<CROP_HEALTH>(this, android.R.layout.simple_list_item_1, enumListCrop);
        autoCTXCropHealth.setAdapter(adapterCropHealth);
        autoCTXCropHealth.setEnabled(false);

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

        List<DEMO_TYPE> enumList = Arrays.asList(DEMO_TYPE.values());
        final ArrayAdapter<DEMO_TYPE> adapterDemoType = new ArrayAdapter<DEMO_TYPE>(this, android.R.layout.simple_list_item_1, enumList);
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            autoCompleteProduct.setShowSoftInputOnFocus(false);
        }


        autoCompleteFarmerName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                autoCompleteFarmerName.setFocusable(false);
                autoCompleteFarmerName.setEnabled(false);
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


        autoCompleteProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCompleteProduct.setFocusable(false);
                autoCompleteProduct.setEnabled(false);
                return false;
            }
        });

        autoCTXProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteProduct.setEnabled(true);
                autoCompleteProduct.showDropDown();
            }
        });

        autoCompleteProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                getListPacking();
            }
        });


        autoCTXPacking.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCTXPacking.setFocusable(false);
                autoCTXPacking.setEnabled(false);
                return false;
            }
        });

        autoCTXPackingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCTXPacking.setEnabled(true);
                autoCTXPacking.showDropDown();
            }
        });

        demoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup demoRadiogroup, int democheckedId) {
                if(democheckedId == R.id.radioButtonDemoReqYes)
                {
                    demosYN=1;
                    visitDemoReq.setVisibility(View.VISIBLE);
                }
                else if(democheckedId == R.id.radioButtonDemoReqNo)
                {
                    demosYN=0;
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
        getListProductName();
        getFarmerNameInDemo();
        saveDemo.setOnClickListener(this);
    }

    private void getListPacking() {
        final String autoProductName = autoCompleteProduct.getText().toString().trim();
        Log.v("Categoryid", "name" + autoProductName);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProductModel>> callPackingList = apiInterface.getPackingList("packing@NameList", autoProductName);
        callPackingList.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                assert response.body() != null;
                packingData.clear();
                packingData.addAll(response.body());
                Log.v("Runcheck1", "user1" + packingData);
                packingList = new ArrayList<String>();
                for (int i = 0; i < packingData.size(); i++) {
                    String lat = packingData.get(i).getPacking();
                    packingList.add(lat);
                }
                final ArrayAdapter<String> adpAllPacking = new ArrayAdapter<String>(DemoEntryActivity.this, android.R.layout.simple_list_item_1, packingList);
                autoCTXPacking.setAdapter(adpAllPacking);
                autoCTXPacking.setEnabled(false);
                Log.v("Runcheck2", "user1" + packingList);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getFarmerNameInDemo() {
        Log.v("fnamelist1", "fnmaelis" + employeeID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<TripModel>> callFarmernmList = apiInterface.getFarmerNameList("getFarmerN@meLists", employeeID);
        callFarmernmList.enqueue(new Callback<ArrayList<TripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TripModel>> call, Response<ArrayList<TripModel>> response) {
                assert response.body() != null;
                farmerNameData.clear();
                farmerNameData.addAll(response.body());
                Log.v("Runcheck1", "user1" + farmerNameData);
                farmerNameList = new ArrayList<String>();
                for (int i = 0; i < farmerNameData.size(); i++) {
                    String lat = farmerNameData.get(i).getVisitedCustomerName();
                    farmerNameList.add(lat);
                }
                final ArrayAdapter<String> adpAllFarmerName = new ArrayAdapter<String>(DemoEntryActivity.this, android.R.layout.simple_list_item_1, farmerNameList);
                autoCompleteFarmerName.setAdapter(adpAllFarmerName);
                autoCompleteFarmerName.setEnabled(false);
                Log.v("Runcheck2", "user1" + farmerNameList);
//                Toast.makeText(DemoEntryActivity.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<TripModel>> call, Throwable t) {
                Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getListProductName() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProductModel>> callList = apiInterface.getProductList("Productn@meList");
        callList.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {

                assert response.body() != null;
                ProductData.clear();
                ProductData.addAll(response.body());
                Log.v("Runcheck1", "user1" + ProductData);
                list = new ArrayList<String>();
                for (int i = 0; i < ProductData.size(); i++) {
                    String lat = ProductData.get(i).getProductName();
                    list.add(lat);
                }
                final ArrayAdapter<String> adpAllID = new ArrayAdapter<String>(DemoEntryActivity.this, android.R.layout.simple_list_item_1, list);
                autoCompleteProduct.setAdapter(adpAllID);
                autoCompleteProduct.setEnabled(false);
                Log.v("Runcheck2", "user1" + list);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SubmitDemoEntry() {
        final String farmerNameText = autoCompleteFarmerName.getText().toString().trim();
        final String farmerDemoName = editTextDemoName.getText().toString().trim();
        final String farmerDemoType = autoCompleteDemoTy.getText().toString().trim();
        final String farmerCrops = editTextCrops.getText().toString().trim();
        final String farmerCropHealth = autoCTXCropHealth.getText().toString().trim();
        final String farmerUsagety = autoCTXUsage.getText().toString().trim();
        final String farmerProductName = autoCompleteProduct.getText().toString().trim();
        final String farmerpacking = autoCTXPacking.getText().toString().trim();
        final String farmerProductQty = editTextProductQuantity.getText().toString().trim();
        final String farmerWaterQty = editTextWaterQuantity.getText().toString().trim();
        final String farmerAdditions = editTextAdditions.getText().toString().trim();
        final int farmerFallowup = followYN;
        final int demoVisit=demosYN;

        if (radioYes.isChecked()) {
            farmerFollowUpDate = editTextFollowUpDate.getText().toString().trim();
        } else if (radioNo.isChecked()) {
            farmerFollowUpDate = "";
        }

        if (demoYes.isChecked())
        {
            if (employeeID.equals("") || farmerNameText.equals("") || farmerDemoType.equals("") || farmerDemoName.equals("") ||
                    farmerCrops.equals("") || farmerCropHealth.equals("") || farmerUsagety.equals("") || farmerProductName.equals("") || farmerpacking.equals("") ||
                    farmerProductQty.equals("") || farmerWaterQty.equals("") || farmerAdditions.equals("")) {
                Toast.makeText(DemoEntryActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
            }
            else {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry("Visited@CustomerDemoEntries", employeeID, farmerNameText, farmerDemoType, farmerCrops,
                        farmerCropHealth, farmerDemoName, farmerUsagety, farmerProductName, farmerpacking, farmerProductQty,
                        farmerWaterQty, farmerAdditions, farmerFallowup, farmerFollowUpDate,demoVisit);
                empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                    @Override
                    public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                        String value = response.body().getValue();
                        String message = response.body().getMassage();
                        if (value.equals("1")) {
                            autoCompleteFarmerName.setText("");
                            editTextDemoName.setText("");
                            autoCompleteDemoTy.setText("");
                            editTextCrops.setText("");
                            autoCTXCropHealth.setText("");
                            autoCTXUsage.setText("");
                            autoCompleteProduct.setText("");
                            autoCTXPacking.setText("");
                            editTextProductQuantity.setText("");
                            editTextWaterQuantity.setText("");
                            editTextAdditions.setText("");

                            Toast.makeText(DemoEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent demoIntent = new Intent(DemoEntryActivity.this, DemoImageActivity.class);
                            demoIntent.putExtra("visitedEmployeeDemoImage", employeeID);
                            startActivity(demoIntent);
                            finish();
                        } else if (value.equals("0")) {
                            Toast.makeText(DemoEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TripModel> call, Throwable t) {
                        Toast.makeText(DemoEntryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        else if(demoNo.isChecked())
        {
            if (employeeID.equals("") || farmerNameText.equals("")) {
                Toast.makeText(DemoEntryActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
            }
            else {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry("Visited@CustomerDemoEntries", employeeID, farmerNameText, farmerDemoType, farmerCrops,
                    farmerCropHealth, farmerDemoName, farmerUsagety, farmerProductName, farmerpacking, farmerProductQty,
                    farmerWaterQty, farmerAdditions, farmerFallowup, farmerFollowUpDate,demoVisit);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();
                    if (value.equals("1")) {
                        autoCompleteFarmerName.setText("");
                        editTextDemoName.setText("");
                        autoCompleteDemoTy.setText("");
                        editTextCrops.setText("");
                        autoCTXCropHealth.setText("");
                        autoCTXUsage.setText("");
                        autoCompleteProduct.setText("");
                        autoCTXPacking.setText("");
                        editTextProductQuantity.setText("");
                        editTextWaterQuantity.setText("");
                        editTextAdditions.setText("");

                        Toast.makeText(DemoEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent demoIntent = new Intent(DemoEntryActivity.this, DemoImageActivity.class);
                        demoIntent.putExtra("visitedEmployeeDemoImage", employeeID);
                        startActivity(demoIntent);
                        finish();
                    } else if (value.equals("0")) {
                        Toast.makeText(DemoEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {
                    Toast.makeText(DemoEntryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        }








    }




    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SubmitDemo) {
            SubmitDemoEntry();
        }

    }


}
