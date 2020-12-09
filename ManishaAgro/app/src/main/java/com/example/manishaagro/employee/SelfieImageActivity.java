package com.example.manishaagro.employee;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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

public class SelfieImageActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RequestPermissionCode = 9003;
    public static final int CAPTURE_IMAGE_REQUEST__1 = 93;
    String employeeID = "";
    String demoImgDate="";
    String demoImgCon="";
    String farmerName="";
    String visitids="";

    ApiInterface apiInterface;
    ConnectionDetector connectionDetector;
    ProgressBar progressBar;

    Toolbar visitStartToolbar;
    File photoFileSelfie = null;
    String mCurrentPhotoPath;
    Uri photoURI1 = null;
    Button visitEntrySubmit;
    Button selfieCustomerCamera;
    ImageView selfieCustomerImage;
    ImageView autoSelfieFarmerImage;
    AutoCompleteTextView autoSelfieFarmerName;
  //  EditText editTextFarmerName;
    private Bitmap bitmap;
    public ArrayList<TripModel> farmerNameData = new ArrayList<TripModel>();
    public ArrayList<String> farmerNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_image);
        connectionDetector=new ConnectionDetector();
        visitStartToolbar = findViewById(R.id.toolbarvisit);
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


        String SelfieImgFromPendkey=intent.getStringExtra("emp_SelfieImg_pen");
        if(SelfieImgFromPendkey!=null && SelfieImgFromPendkey.equals("emp_SelfieImg_pen_check"))
        {
            farmerName = intent.getStringExtra("pendingSelfieImg_customer_name");
            demoImgDate = intent.getStringExtra("pendingSelfieImg_date");
            demoImgCon = intent.getStringExtra("pendingSelfieImg_contact");
            visitids=intent.getStringExtra("pendingSelfieImg_customer_visitid");
            employeeID = intent.getStringExtra("penSelfieImg_empid");
        }
        else
        {
            employeeID = intent.getStringExtra("visitedEmployeeSelfie");
            farmerName=intent.getStringExtra("farmernames");
        }



        progressBar = findViewById(R.id.progress);
      //  editTextFarmerName = findViewById(R.id.editTextFarmerName);
        visitEntrySubmit = findViewById(R.id.UploadSelfieSubmit);
        selfieCustomerCamera = findViewById(R.id.clickSelfie);
        selfieCustomerImage = findViewById(R.id.SelfieCust);
        autoSelfieFarmerName = findViewById(R.id.autoCompleteSelfFarmerName);
        autoSelfieFarmerImage = findViewById(R.id.autoTextSelfFarmerNameImg);

        //   getSelfieImageFarmerName();

        autoSelfieFarmerName.setText(farmerName);
        autoSelfieFarmerName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoSelfieFarmerName.setFocusable(false);
                autoSelfieFarmerName.setEnabled(false);
                return false;
            }
        });

        autoSelfieFarmerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoSelfieFarmerName.setEnabled(false);
                autoSelfieFarmerName.showDropDown();
            }
        });

        visitEntrySubmit.setOnClickListener(this);
        selfieCustomerCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.UploadSelfieSubmit) {
            if (connectionDetector.isConnected(SelfieImageActivity.this))
            {
                visitEntry();
            }
            else
            {
                Toast.makeText(SelfieImageActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }
        if (v.getId() == R.id.clickSelfie) {
            captureImageSelfie();
        }
    }

  /*  private void getSelfieImageFarmerName() {
        Log.v("fnamelist1", "Selfieimgfnmaelis" + employeeID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<TripModel>> callDemoImageFarmerNameList = apiInterface.getForDemoImgFarmerNameList("getSelfieFmrN@meLists", employeeID);
        callDemoImageFarmerNameList.enqueue(new Callback<ArrayList<TripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TripModel>> call, Response<ArrayList<TripModel>> response) {
                assert response.body() != null;
                farmerNameData.clear();
                farmerNameData.addAll(response.body());
                Log.v("Runcheck1", "user1" + farmerNameData);
                farmerNameList = new ArrayList<String>();
                for (int i = 0; i < farmerNameData.size(); i++) {
                    String lat = farmerNameData.get(i).getVisitedCustomerName();
                    farmerNameList.add(lat);
                }
                final ArrayAdapter<String> adpAllFarmerName = new ArrayAdapter<String>(SelfieImageActivity.this, android.R.layout.simple_list_item_1, farmerNameList);
                autoSelfieFarmerName.setAdapter(adpAllFarmerName);
                autoSelfieFarmerName.setEnabled(false);
                Log.v("Runcheck2", "user1" + farmerNameList);
//                Toast.makeText(SelfieImageActivity.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<TripModel>> call, Throwable t) {
                if (connectionDetector.isConnected(SelfieImageActivity.this))
                {
                    Toast.makeText(SelfieImageActivity.this, "Have some error", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(SelfieImageActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_REQUEST__1 && resultCode == RESULT_OK) {
            Bitmap bitmap1 = BitmapFactory.decodeFile(photoFileSelfie.getAbsolutePath());
            bitmap = getResizedBitmap(bitmap1, 400);
            selfieCustomerImage.setImageBitmap(bitmap1);
        } else {
            displayMessage(getBaseContext(), "Request cancelled or something went wrong.");
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void displayMessage(Context context, String message) {
       // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void captureImageSelfie() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {
                    photoFileSelfie = createImageFile();
                    displayMessage(getBaseContext(), photoFileSelfie.getAbsolutePath());
                    Log.i("pathss", photoFileSelfie.getAbsolutePath());
                    // Continue only if the File was successfully created
                    if (photoFileSelfie != null) {
                        photoURI1 = FileProvider.getUriForFile(this,
                                "com.example.manishaagro.fileprovider",
                                photoFileSelfie);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI1);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST__1);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(), ex.getMessage());
                }
            } else {
                displayMessage(getBaseContext(), "Null");
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RequestPermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImageSelfie();
            }
        }
    }

    private void visitEntry() {
        final String farmerNameText = autoSelfieFarmerName.getText().toString().trim();
        final String photoCustomerSelfie;
        if (bitmap == null) {
            photoCustomerSelfie = "";
        } else {
            photoCustomerSelfie = getStringImage(bitmap);
        }

        Log.v("demo", "image" + photoCustomerSelfie);
        Log.v("demoid", "image" + farmerNameText);
        Log.v("demoName", "image" + employeeID);

        if (employeeID.equals("") || farmerNameText.equals("") || photoCustomerSelfie.equals("")) {
            Toast.makeText(SelfieImageActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertSelfiePhotoEntry("SelfieImage@Farmer", employeeID, farmerNameText, photoCustomerSelfie);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                    String value = null;
                    if (response.body() != null) {
                        value = response.body().getValue();
                        String message = response.body().getMassage();
                        if (value.equals("1")) {
                            progressBar.setVisibility(View.GONE);
                            autoSelfieFarmerName.setText("");
                            selfieCustomerImage.setImageBitmap(null);
                         //   Toast.makeText(SelfieImageActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (value.equals("0")) {
                            Toast.makeText(SelfieImageActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {
                    if (connectionDetector.isConnected(SelfieImageActivity.this))
                    {
                        Toast.makeText(SelfieImageActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(SelfieImageActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}
