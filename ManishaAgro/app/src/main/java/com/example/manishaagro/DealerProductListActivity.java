package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.employee.DealerAdapterInEmp;
import com.example.manishaagro.employee.EmployeeStatusActivity;
import com.example.manishaagro.employee.ProductListViewAdapter;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.DealerProductMap;
import com.example.manishaagro.model.VisitProductMapModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class DealerProductListActivity extends AppCompatActivity {
    public ApiInterface apiInterface;
    ConnectionDetector connectionDetector;
    ListView listViewProduct;
    private List<DealerProductMap> productListListview;
    Toolbar DealerProductDetailsToolbar;
    String employeeID = "";
    String dealeName = "";
    String productDateOfPur = "";
    int dealer_id = 0;

    TextView TxtDealeName,TxtDealerProductPurDate,TxtProductCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_product_list);
        connectionDetector=new ConnectionDetector();
        DealerProductDetailsToolbar = findViewById(R.id.toolbarDealerProductDetail);

        setSupportActionBar(DealerProductDetailsToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        listViewProduct=findViewById(R.id.DealerProductDetailListview);
        TxtDealeName=findViewById(R.id.textDealerName);
        TxtDealerProductPurDate=findViewById(R.id.textDealerDateofPur);
        TxtProductCount=findViewById(R.id.textDealerProductCnt);

        Intent intent = getIntent();
        String keyForCompareDealerProductDetail = intent.getStringExtra("Emp_dealerProductVal");
        Log.v("yek", "keyyy" + keyForCompareDealerProductDetail);
        if (keyForCompareDealerProductDetail != null && keyForCompareDealerProductDetail.equals("Emp_Dealer_Product_List_Status")) {
            dealeName = intent.getStringExtra("emp_dealer_name");
            productDateOfPur = intent.getStringExtra("emp_dealer_product_purchase_date");
            employeeID = intent.getStringExtra("EmpId_Dealer_product");

        }
        if (connectionDetector.isConnected(DealerProductListActivity.this))
        {
            getDealerIdCount();
        }
        else
        {
            Toast.makeText(DealerProductListActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }


    }


    private void getDealerIdCount() {
        final String STEmp_ID1 = employeeID;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeID);
        Call<DealerModel> listCall = apiInterface.getAllDealerIdInEmp("get@llDealerIdInEmp", STEmp_ID1,dealeName,productDateOfPur);
        listCall.enqueue(new Callback<DealerModel>() {
            @Override
            public void onResponse(@NonNull Call<DealerModel> call, @NonNull Response<DealerModel> response) {

                String value=response.body().getValue();
                String message=response.body().getMessage();
                int productCnts=response.body().getProductcount();
                dealer_id=response.body().getDealer_id();

                if(value.equals("1"))
                {
                    TxtDealeName.setText(dealeName);
                    String[] productDateOfPur1=productDateOfPur.split(" ");
                    TxtDealerProductPurDate.setText(productDateOfPur1[0]);
                    TxtProductCount.setText(String.valueOf(productCnts));
                  if(dealer_id==0)
                  {
                      Toast.makeText(DealerProductListActivity.this, "DATA NOT FOUND", Toast.LENGTH_LONG).show();

                  }
                  else
                  {
                      apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                      Call<List<DealerProductMap>> dealerProductsDetailsCalls = apiInterface.GetAllProductofDealerID("Get@allProductDetailsOfDealerid", dealer_id);
                     dealerProductsDetailsCalls.enqueue(new Callback<List<DealerProductMap>>() {
                         @Override
                         public void onResponse(Call<List<DealerProductMap>> call, Response<List<DealerProductMap>> response) {


                             String res= String.valueOf(response.body());
                             if (res.equals(null))
                             {
                                 Toast.makeText(DealerProductListActivity.this, "DATA NOT FOUND", Toast.LENGTH_LONG).show();
                             }
                             else
                             {

                                 productListListview= response.body();
                                 listViewProduct.setAdapter(new ProductListViewAdapterDealer(getApplicationContext(),productListListview));

                              //   Toast.makeText(DealerProductListActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();

                             }


                         }

                         @Override
                         public void onFailure(Call<List<DealerProductMap>> call, Throwable t) {

                             if (connectionDetector.isConnected(DealerProductListActivity.this))
                             {
                                 Toast.makeText(DealerProductListActivity.this, "DATA NOT FOUND", Toast.LENGTH_LONG).show();

                             }
                             else
                             {
                                 Toast.makeText(DealerProductListActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                             }
                         }
                     });

                  }



                }
                else if(value.equals("0"))
                {
                    Toast.makeText(DealerProductListActivity.this,message,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<DealerModel> call, @NonNull Throwable t) {

                if (connectionDetector.isConnected(DealerProductListActivity.this))
                {
                    Toast.makeText(DealerProductListActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(DealerProductListActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
