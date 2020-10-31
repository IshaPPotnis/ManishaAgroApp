package com.example.manishaagro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.model.ProductModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends Activity {
    ApiInterface apiInterface;
    String employeeID="";
    String h;
    ImageView addImg;
    AutoCompleteTextView autoCompleteProduct;
    AutoCompleteTextView autoCTXPacking;
    ImageView autoCTXProductImg;
    ImageView autoCTXPackingImg;
    EditText editTextProductQuantity;
    int i=0;
    Button addbt;
    ListView listView;
    ListViewAdapter adapter;
    private ArrayList<HashMap<String, String>> listofProduct=new ArrayList<HashMap<String,String>>();
    HashMap<String,String>  hashval=new HashMap<String, String>(100);
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";

    public ArrayList<ProductModel> ProductData = new ArrayList<ProductModel>();
    public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<ProductModel> packingData = new ArrayList<ProductModel>();
    public ArrayList<String> packingList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeProductAct");

        addImg=findViewById(R.id.circleAddImg);
        listView=(ListView)findViewById(R.id.listView1);

        autoCompleteProduct = findViewById(R.id.autoCompleteProductName);
        autoCTXPacking = findViewById(R.id.autoCompletePacking);
        autoCTXProductImg = findViewById(R.id.autoTextProdctImg);
        autoCTXPackingImg = findViewById(R.id.autoTextPackingImg);
        editTextProductQuantity = findViewById(R.id.editTextProductQty);
        addbt=findViewById(R.id.add);


       // populateList();

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
                adapter=new ListViewAdapter(ProductActivity.this,listofProduct);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

           /* file    String res="";

                res=res + autoCompleteProduct.getText().toString() + "\t" + autoCTXPacking.getText().toString() + "\t" + editTextProductQuantity.getText().toString() + "\n";
                writeData(res);*/
            }
        });

  /*file      addbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result=readFile();

                StringTokenizer st=new StringTokenizer(result,"\n");
                while(st.hasMoreTokens())
                {
                    listfor.add(st.nextToken());
                }
            }
        });*/


        getListProductName();
    }



/*file    public void writeData(String data)      // to writeData to file
    {

        try {
            File file = new File(Environment.getExternalStorageDirectory(), "text");
           // String fileName="listData.txt";
                if (!file.exists()) {
                 file.mkdir();
               }
             h = "sample";

            File filepath = new File(file, h + ".txt");
            FileWriter writer = new FileWriter(filepath,true);
                      writer.append(data);

                    writer.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

       private String readFile()
        {
        File fileEvents = new File(Environment.getExternalStorageDirectory()+"/text/sample");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        return result;
    }*/


    private void insertData()
    {
        String item1 = autoCompleteProduct.getText().toString();
        String item2 = autoCTXPacking.getText().toString();
        String item3 = editTextProductQuantity.getText().toString();

        hashval.put(FIRST_COLUMN, item1);
        hashval.put(SECOND_COLUMN, item2);
        hashval.put(THIRD_COLUMN, item3);

        listofProduct.add(hashval);

        autoCompleteProduct.setText("");
        autoCTXPacking.setText("");
        editTextProductQuantity.setText("");;
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
                final ArrayAdapter<String> adpAllPacking = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_list_item_1, packingList);
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
                list = new ArrayList<String>();
                for (int i = 0; i < ProductData.size(); i++) {
                    String lat = ProductData.get(i).getProductName();
                    list.add(lat);
                }
                final ArrayAdapter<String> adpAllID = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_list_item_1, list);
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
