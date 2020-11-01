package com.example.manishaagro.employee;

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

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerEntryActivity extends AppCompatActivity {
    Toolbar dealerToolbar;
    Button goToProductActButton;
    EditText editTextDealerName;


    public String employeeID = "";
    public ApiInterface apiInterface;


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


        editTextDealerName = findViewById(R.id.editTextDealerName);
        goToProductActButton=findViewById(R.id.gotoProdctAct);



        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeDealerEntry");





goToProductActButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intentadpEmp = new Intent(DealerEntryActivity.this, ProductActivity.class);
        intentadpEmp.putExtra("EmployeeIdInDealer",employeeID);
        intentadpEmp.putExtra("DealerNameDealerAct",editTextDealerName.getText().toString().trim());
        intentadpEmp.putExtra("EmpID&DealerNAME", "EmpID&Dealer");
        startActivity(intentadpEmp);
    }
});

    }



  /*  private void saveDealerEntryData() {
       }*/


}
