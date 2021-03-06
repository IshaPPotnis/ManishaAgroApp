package com.example.manishaagro.employee;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.BuildConfig;
import com.example.manishaagro.ImageLoad;
import com.example.manishaagro.MessageDialog;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.model.VisitProductMapModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeStatusActivity extends AppCompatActivity{
    public ApiInterface apiInterface;
    MessageDialog messageDialog;
    static final int CPAPTURE_PDF_REQUEST=111;
    private LinearLayout llScroll;
    private Bitmap bitmapPdf;
    String savepdffile;
    RelativeLayout badResonRelative;
    File pdffiles=null;
    Uri pdfURI=null;
    ListView listViewProduct;
    private List<VisitProductMapModel> productListListview;
    Toolbar visitDemoDetailsToolbar;
    String employeeID = "", name = "", dateOfTravel = "", dateOfReturn = "";
    String username="";
    RelativeLayout followUpDateRelativeLayout;
    CardView demoDetailsCard,productListcard;
    TextView textName, textadd, textvillage, texttaluka, textDistrict, textContact;
    TextView textdName, textdType, textsCropHealth, textsUsages, textsProdtName, textsPacking, textsProdtQtys, textsWaterQtys,txtaddWater,textsFollowReq, textsFollowdates;
    TextView textsCropsAb, textsAddiAb, startDateText, endDateText;
    ImageView visitedDetailDemoPhoto, visitedDetailDemoSelfies;
    TextView textShowAcre,textShowPurpose;
    TextView badReasontext;
    TextView txtCropGrowth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_status);
        messageDialog=new MessageDialog();
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
        txtCropGrowth=findViewById(R.id.TextcropGrowths);
        badResonRelative=findViewById(R.id.MainRelBadReson);
        badReasontext=findViewById(R.id.TextsBadReasonshow);
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
        txtaddWater=findViewById(R.id.TextsWatersQTYssAdd);


        textShowAcre=findViewById(R.id.TextVisitStarAcre);
        textShowPurpose=findViewById(R.id.TextvisitPurposeDetail);
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
        llScroll=findViewById(R.id.llscroll);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pdfbuttton, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.pdfbutxml)
        {

            Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
            bitmapPdf = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
            createPdf();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

                String visitDetailAcre = String.valueOf(response.body().getAcre());
                String visitDetailPurpose = response.body().getVisitpurpose();

                String visitDetailDemoName = response.body().getDemoname();
                String visitDetailDemoType = response.body().getDemotype();
                String visitDetailHealth = response.body().getCrophealth();
                String visitDetailUsage = response.body().getUsagetype();
                String visitDetailHealthBad=response.body().getHealth_bad_reason();
                String visitDetailWaterQty = response.body().getWaterquantity();
                String visitDetailWaterAddsQty=response.body().getWateradditions();
                String visitDetailFollowupReq = String.valueOf(response.body().getFollowuprequired());
                String visitDetailDemoReq = String.valueOf(response.body().getDemorequired());


                String visitDetailAboutCrops = response.body().getCrops();
                String visitDetailAdditions = response.body().getAdditions();
                String visitCropGrowth=response.body().getCrop_growth();
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
                    textShowAcre.setText(visitDetailAcre);
                    textShowPurpose.setText(visitDetailPurpose);
                    textdName.setText(visitDetailDemoName);
                    textdType.setText(visitDetailDemoType);
                    textsCropHealth.setText(visitDetailHealth);

                    if(visitDetailHealth==null)
                    {

                    }
                    else
                    {
                        if(visitDetailHealth.equals("BAD"))
                        {
                            badResonRelative.setVisibility(View.VISIBLE);
                            badReasontext.setText(visitDetailHealthBad);
                        }
                        else if(visitDetailHealth.equals(""))
                        {
                            badResonRelative.setVisibility(View.GONE);
                        }
                        else
                        {
                            badResonRelative.setVisibility(View.GONE);
                        }
                    }


                    textsUsages.setText(visitDetailUsage);

                    textsWaterQtys.setText(visitDetailWaterQty);
                    txtaddWater.setText(visitDetailWaterAddsQty);

                    textsCropsAb.setText(visitDetailAboutCrops);
                    textsAddiAb.setText(visitDetailAdditions);
                    txtCropGrowth.setText(visitCropGrowth);

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
                    //Toast.makeText(EmployeeStatusActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
               messageDialog.msgDialog(EmployeeStatusActivity.this);
                // Toast.makeText(EmployeeStatusActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
            }
        });
    }



    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
        }
        PdfDocument.PageInfo pageInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        }
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmapPdf = Bitmap.createScaledBitmap(bitmapPdf, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmapPdf, 0, 0 , null);
        document.finishPage(page);



        username=name+"_"+dateOfTravel+"_"+employeeID;
        File storageDir = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }
        else
        {
            storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }

        File pdffilenm=null;
        String linkurl=storageDir + "/" + username+".pdf";
        pdffilenm=new File(linkurl);
        try {

            document.writeTo(new FileOutputStream(pdffilenm));

        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }


        Intent takePdfIntent = new Intent(MediaStore.EXTRA_OUTPUT);
        if(takePdfIntent.resolveActivity(getPackageManager())!=null)
        {
                pdffiles=pdffiles.getAbsoluteFile();
                // Continue only if the File was successfully created
                    if (pdffiles != null) {
                        pdfURI = FileProvider.getUriForFile(this,
                                "com.example.manishaagro.fileprovider",
                                pdffiles);

                        takePdfIntent.putExtra(MediaStore.EXTRA_OUTPUT, pdfURI);
                        startActivityForResult(takePdfIntent, CPAPTURE_PDF_REQUEST);
                    }

            }




        // close the document
        document.close();
        Toast.makeText(this, username + " PDF created!!!", Toast.LENGTH_SHORT).show();

        String storage= String.valueOf(storageDir);
       openGeneratedPDF(username,storage);

    }


   private void openGeneratedPDF(String filenm,String dris){


        String strpaths = dris + "/" +filenm+".pdf";

        File newfile=new File(strpaths);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uriintent=FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName()+".fileprovider",newfile);

        intent.setDataAndType(uriintent, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(EmployeeStatusActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }

    }

}
