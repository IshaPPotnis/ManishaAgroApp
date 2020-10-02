package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        String keyforCompare = intent.getStringExtra("EmpStsVal");
        Log.v("yek", "keyyy" + keyforCompare);
        if (keyforCompare != null && keyforCompare.equals("EmployeeStatusVisitedCustomer")) {
            String name = intent.getStringExtra("StatusCustName");
            String dttravel = intent.getStringExtra("StatusDtTravel");
            String dtreturn = intent.getStringExtra("StatusdtReturn");
            txt1.setText(name);
            txt2.setText(dttravel);
            txt3.setText(dtreturn);
        }
    }
}
