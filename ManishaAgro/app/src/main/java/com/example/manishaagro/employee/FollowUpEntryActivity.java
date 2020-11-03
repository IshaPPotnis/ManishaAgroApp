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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
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
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowUpEntryActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar followToolbar;
    public ApiInterface apiInterface;
    ConnectionDetector connectionDetector;
    public static final int RequestPermissionCode  = 9003;
    static final int CPAPTURE_IMAGE_REQUEST=30;
    public int rating_val=0;
    File photoFile = null;
    String mCurrentPhotoPath;
    Uri photoURI=null;
    ProgressBar progressBar;
    TextView TextFarmerName,ratText;
    EditText editObservaton,editReview;
    Button FollowSubmit,photoDemoCamera;
    String employeeID="";
    RatingBar rateBar;
    ImageView photoDemoImg;
    private Bitmap myBitmap,bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_entry);
        connectionDetector=new ConnectionDetector();
        followToolbar=findViewById(R.id.toolbarFollowup);


        setSupportActionBar(followToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        progressBar=findViewById(R.id.progress);
        TextFarmerName=findViewById(R.id.textFarmName);
        editObservaton=findViewById(R.id.editObservation);
        editReview=findViewById(R.id.editTextReview);
        rateBar=findViewById(R.id.ratingBar);
        ratText=findViewById(R.id.ratingText);
        photoDemoImg=findViewById(R.id.photoDemo);
        photoDemoCamera=findViewById(R.id.clickImage);
        FollowSubmit=findViewById(R.id.folloUpSubmit);

        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("Check_Follow_Up_ACTV");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("Follow_Up_click")) {
            String name = intent.getStringExtra("CustomerOrFarmer_name");
            String dateOffollow = intent.getStringExtra("Follow_UP_Date");
            String idsEmp = intent.getStringExtra("Emp_Id_FromFollow_AcT");

            employeeID=idsEmp;
            TextFarmerName.setText(name);

        }

        rateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBarval, float rating, boolean fromUser) {
                float noofstars = rateBar.getNumStars();
                ratText.setText("Rating: "+rating+"/"+noofstars);
                rating_val= (int) rating;
            }
        });

        FollowSubmit.setOnClickListener(this);
        photoDemoCamera.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.folloUpSubmit)
        {
            if (connectionDetector.isConnected(FollowUpEntryActivity.this))
            {
                followupUpdate();
            }
            else
            {
                Toast.makeText(FollowUpEntryActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }
        if (v.getId() == R.id.clickImage)
        {
            captureImage();

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
                    Log.i("Mayank",photoFile.getAbsolutePath());

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CPAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {

            myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            bitmap=getResizedBitmap(myBitmap, 400);
            photoDemoImg.setImageBitmap(bitmap);
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
      //  Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    private void followupUpdate()
    {

        int ratingvalue=0;

        final String farmerNameText = TextFarmerName.getText().toString().trim();
        final String observation=editObservaton.getText().toString().trim();
        final String review= editReview.getText().toString().trim();
         ratingvalue = rating_val;
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

        if (employeeID.equals("")||farmerNameText.equals("")||observation.equals("")||review.equals("")||ratingvalue==0||photodemo.equals(""))
        {
            Toast.makeText(FollowUpEntryActivity.this,"Fields Are Empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.FollowupEntryUpdate("FollowUp@Update", employeeID,farmerNameText,observation,review,ratingvalue,photodemo);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response)
                {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if(value.equals("1"))
                    {
                        progressBar.setVisibility(View.GONE);
                        TextFarmerName.setText("");
                        editObservaton.setText("");
                        editReview.setText("");

                  //      Toast.makeText(FollowUpEntryActivity.this,message,Toast.LENGTH_LONG).show();
                        finish();

                    }
                    else if(value.equals("0"))
                    {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(FollowUpEntryActivity.this,message,Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t)
                {



                    if (connectionDetector.isConnected(FollowUpEntryActivity.this))
                    {
                        Toast.makeText(FollowUpEntryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(FollowUpEntryActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}
