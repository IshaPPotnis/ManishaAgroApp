package com.example.manishaagro.employee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DealerProductMap;
import com.example.manishaagro.model.ProductModel;
import com.example.manishaagro.model.VisitProductMapModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    Toolbar toolbarProductAct;
    ConnectionDetector connectionDetector;
    ApiInterface apiInterface;
    String employeeID = "";
    String visitids="";
    String dealerids="";
    AutoCompleteTextView autoCompleteProduct;
    AutoCompleteTextView autoCTXPacking;
    ImageView autoCTXProductImg;
    ImageView autoCTXPackingImg;
    EditText editTextProductQuantity;
    Button addbutton,proFinish;
    ListView listView;
    String farmerName;
    ListViewAdapter adapter;
    public List<String> purchasedProductList = new ArrayList<>();
    public List<ProductModel> ProductData = new ArrayList<>();
    public List<String> productList = new ArrayList<>();
    public List<ProductModel> packingData = new ArrayList<>();
    public List<String> packingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        connectionDetector=new ConnectionDetector();
        toolbarProductAct=findViewById(R.id.toolbarProductAct);
        proFinish=findViewById(R.id.addProductFinish);

        setSupportActionBar(toolbarProductAct);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpID&DealerNAME");
        String keyCompareofCust=intent.getStringExtra("CustVisitEmpId&Visitid");
        farmerName = intent.getStringExtra("farmerName");

        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("EmpID&Dealer")) {
            employeeID= intent.getStringExtra("EmployeeIdInDealer");
             dealerids=intent.getStringExtra("DealerNameDealerAct");
             proFinish.setVisibility(View.VISIBLE);

        }
        if(keyCompareofCust!=null && keyCompareofCust.equals("CustEmployeeId&Visitid"))
        {
            employeeID= intent.getStringExtra("visitedEmployeeProductAct");
            visitids = intent.getStringExtra("visitedEmployeeProductActVisitID");
            proFinish.setVisibility(View.GONE);
        }



        Log.v("Getting visit", "id" + visitids);
        Log.v("Getting visitemp", "empid" + employeeID);

        listView = findViewById(R.id.listView1);
        autoCompleteProduct = findViewById(R.id.autoCompleteProductName);
        autoCTXPacking = findViewById(R.id.autoCompletePacking);
        autoCTXProductImg = findViewById(R.id.autoTextProdctImg);
        autoCTXPackingImg = findViewById(R.id.autoTextPackingImg);
        editTextProductQuantity = findViewById(R.id.editTextProductQty);
        addbutton = findViewById(R.id.add);

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

        proFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnected(ProductActivity.this))
                {
                    if (visitids.equals(""))
                    {
                        insertDealerProductData();
                        adapter = new ListViewAdapter(ProductActivity.this, purchasedProductList);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        insertData();
                        adapter = new ListViewAdapter(ProductActivity.this, purchasedProductList);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }


            }
        });
        if (connectionDetector.isConnected(ProductActivity.this))
        {
            getListProductName();
        }
        else
        {
            Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (visitids.equals(""))
        {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.next_arrow, menu);
            MenuItem arrow = menu.findItem(R.id.right_next_arrow);
            MenuItem nexttxt  = menu.findItem(R.id.next_text);
            arrow.setVisible(false);
            nexttxt.setVisible(false);

        }
        else
        {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.next_arrow, menu);
            MenuItem arrow = menu.findItem(R.id.right_next_arrow);
            MenuItem nexttxt  = menu.findItem(R.id.next_text);
            arrow.setVisible(true);
            nexttxt.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.right_next_arrow) {
            if (connectionDetector.isConnected(ProductActivity.this))
            {
                Intent demoIntent = new Intent(ProductActivity.this, DemoImageActivity.class);
                demoIntent.putExtra("farmerName",farmerName);
                demoIntent.putExtra("visitedEmployeeProductActivityToDemoimg", employeeID);
                startActivity(demoIntent);
                finish();
            }
            else
            {
                Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

            return true;
        }
        if(item.getItemId()==R.id.next_text)
        {
            if (connectionDetector.isConnected(ProductActivity.this))
            {
                Intent demoIntent = new Intent(ProductActivity.this, DemoImageActivity.class);
                demoIntent.putExtra("visitedEmployeeProductActivityToDemoimg", employeeID);
                startActivity(demoIntent);
                finish();
            }
            else
            {
                Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertData() {
        String productName = autoCompleteProduct.getText().toString();
        String packing = autoCTXPacking.getText().toString();
        String quantity = editTextProductQuantity.getText().toString();



        if (productName.equals("")||packing.equals("")||quantity.equals(""))
        {
            Toast.makeText(ProductActivity.this,"Select Products",Toast.LENGTH_LONG).show();
        }
        else
        {purchasedProductList.add(productName + "-" + packing + "-" + quantity);
            int visitIdInt= Integer.parseInt(visitids);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<VisitProductMapModel> insetProductdata = apiInterface.insertProductDataEntry("Add@ProductD@ta",visitIdInt,productName,packing,quantity);
            insetProductdata.enqueue(new Callback<VisitProductMapModel>() {
                @Override
                public void onResponse(Call<VisitProductMapModel> call, Response<VisitProductMapModel> response) {
                    String value=response.body().getValue();
                    String message=response.body().getMessage();
                    if (value.equals("1"))
                    {
                        autoCompleteProduct.setText("");
                        autoCTXPacking.setText("");
                        editTextProductQuantity.setText("");
                        Toast.makeText(ProductActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(ProductActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<VisitProductMapModel> call, Throwable t) {

                    if (connectionDetector.isConnected(ProductActivity.this))
                    {
                        Toast.makeText(ProductActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }



    }

    private void insertDealerProductData()
    {
        String productName = autoCompleteProduct.getText().toString();
        String packing = autoCTXPacking.getText().toString();
        String quantity = editTextProductQuantity.getText().toString();


        Log.v("Dealer", "empDealer" + dealerids);

        if (productName.equals("")||packing.equals("")||quantity.equals(""))
        {
            Toast.makeText(ProductActivity.this,"Select Products",Toast.LENGTH_LONG).show();
        }
        else
        {purchasedProductList.add(productName + "-" + packing + "-" + quantity);
            int dealerIdInt= Integer.parseInt(dealerids);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<DealerProductMap> insetdealerProductdata = apiInterface.insertDealersProductDataEntry("Add@Dealer@ProductD@ta",dealerIdInt,productName,packing,quantity);
            insetdealerProductdata.enqueue(new Callback<DealerProductMap>() {
                @Override
                public void onResponse(Call<DealerProductMap> call, Response<DealerProductMap> response) {

                    String value=response.body().getValue();
                    String message=response.body().getMessage();
                    if (value.equals("1"))
                    {
                        autoCompleteProduct.setText("");
                        autoCTXPacking.setText("");
                        editTextProductQuantity.setText("");
                      //  Toast.makeText(ProductActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(ProductActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DealerProductMap> call, Throwable t) {
                    if (connectionDetector.isConnected(ProductActivity.this))
                    {
                        Toast.makeText(ProductActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }





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
                if (connectionDetector.isConnected(ProductActivity.this))
                {
                    Toast.makeText(ProductActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
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
                productList = new ArrayList<>();
                for (int i = 0; i < ProductData.size(); i++) {
                    productList.add(ProductData.get(i).getProductName());
                }
                final ArrayAdapter<String> adpAllID = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_list_item_1, productList);
                autoCompleteProduct.setAdapter(adpAllID);
                autoCompleteProduct.setEnabled(false);
                Log.v("Runcheck2", "user1" + productList);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                if (connectionDetector.isConnected(ProductActivity.this))
                {
                    Toast.makeText(ProductActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ProductActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
