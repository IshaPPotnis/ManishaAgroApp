package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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
import android.widget.TextView;
import android.widget.Toast;

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
    ApiInterface apiInterface;
    Toolbar visitDemoToolbar;
    TextView BackText;
    CardView FollowIsRequird;
    EditText edtxDemoFarmername,edtxDemoName,edtxCrops,edtxProductNm,edtxProductQty,edtxWaterQty,edtxAdditions,edtxFollowupdt;
    RadioGroup rradioGrpFollwup;
    RadioButton radioYes,radioNo;
    AutoCompleteTextView autoCompleteDemoTy,autoCTX_Usage,autoCTX_CropHealth,autoCompleteProduct,autoCTX_Packing,autocomFarmername;

    String employeeID="";
    public int followYN=0;
    public String farmerfollowDate="";
    ImageView AutoCTXImage,autoCTX_UsageImg,autoCTX_CropHealthImg,autoCTX_ProductImg,autoCTX_PackingImg,autoCTX_FarmerImg;
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

        visitDemoToolbar=findViewById(R.id.toolbarDemo);
        setSupportActionBar(visitDemoToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        autoCompleteProduct = findViewById(R.id.autoCompleteProductName);
        autoCTX_Packing = findViewById(R.id.autoCompletePacking);
        autoCTX_ProductImg = findViewById(R.id.autoTextProdctImg);
        autoCTX_PackingImg = findViewById(R.id.autoTextPackingImg);

        BackText=findViewById(R.id.BackfromDemo);
        autocomFarmername=findViewById(R.id.autoCompleteFarmerName);
        autoCTX_FarmerImg=findViewById(R.id.autoTextFarmerNameImg);
        FollowIsRequird=findViewById(R.id.visitCardFollowupRequired);
        edtxDemoName=findViewById(R.id.editTextDemoName);
        edtxCrops=findViewById(R.id.editTextCrop);
        edtxProductQty=findViewById(R.id.editTextProductQty);
        edtxWaterQty=findViewById(R.id.editTextWaterQty);
        edtxAdditions=findViewById(R.id.editTextAdditions);

        edtxFollowupdt=findViewById(R.id.editTextFollowupDate);

        rradioGrpFollwup=findViewById(R.id.RadioGropFollowup);
        radioYes=findViewById(R.id.radioButtonYes);
        radioNo=findViewById(R.id.radioButtonNo);


        autoCompleteDemoTy=findViewById(R.id.autoCompleteDemoType);
        AutoCTXImage=findViewById(R.id.autoTextImg);
        autoCTX_CropHealth=findViewById(R.id.autoCompleteCropHealth);
        autoCTX_Usage=findViewById(R.id.autoCompleteUsageType);
        autoCTX_CropHealthImg=findViewById(R.id.autoTextCropHealthImg);
        autoCTX_UsageImg=findViewById(R.id.autoTextUsageTypeImg);

        saveDemo=findViewById(R.id.SubmitDemo);

        autoCTX_Packing.setEnabled(false);
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
                edtxFollowupdt.setText(sdf.format(myCalendar.getTime()));


            }

        };

        edtxFollowupdt.setFocusableInTouchMode(false);
        edtxFollowupdt.setFocusable(false);
        edtxFollowupdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DemoEntryActivity.this,R.style.DialogTheme, datePickerListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        List<USAGE_TYPE> enumListUsage = Arrays.asList(USAGE_TYPE.class.getEnumConstants());
        final ArrayAdapter<USAGE_TYPE> adpaUsage = new ArrayAdapter<USAGE_TYPE>(this, android.R.layout.simple_list_item_1, enumListUsage);
        autoCTX_Usage.setAdapter(adpaUsage);
        autoCTX_Usage.setEnabled(false);

        autoCTX_Usage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCTX_Usage.setFocusable(false);
                autoCTX_Usage.setEnabled(false);
                return false;
            }
        });

        autoCTX_UsageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCTX_Usage.setEnabled(true);
                autoCTX_Usage.showDropDown();
            }
        });



        List<CROP_HEALTH> enumListCrop = Arrays.asList(CROP_HEALTH.class.getEnumConstants());
        final ArrayAdapter<CROP_HEALTH> adpaCropHealth = new ArrayAdapter<CROP_HEALTH>(this, android.R.layout.simple_list_item_1, enumListCrop);
        autoCTX_CropHealth.setAdapter(adpaCropHealth);
        autoCTX_CropHealth.setEnabled(false);

        autoCTX_CropHealth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCTX_CropHealth.setFocusable(false);
                autoCTX_CropHealth.setEnabled(false);
                return false;
            }
        });

        autoCTX_CropHealthImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCTX_CropHealth.setEnabled(true);
                autoCTX_CropHealth.showDropDown();
            }
        });

        List<DEMO_TYPE> enumList = Arrays.asList(DEMO_TYPE.class.getEnumConstants());
        final ArrayAdapter<DEMO_TYPE> adpallID = new ArrayAdapter<DEMO_TYPE>(this, android.R.layout.simple_list_item_1, enumList);
        autoCompleteDemoTy.setAdapter(adpallID);
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


        autocomFarmername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                autocomFarmername.setFocusable(false);
                autocomFarmername.setEnabled(false);
                return false;
            }
        });

        autoCTX_FarmerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomFarmername.setEnabled(true);
                autocomFarmername.showDropDown();

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

        autoCTX_ProductImg.setOnClickListener(new View.OnClickListener() {
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


        autoCTX_Packing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCTX_Packing.setFocusable(false);
                autoCTX_Packing.setEnabled(false);
                return false;
            }
        });

        autoCTX_PackingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCTX_Packing.setEnabled(true);

                autoCTX_Packing.showDropDown();
            }
        });



        rradioGrpFollwup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.radioButtonYes)
                {
                    followYN=1;
                    FollowIsRequird.setVisibility(View.VISIBLE);


                }
                else if(checkedId == R.id.radioButtonNo)
                {
                    followYN=0;
                    FollowIsRequird.setVisibility(View.INVISIBLE);

                }

            }
        });

        getListProductName();

        getFarmernameInDemo();




        saveDemo.setOnClickListener(this);
        BackText.setOnClickListener(this);


    }
    private void getListPacking() {

        final String autoProductName=autoCompleteProduct.getText().toString().trim();

        Log.v("Categoryid", "name" + autoProductName);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProductModel>> callPackingList = apiInterface.getPackingList("packing@NameList",autoProductName);
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
                autoCTX_Packing.setAdapter(adpAllPacking);
                autoCTX_Packing.setEnabled(false);
                Log.v("Runcheck2", "user1" + packingList);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DemoEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getFarmernameInDemo()
    {
        Log.v("fnamelist1", "fnmaelis" + employeeID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<TripModel>> callFarmernmList=apiInterface.getFarmerNameList("getFarmerN@meLists",employeeID);
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
                final ArrayAdapter<String> adpAllFarmernm = new ArrayAdapter<String>(DemoEntryActivity.this, android.R.layout.simple_list_item_1, farmerNameList);
                autocomFarmername.setAdapter(adpAllFarmernm);
                autocomFarmername.setEnabled(false);
                Log.v("Runcheck2", "user1" + farmerNameList);

                Toast.makeText(DemoEntryActivity.this, "Success", Toast.LENGTH_LONG).show();


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



   private void SubmitDemoEntry()
    {
        final String farmerNameText = autocomFarmername.getText().toString().trim();

        final String farmerDemoName = edtxDemoName.getText().toString().trim();
        final String farmerDemoType = autoCompleteDemoTy.getText().toString().trim();
        final String farmerCrops = edtxCrops.getText().toString().trim();
        final String farmerCropHealth = autoCTX_CropHealth.getText().toString().trim();
        final String farmerUsagety = autoCTX_Usage.getText().toString().trim();
        final String farmerProductName = autoCompleteProduct.getText().toString().trim();
        final String farmerpacking = autoCTX_Packing.getText().toString().trim();
        final String farmerProductQty = edtxProductQty.getText().toString().trim();
        final String farmerWaterQty = edtxWaterQty.getText().toString().trim();
        final String farmerAdditions = edtxAdditions.getText().toString().trim();
        final int farmerFallowup=followYN;

        if (radioYes.isChecked())
        {
            farmerfollowDate = edtxFollowupdt.getText().toString().trim();
        }
        else if(radioNo.isChecked())
        {
            farmerfollowDate="";
        }



        if (employeeID.equals("")||farmerNameText.equals("")||farmerDemoType.equals("")||farmerDemoName.equals("")||
                farmerCrops.equals("")|| farmerCropHealth.equals("")||farmerUsagety.equals("")||farmerProductName.equals("")||farmerpacking.equals("")||
                farmerProductQty.equals("")||farmerWaterQty.equals("")||farmerAdditions.equals(""))
        {
            Toast.makeText(DemoEntryActivity.this,"Fields Are Empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry("Visited@CustomerDemoEntries", employeeID,farmerNameText,farmerDemoType,farmerCrops,
                    farmerCropHealth,farmerDemoName,farmerUsagety,farmerProductName,farmerpacking,farmerProductQty,
                    farmerWaterQty,farmerAdditions,farmerFallowup,farmerfollowDate);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();


                    if(value.equals("1"))
                    {
                        autocomFarmername.setText("");
                        edtxDemoName.setText("");
                        autoCompleteDemoTy.setText("");
                        edtxCrops.setText("");
                        autoCTX_CropHealth.setText("");
                        autoCTX_Usage.setText("");
                        autoCompleteProduct.setText("");
                        autoCTX_Packing.setText("");
                        edtxProductQty.setText("");
                        edtxWaterQty.setText("");
                        edtxAdditions.setText("");

                        Toast.makeText(DemoEntryActivity.this,message,Toast.LENGTH_SHORT).show();

                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(DemoEntryActivity.this,message,Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {
                    Toast.makeText(DemoEntryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }



    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.SubmitDemo)
        {
            SubmitDemoEntry();
        }
        if (v.getId() ==R.id.BackfromDemo)
        {
            Intent  visitIntent = new Intent(DemoEntryActivity.this, CustomerVisitStartActivity.class);
           visitIntent.putExtra("visitedEmployeeBackDemoEntry", employeeID);
            visitIntent.putExtra("CheckDemoActivity", "Customer@DemoEntry");
            startActivity(visitIntent);
            finish();
        }
    }


}
