package com.example.manishaagro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.manishaagro.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends Activity {
    ApiInterface apiInterface;
    String employeeID = "";
    ImageView addImg;
    AutoCompleteTextView autoCompleteProduct;
    AutoCompleteTextView autoCTXPacking;
    ImageView autoCTXProductImg;
    ImageView autoCTXPackingImg;
    EditText editTextProductQuantity;
    Button addbt;
    ListView listView;
    ListViewAdapter adapter;
    public List<String> purchasedProductList = new ArrayList<>();
    public List<ProductModel> ProductData = new ArrayList<>();
    public List<String> list = new ArrayList<>();
    public List<ProductModel> packingData = new ArrayList<>();
    public List<String> packingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeProductAct");
        addImg = findViewById(R.id.circleAddImg);
        listView = findViewById(R.id.listView1);
        autoCompleteProduct = findViewById(R.id.autoCompleteProductName);
        autoCTXPacking = findViewById(R.id.autoCompletePacking);
        autoCTXProductImg = findViewById(R.id.autoTextProdctImg);
        autoCTXPackingImg = findViewById(R.id.autoTextPackingImg);
        editTextProductQuantity = findViewById(R.id.editTextProductQty);
        addbt = findViewById(R.id.add);

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

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                adapter = new ListViewAdapter(ProductActivity.this, purchasedProductList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        getListProductName();
    }

    private void insertData() {
        String productName = autoCompleteProduct.getText().toString();
        String packing = autoCTXPacking.getText().toString();
        String quantity = editTextProductQuantity.getText().toString();
        purchasedProductList.add(productName + "-" + packing + "-" + quantity);
        autoCompleteProduct.setText("");
        autoCTXPacking.setText("");
        editTextProductQuantity.setText("");
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
                packingList = new ArrayList<>();
                for (int i = 0; i < packingData.size(); i++) {
                    String lat = packingData.get(i).getPacking();
                    packingList.add(lat);
                }
                final ArrayAdapter<String> adpAllPacking = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_list_item_1, packingList);
                autoCTXPacking.setAdapter(adpAllPacking);
                autoCTXPacking.setEnabled(false);
                Log.v("Runcheck2", "user1" + packingList);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Have some error", Toast.LENGTH_LONG).show();
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
                list = new ArrayList<>();
                for (int i = 0; i < ProductData.size(); i++) {
                    String lat = ProductData.get(i).getProductName();
                    list.add(lat);
                }
                final ArrayAdapter<String> adpAllID = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_list_item_1, list);
                autoCompleteProduct.setAdapter(adpAllID);
                autoCompleteProduct.setEnabled(false);
                Log.v("Runcheck2", "user1" + list);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
