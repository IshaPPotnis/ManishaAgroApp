package com.example.manishaagro.employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoImageActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RequestPermissionCode  = 9003;
    static final int CPAPTURE_IMAGE_REQUEST=30;
    ConnectionDetector connectionDetector;
    File photoFile = null;
    String mCurrentPhotoPath;
    Uri photoURI=null;
    ProgressBar  progressBar;
    ApiInterface apiInterface;
    Toolbar visitStartToolbar;
    EditText editTextFarmerName;
    AutoCompleteTextView autoDemoFarmername;
    ImageView autoDemoFamemerImg,photoDemoImg;
    Button visitEntrySubmit,photoDemoCamera;
    String employeeID="";

    private Bitmap myBitmap,bitmap;
    public ArrayList<TripModel> DemoImagfmerNameData = new ArrayList<TripModel>();
    public ArrayList<String> DemoImgfmerNameList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_image);
        connectionDetector=new ConnectionDetector();
        visitStartToolbar=findViewById(R.id.toolbarvisit);
        setSupportActionBar(visitStartToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeProductActivityToDemoimg");
        autoDemoFarmername=findViewById(R.id.autoCompleteDemoFarmerName);
        autoDemoFamemerImg=findViewById(R.id.autoTextDemoFarmerNameImg);
        progressBar=findViewById(R.id.progress);
        editTextFarmerName=findViewById(R.id.editTextFarmerName);
        photoDemoImg=findViewById(R.id.photoDemo);
        photoDemoCamera=findViewById(R.id.clickImage);
        visitEntrySubmit=findViewById(R.id.DemoImageSubmit);

        getDemoImageFarmername();

        autoDemoFarmername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                autoDemoFarmername.setFocusable(false);
                autoDemoFarmername.setEnabled(false);
                return false;
            }
        });

        autoDemoFamemerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoDemoFarmername.setEnabled(true);
                autoDemoFarmername.showDropDown();

            }
        });






        visitEntrySubmit.setOnClickListener(this);
        photoDemoCamera.setOnClickListener(this);
    }


    private void getDemoImageFarmername()
    {
        Log.v("fnamelist1", "Demoimgfnmaelis" + employeeID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<TripModel>> callDemoImgFmernmList=apiInterface.getForDemoImgFarmerNameList("getDemoImageFmrN@meLists",employeeID);
        callDemoImgFmernmList.enqueue(new Callback<ArrayList<TripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TripModel>> call, Response<ArrayList<TripModel>> response) {
                assert response.body() != null;
                DemoImagfmerNameData.clear();
                DemoImagfmerNameData.addAll(response.body());
                Log.v("Runcheck1", "user1" + DemoImagfmerNameData);
                DemoImgfmerNameList = new ArrayList<String>();
                for (int i = 0; i < DemoImagfmerNameData.size(); i++) {
                    String lat =  DemoImagfmerNameData.get(i).getVisitedCustomerName();
                    DemoImgfmerNameList.add(lat);
                }
                final ArrayAdapter<String> adpAllFarmernm = new ArrayAdapter<String>(DemoImageActivity.this, android.R.layout.simple_list_item_1,DemoImgfmerNameList);
                autoDemoFarmername.setAdapter(adpAllFarmernm);
                autoDemoFarmername.setEnabled(false);
                Log.v("Runcheck2", "user1" + DemoImgfmerNameList);
//                Toast.makeText(DemoImageActivity.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<TripModel>> call, Throwable t) {
                Toast.makeText(DemoImageActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.DemoImageSubmit) {

            if (connectionDetector.isConnected(DemoImageActivity.this))
            {
                visitEntry();
            }
            else
            {
                Toast.makeText(DemoImageActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }



        }
        if(v.getId() == R.id.clickImage)
        {
            captureImage();
            //Intent intentdemoCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(intentdemoCamera,CPAPTURE_IMAGE_REQUEST);
        }

    }

    public void captureImage()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();
                    displayMessage(getBaseContext(),photoFile.getAbsolutePath());


                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(this,
                                "com.example.manishaagro.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CPAPTURE_IMAGE_REQUEST);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(),ex.getMessage().toString());
                }


            }else
            {
                displayMessage(getBaseContext(),"Nullll");
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CPAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {

                myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            bitmap=getResizedBitmap(myBitmap, 400);
            photoDemoImg.setImageBitmap(bitmap);
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RequestPermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();

            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void visitEntry()
    {


        final String farmerNameText = autoDemoFarmername.getText().toString().trim();

        final  String photodemo;

        if (bitmap==null)
        {
         photodemo="";
        }
        else {
            photodemo = getStringImage(bitmap);

        }
        Log.v("demo", "image" + photodemo);
        Log.v("demoid", "image" + farmerNameText);
        Log.v("demoName", "image" + employeeID);

        if (employeeID.equals("")||farmerNameText.equals("")||photodemo.equals(""))
        {
            Toast.makeText(DemoImageActivity.this,"Fields Are Empty",Toast.LENGTH_SHORT).show();
        }
        else
        {progressBar.setVisibility(View.VISIBLE);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoPhotoEntry("DemoImage@Farmer", employeeID,farmerNameText,photodemo);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();


                    if(value.equals("1"))
                    {
                        progressBar.setVisibility(View.GONE);
                        autoDemoFarmername.setText("");
                        photoDemoImg.setImageBitmap(null);
                        Toast.makeText(DemoImageActivity.this,message,Toast.LENGTH_SHORT).show();

                     Intent dimgIntent = new Intent(DemoImageActivity.this, SelfieImageActivity.class);
                        dimgIntent.putExtra("visitedEmployeeSelfie", employeeID);
                        startActivity(dimgIntent);
                        finish();


                    }
                    else if(value.equals("0"))
                    {   progressBar.setVisibility(View.GONE);

                        Toast.makeText(DemoImageActivity.this,message,Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {

                    if (connectionDetector.isConnected(DemoImageActivity.this))
                    {
                        Toast.makeText(DemoImageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(DemoImageActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }



                }
            });
        }



    }

}
