package com.example.manishaagro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EmployeeStatusActivity extends AppCompatActivity {

    private String  keyforCompare,name,dttravel,dtreturn;
    TextView txt1,txt2,txt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_status);

        txt1=findViewById(R.id.empstatus1);
        txt2=findViewById(R.id.empstatus2);
        txt3=findViewById(R.id.empstatus3);


        Intent intent = getIntent();
        keyforCompare=intent.getStringExtra("EmpStsVal");
        Log.v("yek", "keyyy" + keyforCompare);
        if(keyforCompare.equals("EmployeeStatusVisitedCustomer"))
        {
            name = intent.getStringExtra("StatusCustName");
            dttravel = intent.getStringExtra("StatusDtTravel");
            dtreturn = intent.getStringExtra("StatusdtReturn");

            txt1.setText(name);
            txt2.setText(dttravel);
            txt3.setText(dtreturn);
        }

    }
}
