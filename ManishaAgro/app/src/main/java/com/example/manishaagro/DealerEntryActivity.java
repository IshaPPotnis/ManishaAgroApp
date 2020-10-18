package com.example.manishaagro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerEntryActivity extends AppCompatActivity {
    Toolbar dealerToolbar;
    Button dealerSubmitButton;
    EditText editTextDealerName, editTextQuantity;
    AutoCompleteTextView autoCompleteProduct, autoCTX_Packing;
    ImageView autoCTX_ProductImg, autoCTX_PackingImg;
    public int pro_qty = 0;
    public String employeeID = "";
    public ApiInterface apiInterface;
    public ArrayList<ProductModel> ProductData = new ArrayList<ProductModel>();
    public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<ProductModel> packingData = new ArrayList<ProductModel>();
    public ArrayList<String> packingList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_entry);
        dealerToolbar = findViewById(R.id.toolbarDealer);
        setSupportActionBar(dealerToolbar);
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
        editTextDealerName = findViewById(R.id.editTextDealerName);
        editTextQuantity = findViewById(R.id.editTextProductsQty);
        dealerSubmitButton = findViewById(R.id.DealerSubmit);

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeDealerEntry");

        getListProductName();
        getListPacking();

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

        dealerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDealerEntryData();
            }
        });
    }

    private void saveDealerEntryData() {
        final String ids = employeeID;
        final String dealerNameText = editTextDealerName.getText().toString().trim();
        final String autoProductName = autoCompleteProduct.getText().toString().trim();
        final String autoPacking = autoCTX_Packing.getText().toString().trim();
        pro_qty = Integer.parseInt(editTextQuantity.getText().toString().trim());
        Log.v("Dealer", "empDealer" + ids);
        if (ids.equals("") || dealerNameText.equals("") || pro_qty == 0 || autoProductName.equals("") || autoPacking.equals("")) {
            Toast.makeText(DealerEntryActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<DealerModel> empIdDesignationModelCall = apiInterface.insertDealerEntry("dealerEntry@Emp_id", employeeID, dealerNameText, autoProductName, pro_qty, autoPacking);
            empIdDesignationModelCall.enqueue(new Callback<DealerModel>() {
                @Override
                public void onResponse(Call<DealerModel> call, Response<DealerModel> response) {
                    assert response.body() != null;
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    switch (value) {
                        case "1":
                            editTextDealerName.setText("");
                            editTextQuantity.setText("");
                            autoCompleteProduct.setText("");
                            autoCTX_Packing.setText("");
                            break;
                        case "0":
                            break;
                    }
                    Toast.makeText(DealerEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<DealerModel> call, Throwable t) {
                    Toast.makeText(DealerEntryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getListPacking() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProductModel>> callPackingList = apiInterface.getPackingList("packing@NameList");
        callPackingList.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                assert response.body() != null;
                packingData.addAll(response.body());
                Log.v("Runcheck1", "user1" + packingData);
                packingList = new ArrayList<String>();
                for (int i = 0; i < packingData.size(); i++) {
                    String lat = packingData.get(i).getPacking();

                    packingList.add(lat);
                }
                final ArrayAdapter<String> adpAllPacking = new ArrayAdapter<String>(DealerEntryActivity.this, android.R.layout.simple_list_item_1, packingList);
                autoCTX_Packing.setAdapter(adpAllPacking);
                Log.v("Runcheck2", "user1" + packingList);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DealerEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
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
                ProductData.addAll(response.body());
                Log.v("Runcheck1", "user1" + ProductData);
                list = new ArrayList<String>();
                for (int i = 0; i < ProductData.size(); i++) {
                    String lat = ProductData.get(i).getProductName();
                    list.add(lat);
                }
                final ArrayAdapter<String> adpAllID = new ArrayAdapter<String>(DealerEntryActivity.this, android.R.layout.simple_list_item_1, list);
                autoCompleteProduct.setAdapter(adpAllID);
                Log.v("Runcheck2", "user1" + list);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DealerEntryActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
