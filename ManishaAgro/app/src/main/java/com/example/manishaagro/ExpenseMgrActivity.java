package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.manishaagro.model.ExpenseModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpenseMgrActivity extends AppCompatActivity implements View.OnClickListener {
    ApiInterface apiInterface;
    Toolbar expenseToolbar;
    EditText editTextName, editTextda, editTextOutda,editTextlodget, editTextlodged, editTextmobile, editTextkm;

    Button expenseSubmit;
    public String employeeID = "";
    ConnectionDetector connectionDetector;
    double doubleDA=0;
    double doubleoutDA=0;
    double doubleLodgeT=0;
    double doubleLodgeD=0;
    double doublemobile=0;
    int kmlimitval=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_mgr);
        connectionDetector=new ConnectionDetector();
        expenseToolbar = findViewById(R.id.toolbarExpenseAct);
        setSupportActionBar(expenseToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        editTextName=findViewById(R.id.editTextExpenseName);
        editTextda=findViewById(R.id.editTextDA);
        editTextOutda=findViewById(R.id.editTextOutDA);
        editTextlodget=findViewById(R.id.editTextLodgeT);
        editTextlodged=findViewById(R.id.editTextLodgeD);
        editTextmobile=findViewById(R.id.editTextMobileAmt);
        editTextkm=findViewById(R.id.editTextKMLimit);
        expenseSubmit=findViewById(R.id.expenseActSubmit);

        Intent intent = getIntent();
        String keyCompare1 = intent.getStringExtra("expenseEmpID");
        if (keyCompare1 != null && keyCompare1.equals("ExpenseEmp@ID")) {
            employeeID = intent.getStringExtra("mgrEmployee");
        }
        expenseSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.expenseActSubmit)
        {

            if (connectionDetector.isConnected(ExpenseMgrActivity.this))
            {
                insetExpense();
            }
            else
            {
                Toast.makeText(ExpenseMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }
    }
    private void insetExpense()
    {
        final String expName = editTextName.getText().toString().trim();

        final String expDA = editTextda.getText().toString().trim();
        doubleDA= Double.parseDouble(expDA);

        final String expOutDA = editTextOutda.getText().toString().trim();
        doubleoutDA= Double.parseDouble(expOutDA);

        final String expLodgeT = editTextlodget.getText().toString().trim();
        doubleLodgeT= Double.parseDouble(expLodgeT);

        final String expLodgeD = editTextlodged.getText().toString().trim();
        doubleLodgeD= Double.parseDouble(expLodgeD);

        final String expMobile = editTextmobile.getText().toString().trim();
        doublemobile= Double.parseDouble(expMobile);

        final String expKMLimit = editTextkm.getText().toString().trim();
        kmlimitval= Integer.parseInt(expKMLimit);

        if (employeeID.equals("") ||expName.equals("")||doubleDA==0||doubleoutDA==0|| doubleLodgeT==0||
                doubleLodgeD==0|| doublemobile==0|| kmlimitval==0) {
            Toast.makeText(ExpenseMgrActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ExpenseModel> expModelcall = apiInterface.insertExpenseEntry("Add@ExpenseForPerEmpPost", expName, doubleDA, doubleoutDA, doubleLodgeT, doubleLodgeD,doublemobile,kmlimitval);
            expModelcall.enqueue(new Callback<ExpenseModel>() {
                @Override
                public void onResponse(Call<ExpenseModel> call, Response<ExpenseModel> response) {
                    String value=response.body().getValue();
                    String message=response.body().getMessage();
                    if(value.equals("1"))
                    {
                        editTextName.setText("");
                        editTextda.setText("");
                        editTextOutda.setText("");
                                editTextlodget.setText("");
                                        editTextlodged.setText("");
                        editTextmobile.setText("");
                                editTextkm.setText("");
                        Toast.makeText(ExpenseMgrActivity.this,message,Toast.LENGTH_SHORT).show();

                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(ExpenseMgrActivity.this,message,Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ExpenseModel> call, Throwable t) {
                    if (connectionDetector.isConnected(ExpenseMgrActivity.this))
                    {
                        Toast.makeText(ExpenseMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ExpenseMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}
