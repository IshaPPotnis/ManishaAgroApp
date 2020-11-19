package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.ProfileModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_PROFILE;

public class ExpenseReportActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar rptToolbar;
    ConnectionDetector connectionDetector;
    public String employeeID = "";
    Button submitExpense;
    public ApiInterface apiInterface;
    TextView name,date,totalkm;
    EditText editda,editoutda,editT,editD,editRmk;
    double doubleDA=0;
    double doubleoutDA=0;
    double doubleLodgeT=0;
    double doubleLodgeD=0;
    double doublemobile=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);
        connectionDetector=new ConnectionDetector();
        rptToolbar = findViewById(R.id.toolbarExpRpt);
        setSupportActionBar(rptToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        submitExpense=findViewById(R.id.submitOtherExp);
        name=findViewById(R.id.textTextEmpname);
        date=findViewById(R.id.textTextdate);
        totalkm=findViewById(R.id.rpttotalKMVal);
        editRmk=findViewById(R.id.editTextRptRemark);
        editda=findViewById(R.id.editTextRptDA);
        editoutda=findViewById(R.id.editTextRptOutDA);
        editT=findViewById(R.id.editTextRptT);
        editD=findViewById(R.id.editTextRptD);

        editda.setEnabled(false);
        editoutda.setEnabled(false);
        editT.setEnabled(false);
        editD.setEnabled(false);

        submitExpense.setEnabled(false);
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("EmployeeExpenseRptMeterEntry");
        getExpenseRptData();

        submitExpense.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getExpenseRptData();
    }
    private void getExpenseRptData()
    {

        final String empid = employeeID;
        Log.v("yek", "empid" + empid);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DailyEmpExpenseModel> expenseCalls = apiInterface. getEmpExpenseInRpt("get@expenseData@Report", empid);
        expenseCalls.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                String value=response.body().getValue();
                String message =response.body().getMessage();


                if (value.equals("1"))
                {
                    submitExpense.setEnabled(true);
                    String idemp=response.body().getEmpid();
                    String curdate=response.body().getCurdate();



                    String km= String.valueOf(response.body().getKmlimit());

                    String strda= String.valueOf(response.body().getDa());
                    String stroutda= String.valueOf(response.body().getOutda());
                    if(curdate!=null)
                    {
                        String[] arrdate=curdate.split(" ");
                        date.setText(arrdate[0]);
                    }
                    name.setText(idemp);

                    totalkm.setText(km);
                    editda.setText(strda);
                    editoutda.setText(stroutda);
                    if(editoutda.equals("00"))
                    {
                        editD.setEnabled(false);
                        editT.setEnabled(false);
                    }
                    else
                    {
                        editD.setEnabled(true);
                        editT.setEnabled(true);
                    }
                    Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();
                }
                else if(value.equals("0"))
                {
                    submitExpense.setEnabled(false);
                    Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                Toast.makeText(ExpenseReportActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
    private void insetOtherEmpExpense()
    {
        if (editda.equals("")||editoutda.equals("")||editT.equals("")||editD.equals("")||editRmk.equals(""))
        {
            Toast.makeText(ExpenseReportActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            doubleLodgeT = ParseDouble(editT.getText().toString().trim());
            doubleLodgeD = ParseDouble(editD.getText().toString().trim());
            String strRmk=editRmk.getText().toString().trim();
            if (strRmk.equals("")||doubleLodgeT==0||doubleLodgeD==0)
            {
                Toast.makeText(ExpenseReportActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
            }
            else
            {
                
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.submitOtherExp)
        {
            if (connectionDetector.isConnected(ExpenseReportActivity.this))
            {


                insetOtherEmpExpense();


            }
            else
            {
                Toast.makeText(ExpenseReportActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }

    }
}
