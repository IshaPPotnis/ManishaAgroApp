package com.example.manishaagro;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.manishaagro.employee.CustomerVisitStartActivity;
import com.example.manishaagro.employee.ProductActivity;
import com.example.manishaagro.model.ProductModel;
import com.example.manishaagro.model.ProfileModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.DBHelper.COLUMN_ADDRESS;
import static com.example.manishaagro.DBHelper.COLUMN_CONTACT_DETAIL;
import static com.example.manishaagro.DBHelper.COLUMN_DESIGNATION;
import static com.example.manishaagro.DBHelper.COLUMN_DOB;
import static com.example.manishaagro.DBHelper.COLUMN_DOJ;
import static com.example.manishaagro.DBHelper.COLUMN_EMAIL_ID;
import static com.example.manishaagro.DBHelper.COLUMN_EMPI_ID;
import static com.example.manishaagro.DBHelper.COLUMN_HEADQUARTER;
import static com.example.manishaagro.DBHelper.COLUMN_IS_ACTIVE;
import static com.example.manishaagro.DBHelper.COLUMN_NAME;
import static com.example.manishaagro.DBHelper.COLUMN_PASSWORD;
import static com.example.manishaagro.DBHelper.COLUMN_USERNAME;
import static com.example.manishaagro.DBHelper.EMPLOYEE_DETAILS;
import static com.example.manishaagro.DBHelper.PRODUCT_DETAILS;

public class MainActivity extends AppCompatActivity {
    private int RequestPermissionCode  = 3090;
    static final int STORAGE_REQUEST=1110;

    MessageDialog messageDialog;
    ConnectionDetector connectionDetector;
    public int empcount=0;
    public int totalsizeArrList=0;
    ApiInterface apiInterface;
    DBHelper dbHelpers;
    SQLiteDatabase db2;
    Button exp;
    public ArrayList<ProfileModel> ProfileData = new ArrayList<>();
    public ArrayList<String> profileList = new ArrayList<>();

