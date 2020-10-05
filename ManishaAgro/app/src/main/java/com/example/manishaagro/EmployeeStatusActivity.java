package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeStatusActivity extends AppCompatActivity {

    TextView txt1, txt2, txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_status);

        txt1 = findViewById(R.id.empstatus1);
        txt2 = findViewById(R.id.empstatus2);
        txt3 = findViewById(R.id.empstatus3);

        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpStsVal");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals(STATUS_EMPLOYEE_VISITED_CUSTOMER)) {
            String name = intent.getStringExtra(STATUS_VISITED_CUSTOMER_NAME);
            String dateOfTravel = intent.getStringExtra(STATUS_DATE_OF_TRAVEL);
            String dateOfReturn = intent.getStringExtra(STATUS_DATE_OF_RETURN);
            txt1.setText(name);
            txt2.setText(dateOfTravel);
            txt3.setText(dateOfReturn);
        }
    }
}
