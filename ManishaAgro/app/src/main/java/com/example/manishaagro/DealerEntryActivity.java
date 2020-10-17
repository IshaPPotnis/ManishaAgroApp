package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.ProductModel;
import com.example.manishaagro.model.TripModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerEntryActivity extends AppCompatActivity
{
    public String employeeID="";
    Toolbar dealerToolbar;
    public int pro_qty=0;
    public ApiInterface apiInterface;

    EditText edtxDealername,edtxQty;

    public ArrayList<ProductModel> ProductData = new ArrayList<ProductModel>();

    public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<ProductModel> packingData = new ArrayList<ProductModel>();

    public ArrayList<String> packinglist = new ArrayList<String>();
    AutoCompleteTextView autoCompleteProduct,autoCTX_Packing;
    ImageView autoCTX_ProductImg,autoCTX_PackingImg;
    Button dealersubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_entry);
        dealerToolbar=findViewById(R.id.toolbarDealer);

        setSupportActionBar(dealerToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        autoCompleteProduct=findViewById(R.id.autoCompleteProductName);
        autoCTX_Packing=findViewById(R.id.autoCompletePacking);
        autoCTX_ProductImg=findViewById(R.id.autoTextProdctImg);
        autoCTX_PackingImg=findViewById(R.id.autoTextPackingImg);

        edtxDealername=findViewById(R.id.editTextDealerName);
        edtxQty=findViewById(R.id.editTextProductsQty);
        dealersubmitButton=findViewById(R.id.DealerSubmit);

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeDealerEntry");

        getListProductname("Productn@meList");
        getListPacking("packing@NameList");

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


        dealersubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDealerEntryData();
            }
        });

    }
    private void saveDealerEntryData()
    {
        final String dealerNameText = edtxDealername.getText().toString().trim();

     final String  pro_qty1= edtxQty.getText().toString().trim();

        try {
            pro_qty= Integer.parseInt(pro_qty1);
        }catch (NumberFormatException e){
            System.out.println("not a number");
        }


        final String autoProductnm = autoCompleteProduct.getText().toString().trim();
        final String autoPacking = autoCTX_Packing.getText().toString().trim();
final String ids=employeeID;

        Log.v("Dealer", "empDealer" + ids);



        if (ids.equals("")||dealerNameText.equals("")||autoProductnm.equals("")||autoPacking.equals(""))
        {
            Toast.makeText(DealerEntryActivity.this,"Fields Are Empty",Toast.LENGTH_SHORT);
        }
        else
        {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<DealerModel> empIdDesignationModelCall = apiInterface.insertDealerEntry("dealerEntry@Emp_id", employeeID,dealerNameText,autoProductnm,pro_qty,autoPacking);
            empIdDesignationModelCall.enqueue(new Callback<DealerModel>() {
                @Override
                public void onResponse(Call<DealerModel> call, Response<DealerModel> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();


                    if(value.equals("1"))
                    {
                        edtxDealername.setText("");
                        edtxQty.setText("");
                        autoCompleteProduct.setText("");
                        autoCTX_Packing.setText("");

                        Toast.makeText(DealerEntryActivity.this,message,Toast.LENGTH_SHORT);

                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(DealerEntryActivity.this,message,Toast.LENGTH_SHORT);
                    }


                }

                @Override
                public void onFailure(Call<DealerModel> call, Throwable t) {
                    Toast.makeText(DealerEntryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private ArrayList<String> getListPacking(final String key)
    {
        apiInterface =ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProductModel>> callpackinglist=apiInterface.getPackingList(key);
        callpackinglist.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                packingData.addAll(response.body());
                Log.v("Runcheck1", "user1" + packingData);
                packinglist = new ArrayList<String>();
                for (int i = 0; i < packingData.size(); i++) {
                    String lat = packingData.get(i).getPacking();

                    packinglist.add(lat);
                }
                final ArrayAdapter<String> adpallPacking = new ArrayAdapter<String>(DealerEntryActivity.this, android.R.layout.simple_list_item_1, packinglist);
                autoCTX_Packing.setAdapter(adpallPacking);
                Log.v("Runcheck2", "user1" + packinglist);

            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DealerEntryActivity.this,"Have some error",Toast.LENGTH_LONG).show();
            }
        });

        return packinglist;
    }

    private ArrayList<String> getListProductname(final String key)
    {
        apiInterface =ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProductModel>> calllist=apiInterface.getProductList(key);
        calllist.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {

                ProductData.addAll(response.body());
                Log.v("Runcheck1", "user1" + ProductData);
                list = new ArrayList<String>();
                for (int i = 0; i < ProductData.size(); i++) {
                    String lat = ProductData.get(i).getProductName();

                    list.add(lat);
                }
                final ArrayAdapter<String> adpallID = new ArrayAdapter<String>(DealerEntryActivity.this, android.R.layout.simple_list_item_1, list);
                autoCompleteProduct.setAdapter(adpallID);
                Log.v("Runcheck2", "user1" + list);

            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DealerEntryActivity.this,"Have some error",Toast.LENGTH_LONG).show();
            }
        });
        return list;

    }
}
