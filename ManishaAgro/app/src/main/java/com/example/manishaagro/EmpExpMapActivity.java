package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class EmpExpMapActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    Toolbar mapToolbar;


    Button expenseSubmit;
    public String employeeID = "";
    ConnectionDetector connectionDetector;
    AutoCompleteTextView autoCompleteExp;
    AutoCompleteTextView autoCTXEmp;
    ImageView autoCTXExpImg;
    ImageView autoCTXEmpImg;

    Button addbutton;
    ListView listView;

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
        listView=findViewById(R.id.listViewEXPEMP);
        Intent intent = getIntent();
        String keyCompare1 = intent.getStringExtra("mapMgrID");
        if (keyCompare1 != null && keyCompare1.equals("ExpEmpMapMgrID")) {
            employeeID = intent.getStringExtra("mgrEmployee");
        }
    }
}
