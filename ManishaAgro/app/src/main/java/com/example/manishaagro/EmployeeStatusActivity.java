package com.example.manishaagro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_PROFILE;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeStatusActivity extends AppCompatActivity {
    public ApiInterface apiInterface;
    Toolbar visitDemoDetailsToolbar;
    String employeeID="",name="",dateOfTravel="",dateOfReturn="";
    RelativeLayout followpdatRel;

    TextView textName,textadd,textvillage,texttaluka,textDistrict,textContact;
    TextView textdName,textdType,textsCropHealth,textsUsages,textsProdtName,textsPacking,textsProdtQtys,textsWaterQtys,textsFollowReq,textsFollowdates;
    TextView textsCropsAb,textsAddiAb,startDateText,endDateText;
    ImageView visitedDetailDemoPhoto,visitedDetailDemoSelfies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_status);

        visitDemoDetailsToolbar = findViewById(R.id.toolbarVisitedDetailDemo);
        setSupportActionBar(visitDemoDetailsToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        followpdatRel=findViewById(R.id.FollowDateReqRel);
        textName =findViewById(R.id.custVisitedname);
        textadd=findViewById(R.id.CustVisitedAddresss);
        textvillage=findViewById(R.id.Textvillage);
        texttaluka=findViewById(R.id.Texttaluka);
        textDistrict=findViewById(R.id.TxtsDistrict);
        textContact=findViewById(R.id.contactVisited);
        textdName=findViewById(R.id.TextdName);
        textdType=findViewById(R.id.TextdType);
        textsCropHealth=findViewById(R.id.TxtCrpssHealth);
        textsUsages=findViewById(R.id.TextsUsagesTys);
        textsProdtName=findViewById(R.id.TextsProdtsNames);
        textsPacking=findViewById(R.id.TextsProdtsPACKGS);
        textsProdtQtys=findViewById(R.id.TextsProdtsQTYss);
        textsWaterQtys=findViewById(R.id.TextsWatersQTYss);
        textsFollowReq=findViewById(R.id.textsFollowsDemosYNs);
        textsFollowdates=findViewById(R.id.textsFollowsDemosDTsYNs);
        textsCropsAb=findViewById(R.id.Textdcropss);
        textsAddiAb=findViewById(R.id.TextsdAdditionss);
        visitedDetailDemoPhoto=findViewById(R.id.visitedDetailsDemoPhoto);
        visitedDetailDemoSelfies=findViewById(R.id.visitedDetailsDemoSelfie);
        startDateText=findViewById(R.id.TextstartsDatess);
        endDateText=findViewById(R.id.TextendsDates);


        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpStsVal");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals(STATUS_EMPLOYEE_VISITED_CUSTOMER)) {
             name = intent.getStringExtra(STATUS_VISITED_CUSTOMER_NAME);
             dateOfTravel = intent.getStringExtra(STATUS_DATE_OF_TRAVEL);
             dateOfReturn = intent.getStringExtra(STATUS_DATE_OF_RETURN);
            employeeID=intent.getStringExtra("EMPLOYEE_ID_STATUS");

        }
        getVisitedCustomerDetailsofStatusFrg();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVisitedCustomerDetailsofStatusFrg();
    }
    private void getVisitedCustomerDetailsofStatusFrg()
    {
        Log.v("yek", "empid" + employeeID);
        Log.v("yek", "name" + name);
        Log.v("yek", "dateoftravel" + dateOfTravel);
        Log.v("yek", "dateofreturn" + dateOfReturn);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> visitedDetailsCalls = apiInterface.getCustomerVisitedDetailsOfEmp("Get@allDetailsOfVisitedCust", employeeID,name,dateOfTravel,dateOfReturn);
        visitedDetailsCalls.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                String visitdetailName=response.body().getVisitedCustomerName();
                String visitdetailAdd=response.body().getAddress();
                String visitdetailvillage=response.body().getVillage();
                String visitdetailTaluka=response.body().getTaluka();
                String visitdetaildistrict=response.body().getDistrict();
                String visitdetailContact=response.body().getContactdetail();
                String visitdetailDemonms=response.body().getDemoname();
                String visitdetailDemoTyps=response.body().getDemotype();
                String visitdetailHealth=response.body().getCrophealth();
                String visitdetailuasage=response.body().getUsagetype();
                String visitdetailPeoductnm=response.body().getProductname();
                String visitdetailpacking=response.body().getPacking();
                String visitdetailprodtQTY=response.body().getProductquantity();
                String visitdetailWaterQty=response.body().getWaterquantity();
                String visitdetailFollowupReq= String.valueOf(response.body().getFollowuprequired());

                String visitdetailcropsAbt=response.body().getCrops();
                String visitdetailAdditions=response.body().getAdditions();
                String visitdetailPhoto=response.body().getDemoimage();
                String visitdetailSelfie=response.body().getSelfiewithcustomer();

                String visitdetailFollowupDate=response.body().getFollowupdate();
                String[] visitdetailFollowupDate1=visitdetailFollowupDate.split(" ");

                String visitdetailStartDate=response.body().getDateOfTravel();
                String[] visitdetailStartDate1=visitdetailStartDate.split(" ");
                String visitdetailEndDate=response.body().getDateOfReturn();
                String[] visitdetailEndDate1=visitdetailEndDate.split(" ");


                if (value.equals("1"))
                {

                    if (visitdetailFollowupReq.equals("1"))
                    {followpdatRel.setVisibility(View.VISIBLE);
                        textsFollowReq.setText("YES");
                        textsFollowdates.setText(visitdetailFollowupDate1[0]);
                    }
                    else if(visitdetailFollowupReq.equals("0"))
                    {
                        textsFollowReq.setText("NO");
                        followpdatRel.setVisibility(View.INVISIBLE);
                        textsFollowdates.setText("");

                    }

                   textName.setText(visitdetailName);
                    textadd.setText(visitdetailAdd);
                    textvillage.setText(visitdetailvillage);
                    texttaluka.setText(visitdetailTaluka);
                    textDistrict.setText(visitdetaildistrict);
                    textContact.setText(visitdetailContact);
                    textdName.setText(visitdetailDemonms);
                    textdType.setText(visitdetailDemoTyps);
                    textsCropHealth.setText(visitdetailHealth);
                    textsUsages.setText(visitdetailuasage);
                    textsProdtName.setText(visitdetailPeoductnm);
                    textsPacking.setText(visitdetailpacking);
                    textsProdtQtys.setText(visitdetailprodtQTY);
                    textsWaterQtys.setText(visitdetailWaterQty);

                    textsCropsAb.setText(visitdetailcropsAbt);
                    textsAddiAb.setText(visitdetailAdditions);

                    startDateText.setText(visitdetailStartDate1[0]);
                    endDateText.setText(visitdetailEndDate1[0]);

                    Picasso.with(EmployeeStatusActivity.this).load(visitdetailPhoto).into(visitedDetailDemoPhoto);
                    Picasso.with(EmployeeStatusActivity.this).load(visitdetailSelfie).into( visitedDetailDemoSelfies);


                }
                else if(value.equals("0"))
                {
                    Toast.makeText(EmployeeStatusActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                Toast.makeText(EmployeeStatusActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
