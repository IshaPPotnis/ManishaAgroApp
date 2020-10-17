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
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.model.TripModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.R.id.VisitStartSubmit;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class DemoImageActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RequestPermissionCode  = 9003;
    static final int CPAPTURE_IMAGE_REQUEST=30;
    File photoFile = null;
    String mCurrentPhotoPath;
    Uri photoURI=null;

    TextView backDemoImage;
    ProgressBar  progressBar;

    ApiInterface apiInterface;

    Toolbar visitStartToolbar;
    EditText editTextFarmerName;

    Button visitEntrySubmit,photoDemoCamera;
    String employeeID="";

    ImageView photoDemoImg;
    private Bitmap myBitmap,bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_image);
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
        backDemoImage=findViewById(R.id.BackfromDemoImage);
        progressBar=findViewById(R.id.progress);
        editTextFarmerName=findViewById(R.id.editTextFarmerName);
        photoDemoImg=findViewById(R.id.photoDemo);
        photoDemoCamera=findViewById(R.id.clickImage);
        visitEntrySubmit=findViewById(R.id.DemoImageSubmit);

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeDemo");


        visitEntrySubmit.setOnClickListener(this);
        photoDemoCamera.setOnClickListener(this);
        backDemoImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.BackfromDemoImage)
        {
            Intent  visitIntent = new Intent( DemoImageActivity.this,CustomeVisitStartActivity.class);
           visitIntent.putExtra("visitedEmployeeBackFromDemoImage", employeeID);
            visitIntent.putExtra("CheckDemoImageActivity", "Demo@Customerimage");
            startActivity(visitIntent);
            finish();


        }
        if (v.getId() == R.id.DemoImageSubmit) {

            visitEntry();

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


        final String farmerNameText = editTextFarmerName.getText().toString().trim();

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
            Toast.makeText(DemoImageActivity.this,"Fields Are Empty",Toast.LENGTH_SHORT);
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
                        editTextFarmerName.setText("");
                        photoDemoImg.setImageBitmap(null);
                        Toast.makeText(DemoImageActivity.this,message,Toast.LENGTH_SHORT);

                    }
                    else if(value.equals("0"))
                    {   progressBar.setVisibility(View.GONE);

                        Toast.makeText(DemoImageActivity.this,message,Toast.LENGTH_SHORT);
                    }


                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {

                    Toast.makeText(DemoImageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        }



    }

}
