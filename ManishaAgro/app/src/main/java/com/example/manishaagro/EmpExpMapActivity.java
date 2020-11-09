package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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


import com.example.manishaagro.employee.ListViewAdapter;
import com.example.manishaagro.employee.ProductActivity;
import com.example.manishaagro.model.ExpEmpMapModel;
import com.example.manishaagro.model.ExpenseModel;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.VisitProductMapModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpExpMapActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    Toolbar mapToolbar;

    public List<ExpenseModel> ExpenseData = new ArrayList<>();
    public List<String> expenseDataList = new ArrayList<>();
    public List<ProfileModel> EmpData = new ArrayList<>();
    public List<String> empDataList = new ArrayList<>();

    public String employeeID = "";
    ConnectionDetector connectionDetector;
    AutoCompleteTextView autoCompleteExp;
    AutoCompleteTextView autoCTXEmp;
    ImageView autoCTXExpImg;
    ImageView autoCTXEmpImg;

    Button addbutton;

    int expids=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_exp_map);
        connectionDetector=new ConnectionDetector();
        mapToolbar = findViewById(R.id.toolbarEmpExpMapAct);
        setSupportActionBar(mapToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        autoCompleteExp=findViewById(R.id.autoCompleteExpenseName);
        autoCTXExpImg=findViewById(R.id.autoTextExpImg);
        autoCTXEmp=findViewById(R.id.autoCompleteEmpName);
        autoCTXEmpImg=findViewById(R.id.autoTextEmpImg);

        addbutton=findViewById(R.id.addmap);
        Intent intent = getIntent();
        String keyCompare1 = intent.getStringExtra("mapMgrID");
        if (keyCompare1 != null && keyCompare1.equals("ExpEmpMapMgrID")) {
            employeeID = intent.getStringExtra("mgrEmployee");
        }


        autoCTXExpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteExp.setEnabled(true);
                autoCompleteExp.showDropDown();
            }
        });

        autoCompleteExp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);

            }
        });


        autoCTXEmp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCTXEmp.setFocusable(false);
                autoCTXEmp.setEnabled(false);
                return false;
            }
        });

        autoCTXEmpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCTXEmp.setEnabled(true);
                autoCTXEmp.showDropDown();
            }
        });
        getListExpenseName();
        getListEmpName();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionDetector.isConnected(EmpExpMapActivity.this))
                {
                    insetExpempMapdata();
                }
                else
                {
                    Toast.makeText(EmpExpMapActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void getListExpenseName() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ExpenseModel>> callList = apiInterface.getExpenseList("Expense@Get@meList");
        callList.enqueue(new Callback<ArrayList<ExpenseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ExpenseModel>> call, Response<ArrayList<ExpenseModel>> response) {
                assert response.body() != null;
                ExpenseData.clear();
                ExpenseData.addAll(response.body());
                Log.v("Runcheck1", "user1" + ExpenseData);
                expenseDataList = new ArrayList<>();
                for (int i = 0; i < ExpenseData.size(); i++) {
                    expenseDataList.add(ExpenseData.get(i).getExpenseid()+" "+ExpenseData.get(i).getExpensename());

                }
                final ArrayAdapter<String> adpAllID = new ArrayAdapter<>(EmpExpMapActivity.this, android.R.layout.simple_list_item_1, expenseDataList);
                autoCompleteExp.setAdapter(adpAllID);
                autoCompleteExp.setEnabled(false);
                Log.v("Runcheck2", "user1" + expenseDataList);
            }

            @Override
            public void onFailure(Call<ArrayList<ExpenseModel>> call, Throwable t) {
                if (connectionDetector.isConnected(EmpExpMapActivity.this))
                {
                    Toast.makeText(EmpExpMapActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(EmpExpMapActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void getListEmpName()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProfileModel>> callListprofile = apiInterface.getEmpList("getEmployee@Name@meList");
        callListprofile.enqueue(new Callback<ArrayList<ProfileModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProfileModel>> call, Response<ArrayList<ProfileModel>> response) {
                assert response.body() != null;
                EmpData.clear();
                EmpData.addAll(response.body());
                Log.v("Runcheck1", "user1" + EmpData);
                empDataList = new ArrayList<>();
                for (int i = 0; i < EmpData.size(); i++) {
                    empDataList.add(EmpData.get(i).getEmpId()+" "+EmpData.get(i).getName());

                }
                final ArrayAdapter<String> adpAllID = new ArrayAdapter<>(EmpExpMapActivity.this, android.R.layout.simple_list_item_1, empDataList);
                autoCTXEmp.setAdapter(adpAllID);
                autoCTXEmp.setEnabled(false);
                Log.v("Runcheck2", "user1" + empDataList);
            }

            @Override
            public void onFailure(Call<ArrayList<ProfileModel>> call, Throwable t) {
                if (connectionDetector.isConnected(EmpExpMapActivity.this))
                {
                    Toast.makeText(EmpExpMapActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(EmpExpMapActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void insetExpempMapdata()
    {
        String autoCompleteExpname = autoCompleteExp.getText().toString();
        String[] strexp=autoCompleteExpname.split(" ");
        expids= ParseInteger(strexp[0]);
        String autoCTXEmpname = autoCTXEmp.getText().toString();
        String[] stremp=autoCTXEmpname.split(" ");
        final String eid=stremp[0];

        if (expids==0||eid.equals(""))
        {
            Toast.makeText(EmpExpMapActivity.this,"Select Expense Name and Employee Name",Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.v("epense", "nameid" + expids);
            Log.v("emp", "id" + eid);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ExpEmpMapModel> insetexpempmapdata = apiInterface.insertExpEmpMapDataEntry("Add@ExpEmpMapDataEntry",expids,eid);
            insetexpempmapdata.enqueue(new Callback<ExpEmpMapModel>() {
                @Override
                public void onResponse(Call<ExpEmpMapModel> call, Response<ExpEmpMapModel> response) {
                    String value=response.body().getValue();
                    String message=response.body().getMessage();
                    if (value.equals("1"))
                    {
                        autoCompleteExp.setText("");
                        autoCTXEmp.setText("");

                        Toast.makeText(EmpExpMapActivity.this,message,Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(EmpExpMapActivity.this,message,Toast.LENGTH_LONG).show();
                    }



                }

                @Override
                public void onFailure(Call<ExpEmpMapModel> call, Throwable t) {
                    if (connectionDetector.isConnected(EmpExpMapActivity.this))
                    {
                        Toast.makeText(EmpExpMapActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(EmpExpMapActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


    }



    int ParseInteger(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
}