    public ArrayList<ProductModel> ProductTableData = new ArrayList<>();
    public ArrayList<String> productTableList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelpers = new DBHelper(this);
        messageDialog=new MessageDialog();
        exp = findViewById(R.id.export);
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // importData();
              /*  if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                { Toast.makeText(MainActivity.this,"Already Permission Granted",Toast.LENGTH_SHORT).show();}
                else
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                       new AlertDialog.Builder(MainActivity.this)
                               .setTitle("Permission Needed")
                               .setMessage("Storage Permission")
                               .setPositiveButton("", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               })
                               .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       dialog.dismiss();
                                   }
                               })
                               .create().show();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},RequestPermissionCode);
                    }
                }*/
                getAllTableDataEmpDetails();



            }
        });
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RequestPermissionCode)
        {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }*/

   private void getAllProductDetailTable()
   {
       empcount=0;
       totalsizeArrList=0;

       apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
       Call<ArrayList<ProductModel>> callListProducttable = apiInterface.getallProductDetailTableList("getProductDetailsTables@meData");
       callListProducttable.enqueue(new Callback<ArrayList<ProductModel>>() {
           @Override
           public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
               if(response.body() != null)
               {
                   ProductTableData.clear();
                   ProductTableData.addAll(response.body());

                   db2 = dbHelpers.getWritableDatabase();
                   db2.execSQL("delete from " + PRODUCT_DETAILS);

                   Log.v("ProductDetailscheck1", "prodt1" + ProfileData);
                   productTableList = new ArrayList<>();
                   totalsizeArrList=ProductTableData.size();
                   Log.v("totalsizeArrListProdt", "prodt2" + totalsizeArrList);
                   for (int i = 0; i < ProductTableData.size(); i++)
                   {
                       int isstrTbleProductid=ProductTableData.get(i).getProductId();

                       String strprodtTbleProdtName=ProductTableData.get(i).getProductName();
                       String strprodtTbleQtyUse=ProductTableData.get(i).getQuantityused();
                       String strprodtTblePacking=ProductTableData.get(i).getPacking();

                       System.out.println("Check profl data" + isstrTbleProductid+","+strprodtTbleProdtName+","+strprodtTbleQtyUse+","+strprodtTblePacking+"\n");
                       boolean Inserted = dbHelpers.addProdtTableDeatilsdata(isstrTbleProductid,strprodtTbleProdtName,strprodtTbleQtyUse,strprodtTblePacking);

                       if (Inserted == true) {
                           //  Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                           empcount=empcount+1;

                       }
                       else
                       {
                           Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                       }


                   }

                  if (totalsizeArrList==empcount)
                   {

                       // Toast.makeText(MainActivity.this,"Table Updated",Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(MainActivity.this, LoginActivity.class);
                       startActivity(i);
                       finish();


                   }
                   else
                   {
                       Toast.makeText(MainActivity.this, "Again Start App", Toast.LENGTH_SHORT).show();
                   }

               }
               else
               {
                   Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
               Intent i = new Intent(MainActivity.this, LoginActivity.class);
               startActivity(i);
               finish();

           }
       });
   }
    private void getAllTableDataEmpDetails()
    {
          empcount=0;
          totalsizeArrList=0;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<ProfileModel>> callListtable = apiInterface.getEmpDetailTableList("getEmpDetail@meTableData");
        callListtable.enqueue(new Callback<ArrayList<ProfileModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProfileModel>> call, Response<ArrayList<ProfileModel>> response) {

              if(response.body() != null)
                {
                  //  int userEmpCounts =  dbHelpers.dbEmpDtlTblCount();


                        ProfileData.clear();
                        ProfileData.addAll(response.body());

                        db2 = dbHelpers.getWritableDatabase();
                        db2.execSQL("delete from " + EMPLOYEE_DETAILS);


                        Log.v("Profilearraycheck1", "profile1" + ProfileData);
                        profileList = new ArrayList<>();
                        totalsizeArrList=ProfileData.size();
                        Log.v("totalsizeArrList", "profile2" + totalsizeArrList);
                        for (int i = 0; i < ProfileData.size(); i++) {
                            String strEmpid=ProfileData.get(i).getEmpId();
                            String strEmpuserName=ProfileData.get(i).getUsername();
                            String strEmpPass=ProfileData.get(i).getPassword();
                            String strEmpNames=ProfileData.get(i).getName();
                            String strEmpDesignations=ProfileData.get(i).getDesignation();
                            String strEmpDob=ProfileData.get(i).getDob();
                            String strEmpJod=ProfileData.get(i).getJoiningDate();
                            String strEmpEml=ProfileData.get(i).getEmail();
                            String strEmpCondtl=ProfileData.get(i).getContactDetails();
                            String strEmpAdd=ProfileData.get(i).getAddress();
                            String strEmpHead=ProfileData.get(i).getHeadquarter();
                            int isAct=ProfileData.get(i).getIsactive();

                            System.out.println("Check profl data" + strEmpid+","+strEmpuserName+","+strEmpPass+","+strEmpNames+","+strEmpDesignations+","+strEmpDob+","+strEmpJod+","+strEmpEml+","+strEmpCondtl+","+strEmpAdd+","+strEmpHead+","+isAct+"\n");
                            boolean Inserted = dbHelpers.addEmpDeatilsdata(strEmpid,strEmpuserName,strEmpPass,strEmpNames,strEmpDesignations,strEmpDob,strEmpJod,strEmpEml,strEmpCondtl,strEmpAdd,strEmpHead,isAct);
                            if (Inserted == true) {
                              //  Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                empcount=empcount+1;

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            }

                        }

                        if (totalsizeArrList==empcount)
                        {

                           // Toast.makeText(MainActivity.this,"Table Updated",Toast.LENGTH_SHORT).show();
                           //12/26/2020 runnable@ getAllProductDetailTable();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Again Start App", Toast.LENGTH_SHORT).show();
                        }





                }
              else
                {
                    Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ProfileModel>> call, Throwable t) {
               // messageDialog.msgDialog(MainActivity.this);
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


  /*  private void importData() {
        db2 = dbHelpers.getWritableDatabase();
        db2.execSQL("delete from " + EMPLOYEE_DETAILS);
        InputStream opStream;
        try {
            BufferedReader buffer;
            opStream = getApplicationContext().getAssets().open("employee_detail.csv");
            buffer = new BufferedReader(new InputStreamReader(opStream));
            String line;
            db2.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length != 12) {
                        Log.d("CSVParser", "Skipping Bad CSV Row");
                        continue;
                    }
                    ContentValues cv = new ContentValues(50);
                    String strempid=columns[0].trim();
                    String Strempid1=strempid.replace("\"","");
                    cv.put(COLUMN_EMPI_ID, Strempid1);
                    // Log.v("resul3","res3"+(Col1, columns[0].trim()));

                    String strusername=columns[1].trim();
                    String Strusername=strusername.replace("\"","");
                    cv.put(COLUMN_USERNAME,Strusername);

                    String strpasss=columns[2].trim();
                    String strPass1=strpasss.replace("\"","");
                    cv.put(COLUMN_PASSWORD,strPass1);

                    cv.put(COLUMN_NAME,columns[3].trim());
                    cv.put(COLUMN_DESIGNATION,columns[4].trim());

                    String strdob=columns[5].trim();
                    String Strdob1=strdob.replace("\"","");
                    cv.put(COLUMN_DOB,Strdob1);

                    String strdoj=columns[6].trim();
                    String Strdoj1=strdoj.replace("\"","");
                    cv.put(COLUMN_DOJ,Strdoj1);

                    cv.put(COLUMN_EMAIL_ID, columns[7].trim());


                    String strcontact=columns[8].trim();
                    String Strcontact1=strcontact.replace("\"","");
                    cv.put(COLUMN_CONTACT_DETAIL,Strcontact1);

                    cv.put(COLUMN_ADDRESS, columns[9].trim());
                    cv.put(COLUMN_HEADQUARTER, columns[10].trim());
                    cv.put(COLUMN_IS_ACTIVE, columns[11].trim());

                    db2.insert(EMPLOYEE_DETAILS, null, cv);
                    //  Log.v("resul3","res3"+db2.insert(Table, null, cv));

                    //Toast.makeText(MainActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db2.setTransactionSuccessful();
            db2.endTransaction();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/


}