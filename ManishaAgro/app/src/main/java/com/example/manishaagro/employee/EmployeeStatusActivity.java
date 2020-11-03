package com.example.manishaagro.employee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ImageLoad;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.model.VisitProductMapModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeStatusActivity extends AppCompatActivity {
    public ApiInterface apiInterface;

    ListView listViewProduct;
    private List<VisitProductMapModel> productListListview;
    Toolbar visitDemoDetailsToolbar;
    String employeeID = "", name = "", dateOfTravel = "", dateOfReturn = "";
    RelativeLayout followUpDateRelativeLayout;
    CardView demoDetailsCard,productListcard;
    TextView textName, textadd, textvillage, texttaluka, textDistrict, textContact;
    TextView textdName, textdType, textsCropHealth, textsUsages, textsProdtName, textsPacking, textsProdtQtys, textsWaterQtys, textsFollowReq, textsFollowdates;
    TextView textsCropsAb, textsAddiAb, startDateText, endDateText;
    ImageView visitedDetailDemoPhoto, visitedDetailDemoSelfies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_status);

        visitDemoDetailsToolbar = findViewById(R.id.toolbarVisitedDetailDemo);
        productListcard=findViewById(R.id.cardViewListViewProduct);
        setSupportActionBar(visitDemoDetailsToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        listViewProduct=findViewById(R.id.listView1);
        demoDetailsCard = findViewById(R.id.cardViewdemoVisitedDetails);
        followUpDateRelativeLayout = findViewById(R.id.FollowDateReqRel);
        textName = findViewById(R.id.custVisitedname);
        textadd = findViewById(R.id.CustVisitedAddresss);
        textvillage = findViewById(R.id.Textvillage);
        texttaluka = findViewById(R.id.Texttaluka);
        textDistrict = findViewById(R.id.TxtsDistrict);
        textContact = findViewById(R.id.contactVisited);
        textdName = findViewById(R.id.TextdName);
        textdType = findViewById(R.id.TextdType);
        textsCropHealth = findViewById(R.id.TxtCrpssHealth);
        textsUsages = findViewById(R.id.TextsUsagesTys);
   //     textsProdtName = findViewById(R.id.TextsProdtsNames);
     //   textsPacking = findViewById(R.id.TextsProdtsPACKGS);
      //  textsProdtQtys = findViewById(R.id.TextsProdtsQTYss);
        textsWaterQtys = findViewById(R.id.TextsWatersQTYss);
        textsFollowReq = findViewById(R.id.textsFollowsDemosYNs);
        textsFollowdates = findViewById(R.id.textsFollowsDemosDTsYNs);
        textsCropsAb = findViewById(R.id.Textdcropss);
        textsAddiAb = findViewById(R.id.TextsdAdditionss);
        visitedDetailDemoPhoto = findViewById(R.id.visitedDetailsDemoPhoto);
        visitedDetailDemoSelfies = findViewById(R.id.visitedDetailsDemoSelfie);
        startDateText = findViewById(R.id.TextstartsDatess);
        endDateText = findViewById(R.id.TextendsDates);


        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpStsVal");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals(STATUS_EMPLOYEE_VISITED_CUSTOMER)) {
            name = intent.getStringExtra(STATUS_VISITED_CUSTOMER_NAME);
            dateOfTravel = intent.getStringExtra(STATUS_DATE_OF_TRAVEL);
            dateOfReturn = intent.getStringExtra(STATUS_DATE_OF_RETURN);
            employeeID = intent.getStringExtra("EMPLOYEE_ID_STATUS");

        }
        getVisitedCustomerDetailsofStatusFrg();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVisitedCustomerDetailsofStatusFrg();
    }

    private void getVisitedCustomerDetailsofStatusFrg() {
        Log.v("yek", "empid" + employeeID);
        Log.v("yek", "name" + name);
        Log.v("yek", "dateoftravel" + dateOfTravel);
        Log.v("yek", "dateofreturn" + dateOfReturn);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> visitedDetailsCalls = apiInterface.getCustomerVisitedDetailsOfEmp("Get@allDetailsOfVisitedCust", employeeID, name, dateOfTravel, dateOfReturn);
        visitedDetailsCalls.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                int visitId = response.body().getVisitid();
                String visitDetailName = response.body().getVisitedCustomerName();
                String visitDetailAddress = response.body().getAddress();
                String visitDetailVillage = response.body().getVillage();
                String visitDetailTaluka = response.body().getTaluka();
                String visitDetailDistrict = response.body().getDistrict();
                String visitDetailContact = response.body().getContactdetail();
                String visitDetailDemoName = response.body().getDemoname();
                String visitDetailDemoType = response.body().getDemotype();
                String visitDetailHealth = response.body().getCrophealth();
                String visitDetailUsage = response.body().getUsagetype();

                String visitDetailWaterQty = response.body().getWaterquantity();
                String visitDetailFollowupReq = String.valueOf(response.body().getFollowuprequired());
                String visitDetailDemoReq = String.valueOf(response.body().getDemorequired());

                String visitDetailAboutCrops = response.body().getCrops();
                String visitDetailAdditions = response.body().getAdditions();
                String visitDetailPhoto = response.body().getDemoimage();
                String visitDetailSelfie = response.body().getSelfiewithcustomer();

                String visitDetailFollowupDate = response.body().getFollowupdate();
                String[] visitDetailFollowupDate1 = (visitDetailFollowupDate != null) ?
                        visitDetailFollowupDate.split(" ") : null;

                String visitDetailStartDate = response.body().getDateOfTravel();
                String[] visitDetailStartDate1 = (visitDetailStartDate != null) ? visitDetailStartDate.split(" ") : new String[0];
                String visitDetailEndDate = response.body().getDateOfReturn();
                String[] visitDetailEndDate1 = (visitDetailEndDate != null) ? visitDetailEndDate.split(" ") : new String[0];

                if (value.equals("1")) {

                    if (visitDetailFollowupReq.equals("1")) {
                        followUpDateRelativeLayout.setVisibility(View.VISIBLE);
                        textsFollowReq.setText(R.string.yes_alert_button);
                        textsFollowdates.setText(visitDetailFollowupDate1 != null ? visitDetailFollowupDate1[0] : "0");
                    } else if (visitDetailFollowupReq.equals("0")) {
                        textsFollowReq.setText(R.string.no_alert_button);
                        followUpDateRelativeLayout.setVisibility(View.INVISIBLE);
                        textsFollowdates.setText("");

                    }
                    if (visitDetailDemoReq.equals("1")) {
                        demoDetailsCard.setVisibility(View.VISIBLE);

                    } else if (visitDetailDemoReq.equals("0")) {
                        demoDetailsCard.setVisibility(View.GONE);
                    }

                    textName.setText(visitDetailName);
                    textadd.setText(visitDetailAddress);
                    textvillage.setText(visitDetailVillage);
                    texttaluka.setText(visitDetailTaluka);
                    textDistrict.setText(visitDetailDistrict);
                    textContact.setText(visitDetailContact);
                    textdName.setText(visitDetailDemoName);
                    textdType.setText(visitDetailDemoType);
                    textsCropHealth.setText(visitDetailHealth);
                    textsUsages.setText(visitDetailUsage);

                    textsWaterQtys.setText(visitDetailWaterQty);

                    textsCropsAb.setText(visitDetailAboutCrops);
                    textsAddiAb.setText(visitDetailAdditions);

                    startDateText.setText(visitDetailStartDate1[0]);
                    endDateText.setText(visitDetailEndDate1[0]);

//get all product list using visit id
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<List<VisitProductMapModel>> visitedProductsDetailsCalls = apiInterface.GetProductofVisitedID("Get@allProductDetailsOfVisitid", visitId);
                   visitedProductsDetailsCalls.enqueue(new Callback<List<VisitProductMapModel>>() {
                       @Override
                       public void onResponse(Call<List<VisitProductMapModel>> call, Response<List<VisitProductMapModel>> response) {

                            String res= String.valueOf(response.body());
                            if (res.equals(null))
                            {
                                productListcard.setVisibility(View.GONE);
                            }
                            else
                            {
                                productListcard.setVisibility(View.VISIBLE);
                                productListListview= response.body();
                                listViewProduct.setAdapter(new ProductListViewAdapter(getApplicationContext(),productListListview));

                             //   Toast.makeText(EmployeeStatusActivity.this, "message", Toast.LENGTH_LONG).show();
                            }


                       }

                       @Override
                       public void onFailure(Call<List<VisitProductMapModel>> call, Throwable t) {

                       }
                   });




                    if (visitDetailPhoto != null) {

                       // visitDetailPhotoBitmap
                        new ImageLoad(visitDetailPhoto,visitedDetailDemoPhoto).execute();
                       // visitedDetailDemoPhoto.setImageBitmap(imageLoad);


                    } else
                        {
                        visitedDetailDemoPhoto.setImageResource(R.drawable.photo_not_found);
                    }
                    if (visitDetailSelfie != null) {
                        new ImageLoad(visitDetailSelfie,visitedDetailDemoSelfies).execute();
                     //   visitedDetailDemoSelfies.setImageBitmap(getBitmapFromURL(visitDetailSelfie));
                    }
                    else {


                        visitedDetailDemoSelfies.setImageResource(R.drawable.photo_not_found);
                    }

                } else if (value.equals("0")) {
                    Toast.makeText(EmployeeStatusActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                Toast.makeText(EmployeeStatusActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
