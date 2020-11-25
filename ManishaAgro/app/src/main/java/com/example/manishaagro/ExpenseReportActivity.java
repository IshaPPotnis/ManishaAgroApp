package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.ExpenseModel;
import com.example.manishaagro.model.ProfileModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_PROFILE;

public class ExpenseReportActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar rptToolbar;
    ConnectionDetector connectionDetector;
    public String employeeID = "";
    RelativeLayout busRel,bikeRel,driverRel,ActualRel,ActualDisRel;
    Button submitExpense;
    int totalKmforBike=0;
    TextView actualAmtTxt,txtbus,txtbikes,txtDri;
    EditText editActual,editActualdisc;
    TextView textRoute;
    RadioGroup radioGroupFourAmt;
    RadioButton radioBus,radiobike,radioDriver,radioActual;

    TextView refreshTxt,LDtxt,LTtxt;
    TextView textHalt;
    public ApiInterface apiInterface;
    TextView name,date,totalkm;
    EditText editda,editoutda,editT,editD,editRmk,editBusTrain,editBike,editDriver,editOther;
    double doubleDA=0;
    double doubleoutDA=0;
    double doubleLodgeT=0;
    double doubleLodgeD=0;
    double doublemobile=0;
    double doubleBusTrain=0;
    double doubleBike=0;
    double doubleDriver=0;

    double doubleOther=0;

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

        busRel=findViewById(R.id.busTrailRel);
        bikeRel=findViewById(R.id.BikeRelMain);
        driverRel=findViewById(R.id.driverRel);
        ActualRel=findViewById(R.id.ActualRel);
        ActualDisRel=findViewById(R.id.ActualDisRel);
        txtbus=findViewById(R.id.busTxt);
        txtbikes=findViewById(R.id.bikeTxt);
        txtDri=findViewById(R.id.driverRelTxt);
        actualAmtTxt=findViewById(R.id.ActualRelTxt);
        editActual=findViewById(R.id.editTextRptActualRelval);
        editActualdisc=findViewById(R.id.editTextRptActualdis);
        radioGroupFourAmt=findViewById(R.id.radiofourAmt);
        radioBus=findViewById(R.id.radioButtonBustrain);
        radiobike=findViewById(R.id.radioButtonBike);
        radioDriver=findViewById(R.id.radioButtonDriver);
        radioActual=findViewById(R.id.radioButtonActual);
      //  refreshTxt=findViewById(R.id.RefreshTxt);
        submitExpense=findViewById(R.id.submitOtherExp);
        name=findViewById(R.id.textTextEmpname);
        date=findViewById(R.id.textTextdate);
        totalkm=findViewById(R.id.rpttotalKMVal);
        textRoute=findViewById(R.id.rptRoutesVal);
        textHalt=findViewById(R.id.rpthaltVal);
        editRmk=findViewById(R.id.editTextRptRemark);
        editda=findViewById(R.id.editTextRptDA);
        editoutda=findViewById(R.id.editTextRptOutDA);
        editT=findViewById(R.id.editTextRptT);
        editD=findViewById(R.id.editTextRptD);
        editBusTrain=findViewById(R.id.editTextRptBusTrainval);
        editBike=findViewById(R.id.editTextRptBikeval);
        editDriver=findViewById(R.id.editTextRptDriverRelval);
        editOther=findViewById(R.id.editTextRptOtherAmt);

        LDtxt=findViewById(R.id.rptLD);
        LTtxt=findViewById(R.id.rptLT);
        editda.setEnabled(false);
        editoutda.setEnabled(false);
        editT.setEnabled(false);
        editD.setEnabled(false);

        submitExpense.setEnabled(false);
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("EmployeeExpenseRptMeterEntry");
        getExpenseRptData();

        submitExpense.setOnClickListener(this);


        radioGroupFourAmt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedIdRadioBut) {

                if (checkedIdRadioBut==R.id.radioButtonBustrain)
                {
                    editBusTrain.setText("");
                    editBusTrain.setEnabled(true);
                    editBusTrain.setFocusable(true);
                    busRel.setVisibility(View.VISIBLE);
                    bikeRel.setVisibility(View.GONE);
                    driverRel.setVisibility(View.GONE);
                    ActualRel.setVisibility(View.GONE);
                    ActualDisRel.setVisibility(View.GONE);

                    editBike.setText("0.0");
                    editBike.setEnabled(false);


                    editDriver.setText("0.0");
                    editDriver.setEnabled(false);




                    editActual.setText("0.0");
                    editActual.setEnabled(false);


                    editActualdisc.setText("NO");
                    editActualdisc.setEnabled(false);

                }
                if(checkedIdRadioBut==R.id.radioButtonBike)
                {
                    editBike.setText("0.0");
                    editBike.setEnabled(true);

                    editBike.setFocusable(true);


                    busRel.setVisibility(View.GONE);
                    bikeRel.setVisibility(View.VISIBLE);
                    driverRel.setVisibility(View.GONE);
                    ActualRel.setVisibility(View.GONE);
                    ActualDisRel.setVisibility(View.GONE);


                    editBusTrain.setText("0.0");
                    editBusTrain.setEnabled(false);


                    editDriver.setText("0.0");
                    editDriver.setEnabled(false);



                    editActual.setText("0.0");
                    editActual.setEnabled(false);

                    editActualdisc.setText("NO");
                    editActualdisc.setEnabled(false);


                }
                if(checkedIdRadioBut==R.id.radioButtonDriver)
                {

                    editDriver.setText("0.0");
                    editDriver.setEnabled(true);

                    editDriver.setFocusable(true);


                    busRel.setVisibility(View.GONE);
                    bikeRel.setVisibility(View.GONE);
                    driverRel.setVisibility(View.VISIBLE);
                    ActualRel.setVisibility(View.GONE);
                    ActualDisRel.setVisibility(View.GONE);


                    editBike.setText("0.0");
                    editBike.setEnabled(false);


                    editBusTrain.setText("0.0");
                    editBusTrain.setEnabled(false);

                    editActual.setText("0.0");
                    editActual.setEnabled(false);

                    editActualdisc.setText("NO");
                    editActualdisc.setEnabled(false);

                }
                if(checkedIdRadioBut==R.id.radioButtonActual)
                {
                    editActual.setText("0.0");
                    editActual.setEnabled(true);

                    editActual.setFocusable(true);

                    busRel.setVisibility(View.GONE);
                    bikeRel.setVisibility(View.GONE);
                    driverRel.setVisibility(View.GONE);
                    ActualRel.setVisibility(View.VISIBLE);
                    ActualDisRel.setVisibility(View.VISIBLE);

                    editActualdisc.setText("");
                    editActualdisc.setEnabled(true);

                    editDriver.setText("0.0");
                    editDriver.setEnabled(false);

                    editBike.setText("0.0");
                    editBike.setEnabled(false);

                    editBusTrain.setText("0.0");
                    editBusTrain.setEnabled(false);


                }
            }

        });

       /* refreshTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBike.setText("0.0");
                editDriver.setText("0.0");
                editBusTrain.setText("0.0");
                editBusTrain.setEnabled(true);
                editBike.setEnabled(true);
                editDriver.setEnabled(true);
                editBusTrain.setFocusableInTouchMode(true);
            }
        }); */
   /*     editBusTrain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editBike.setText("0.0");
                editDriver.setText("0.0");
                editBusTrain.setEnabled(true);
                editBike.setEnabled(false);
                editDriver.setEnabled(false);
                editBusTrain.setText("");
                editBusTrain.setFocusable(true);


                return false;
            }
        });*/

  /*    editBike.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editBusTrain.setText("0.0");
                editDriver.setText("0.0");
                editBike.setEnabled(true);
                editBusTrain.setEnabled(false);
                editDriver.setEnabled(false);
                editBike.setText("");
                editBike.setFocusable(true);
                return false;
            }
        });*/

    /* editDriver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editBusTrain.setText("0.0");
                editBike.setText("0.0");
                editDriver.setEnabled(true);
                editBusTrain.setEnabled(false);
                editBike.setEnabled(false);
                editDriver.setText("");
                editDriver.setFocusable(true);
                return false;
            }
        });*/


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
                    String curdate=response.body().getStardate();

                    String km= String.valueOf(response.body().getKmlimit());
                    totalKmforBike= Integer.parseInt(km);

                    String strda= String.valueOf(response.body().getDa());
                    String stroutda= String.valueOf(response.body().getOutda());
                    String strLodget= String.valueOf(response.body().getLodget());
                    String strLodged= String.valueOf(response.body().getLodged());
                    String strRoute=response.body().getRoutes();
                    String strHalts= String.valueOf(response.body().getIshalt());

                    String strPlacety= String.valueOf(response.body().getPlacetype());
                    String strPlacenm=response.body().getPlacename();

                    String strBus= String.valueOf(response.body().getBustrain());
                    String strBikes= String.valueOf(response.body().getBike());
                    String strDrivers= String.valueOf(response.body().getDriver());

                    String strOtherAmt= String.valueOf(response.body().getOther_expense_amt());

                    String strOtherReason=response.body().getOther_expense_reason();



                    if(curdate!=null)
                    {
                        String[] arrdate=curdate.split(" ");
                        date.setText(arrdate[0]);
                    }
                    name.setText(idemp);

                    totalkm.setText(km);
                    if(strPlacety.equals("1"))
                    {
                        editD.setVisibility(View.VISIBLE);
                        LDtxt.setVisibility(View.VISIBLE);
                        editD.setText(strLodged);
                        editT.setText(strLodget);
                        editT.setVisibility(View.GONE);
                        LTtxt.setVisibility(View.GONE);
                    }
                    else if(strPlacety.equals("2"))
                    {
                        LTtxt.setVisibility(View.VISIBLE);
                        editT.setVisibility(View.VISIBLE);
                        editT.setText(strLodget);
                        editD.setText(strLodged);
                        editD.setVisibility(View.GONE);
                        LDtxt.setVisibility(View.GONE);
                    }
                    if (strHalts.equals("1"))
                    {
                        textHalt.setText("YES");
                    }
                    else
                    {
                        textHalt.setText("NO");
                    }
                    textRoute.setText(strRoute);
                    editda.setText(strda);
                    editoutda.setText(stroutda);

                 /*   if (strHalts.equals("1"))
                    {
                        editT.setText(strLodget);
                        editD.setText(strLodged);
                        editT.setEnabled(true);
                        editD.setEnabled(true);

                    }
                    else
                    {
                        editT.setText(strLodget);
                        editD.setText(strLodged);
                        editT.setEnabled(false);
                        editD.setEnabled(false);
                    }*/

                    editOther.setText(strOtherAmt);
                    editRmk.setText(strOtherReason);
                    editBike.setText(strBikes);
                    editDriver.setText(strDrivers);
                    editBusTrain.setText(strBus);

                    Log.v("DA", "empda" + strda);
                    Log.v("outDA", "empoutda" + stroutda);

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

        if (radiobike.isChecked())
        {
            editBike.setText(totalKmforBike);
        }
        else
        {
            editBike.setText("0.0");
        }
        final String strname=name.getText().toString().trim();
        final String strdate=date.getText().toString().trim();
        String strRmk=editRmk.getText().toString().trim();
        doubleLodgeT = ParseDouble(editT.getText().toString().trim());
        doubleLodgeD = ParseDouble(editD.getText().toString().trim());

        doubleBusTrain=ParseDouble(editBusTrain.getText().toString().trim());
        doubleBike=ParseDouble(editBike.getText().toString().trim());
        doubleDriver=ParseDouble(editDriver.getText().toString().trim());
        doubleOther=ParseDouble(editOther.getText().toString().trim());





            if(doubleBike>0.0 && doubleDriver==0.0 && doubleBusTrain==0.0)
            {
                if (strRmk.equals("")||doubleOther==0||strname.equals("")||strdate.equals(""))
                {
                    Toast.makeText(ExpenseReportActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.v("val1", "doubleLodgeT" + doubleLodgeT);
                    Log.v("val2", "doubleLodgeD" + doubleLodgeD);
                    Log.v("val3", "strRmk" + strRmk);
                    Log.v("val4", "doubleBusTrain" + doubleBusTrain);
                    Log.v("val5", "doubleDriver" + doubleDriver);
                    Log.v("val6", "doubleOther" + doubleOther);
                    Log.v("val", "doubleBike" + doubleBike);
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<DailyEmpExpenseModel> dailyExpcall = apiInterface.insertOtherExpenseEntry("Add@OtherExpenseInEmp",employeeID,doubleLodgeT,doubleLodgeD,strRmk,doubleBusTrain,doubleDriver,doubleOther,doubleBike);
                    dailyExpcall.enqueue(new Callback<DailyEmpExpenseModel>() {
                        @Override
                        public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response)
                        {
                            String value=response.body().getValue();
                            String message=response.body().getMessage();
                            if(value.equals("1"))
                            {
                                editT.setText("");
                                editD.setText("");
                                editBike.setText("");
                                editBusTrain.setText("");
                                editDriver.setText("");
                                editOther.setText("");
                                editRmk.setText("");
                                finish();
                                Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                            else if(value.equals("0"))
                            {
                                Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                            Toast.makeText(ExpenseReportActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            else if(doubleBusTrain>0.0 && doubleBike==0.0 && doubleDriver==0.0)
            {
                if (strRmk.equals("")||doubleOther==0||strname.equals("")||strdate.equals(""))
                {
                    Toast.makeText(ExpenseReportActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.v("val1", "doubleLodgeT" + doubleLodgeT);
                    Log.v("val2", "doubleLodgeD" + doubleLodgeD);
                    Log.v("val3", "strRmk" + strRmk);
                    Log.v("val4", "doubleBusTrain" + doubleBusTrain);
                    Log.v("val5", "doubleDriver" + doubleDriver);
                    Log.v("val6", "doubleOther" + doubleOther);
                    Log.v("val", "doubleBike" + doubleBike);
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<DailyEmpExpenseModel> dailyExpcall = apiInterface.insertOtherExpenseEntry("Add@OtherExpenseInEmp",employeeID,doubleLodgeT,doubleLodgeD,strRmk,doubleBusTrain,doubleDriver,doubleOther,doubleBike);
                    dailyExpcall.enqueue(new Callback<DailyEmpExpenseModel>() {
                        @Override
                        public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response)
                        {
                            String value=response.body().getValue();
                            String message=response.body().getMessage();
                            if(value.equals("1"))
                            {
                                editT.setText("");
                                editD.setText("");
                                editBike.setText("");
                                editBusTrain.setText("");
                                editDriver.setText("");
                                editOther.setText("");
                                editRmk.setText("");
                                finish();
                                Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();

                            }
                            else if(value.equals("0"))
                            {
                                Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                            Toast.makeText(ExpenseReportActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            else if(doubleDriver>0.0 && doubleBike==0.0 && doubleBusTrain==0.0)
            {

                if (strRmk.equals("")||doubleOther==0||strname.equals("")||strdate.equals(""))
                {
                    Toast.makeText(ExpenseReportActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.v("val1", "doubleLodgeT" + doubleLodgeT);
                    Log.v("val2", "doubleLodgeD" + doubleLodgeD);
                    Log.v("val3", "strRmk" + strRmk);
                    Log.v("val4", "doubleBusTrain" + doubleBusTrain);
                    Log.v("val5", "doubleDriver" + doubleDriver);
                    Log.v("val6", "doubleOther" + doubleOther);
                    Log.v("val", "doubleBike" + doubleBike);
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<DailyEmpExpenseModel> dailyExpcall = apiInterface.insertOtherExpenseEntry("Add@OtherExpenseInEmp",employeeID,doubleLodgeT,doubleLodgeD,strRmk,doubleBusTrain,doubleDriver,doubleOther,doubleBike);
                    dailyExpcall.enqueue(new Callback<DailyEmpExpenseModel>() {
                        @Override
                        public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response)
                        {
                            String value=response.body().getValue();
                            String message=response.body().getMessage();
                            if(value.equals("1"))
                            {
                                editT.setText("");
                                editD.setText("");
                                editBike.setText("");
                                editBusTrain.setText("");
                                editDriver.setText("");
                                editOther.setText("");
                                editRmk.setText("");
                                finish();
                                Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();

                            }
                            else if(value.equals("0"))
                            {
                                Toast.makeText(ExpenseReportActivity.this,message,Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                            Toast.makeText(ExpenseReportActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            else
            {
                Toast.makeText(ExpenseReportActivity.this,"Enter Bus/Train,Bike,Driver Any One Field Amount",Toast.LENGTH_LONG).show();
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
