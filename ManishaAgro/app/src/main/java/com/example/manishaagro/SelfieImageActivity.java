package com.example.manishaagro;

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
import android.widget.TextView;
import android.widget.Toast;

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
    ApiInterface apiInterface;
    ProgressBar progressBar;
    Toolbar visitStartToolbar;

    public static final int RequestPermissionCode  = 9003;
    public static final int CPAPTURE_IMAGE_REQUEST__1=93;
    File photoFileSelfie=null;
    String mCurrentPhotoPath;
    Uri photoURI1=null;
    Button visitEntrySubmit,SelfieCustCamera;
    ImageView SelfieCustImage,autoSelfieFamemerImg;

    AutoCompleteTextView autoSelfieFarmername;

    EditText editTextFarmerName;
    private Bitmap myBitmap1,bitmap;
    String employeeID="";
    public ArrayList<TripModel> SelffmerNameData = new ArrayList<TripModel>();
    public ArrayList<String> SelffmerNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_image);
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
        employeeID = intent.getStringExtra("visitedEmployeeSelfie");


        progressBar=findViewById(R.id.progress);
        editTextFarmerName=findViewById(R.id.editTextFarmerName);
        visitEntrySubmit=findViewById(R.id.UploadSelfieSubmit);
        SelfieCustCamera=findViewById(R.id.clickSelfie);
        SelfieCustImage=findViewById(R.id.SelfieCust);
        autoSelfieFarmername=findViewById(R.id.autoCompleteSelfFarmerName);
        autoSelfieFamemerImg=findViewById(R.id.autoTextSelfFarmerNameImg);



        getSelfieImageFarmername();

        autoSelfieFarmername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                autoSelfieFarmername.setFocusable(false);
                autoSelfieFarmername.setEnabled(false);
                return false;
            }
        });

        autoSelfieFamemerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoSelfieFarmername.setEnabled(true);
                autoSelfieFarmername.showDropDown();

            }
        });


        visitEntrySubmit.setOnClickListener(this);
        SelfieCustCamera.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.UploadSelfieSubmit)
        {
            visitEntry();
        }
        if (v.getId() == R.id.clickSelfie)
        {
            captureImageSelfie();
        }

    }

    private void getSelfieImageFarmername()
    {
        Log.v("fnamelist1", "Selfieimgfnmaelis" + employeeID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<TripModel>> callDemoImgFmernmList=apiInterface.getForDemoImgFarmerNameList("getSelfieFmrN@meLists",employeeID);
        callDemoImgFmernmList.enqueue(new Callback<ArrayList<TripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TripModel>> call, Response<ArrayList<TripModel>> response) {
                assert response.body() != null;
                SelffmerNameData.clear();
                SelffmerNameData.addAll(response.body());
                Log.v("Runcheck1", "user1" + SelffmerNameData);
                SelffmerNameList = new ArrayList<String>();
                for (int i = 0; i < SelffmerNameData.size(); i++) {
                    String lat =  SelffmerNameData.get(i).getVisitedCustomerName();
                    SelffmerNameList.add(lat);
                }
                final ArrayAdapter<String> adpAllFarmernm = new ArrayAdapter<String>(SelfieImageActivity.this, android.R.layout.simple_list_item_1,SelffmerNameList);

                autoSelfieFarmername.setAdapter(adpAllFarmernm);
                autoSelfieFarmername.setEnabled(false);
                Log.v("Runcheck2", "user1" + SelffmerNameList);

                Toast.makeText(SelfieImageActivity.this, "Success", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ArrayList<TripModel>> call, Throwable t) {
                Toast.makeText(SelfieImageActivity.this, "Have some error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CPAPTURE_IMAGE_REQUEST__1 && resultCode == RESULT_OK) {
            myBitmap1 = BitmapFactory.decodeFile(photoFileSelfie.getAbsolutePath());
            bitmap=getResizedBitmap(myBitmap1, 400);
            SelfieCustImage.setImageBitmap(myBitmap1);
        }
        else
        {
            displayMessage(getBaseContext(),"Request cancelled or something went wrong.");
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

    public void captureImageSelfie()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, RequestPermissionCode);
        }
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFileSelfie = createImageFile();
                    displayMessage(getBaseContext(),photoFileSelfie.getAbsolutePath());
                    Log.i("Mayank",photoFileSelfie.getAbsolutePath());

                    // Continue only if the File was successfully created
                    if (photoFileSelfie != null) {
                        photoURI1 = FileProvider.getUriForFile(this,
                                "com.example.manishaagro.fileprovider",
                                photoFileSelfie);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI1);
                        startActivityForResult(takePictureIntent, CPAPTURE_IMAGE_REQUEST__1);
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

        if (requestCode == RequestPermissionCode)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImageSelfie();

            }
        }

    }

    private void visitEntry()
    {


        final String farmerNameText = autoSelfieFarmername.getText().toString().trim();

        final  String photocustSelfie;
        if (bitmap==null)
        {
            photocustSelfie="";
        }
        else
        {
            photocustSelfie = getStringImage(bitmap);
        }





        Log.v("demo", "image" + photocustSelfie);
        Log.v("demoid", "image" + farmerNameText);
        Log.v("demoName", "image" + employeeID);

        if (employeeID.equals("")||farmerNameText.equals("")||photocustSelfie.equals(""))
        {
            Toast.makeText(SelfieImageActivity.this,"Fields Are Empty",Toast.LENGTH_SHORT).show();
        }
        else
        {    progressBar.setVisibility(View.VISIBLE);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertSelfiePhotoEntry("SelfieImage@Farmer", employeeID,farmerNameText,photocustSelfie);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();


                    if(value.equals("1"))
                    {
                        progressBar.setVisibility(View.GONE);
                        autoSelfieFarmername.setText("");
                        SelfieCustImage.setImageBitmap(null);
                        Toast.makeText(SelfieImageActivity.this,message,Toast.LENGTH_SHORT).show();

                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(SelfieImageActivity.this,message,Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {

                    Toast.makeText(SelfieImageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });

        }



    }

}
