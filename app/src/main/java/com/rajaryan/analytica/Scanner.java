package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Scanner extends AppCompatActivity {
    Button capture,select;
    Bitmap bitmap;
    CropImageView cropImageView;
    Bitmap cropped;
    private ImageView img;
    private TextView textview;
    private Button snapBtn;
    String cost;
    private Button detectBtn;
    Uri imageUri;
    String current;
    // variable for our image bitmap.
    private Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        capture=findViewById(R.id.cap);
        select=findViewById(R.id.select);
        cropImageView=findViewById(R.id.cropImageView);
        if(ContextCompat.checkSelfPermission(Scanner.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Scanner.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        img = (ImageView) findViewById(R.id.image);
        textview = (TextView) findViewById(R.id.text);
        snapBtn = (Button) findViewById(R.id.snapbtn);
        detectBtn = (Button) findViewById(R.id.detectbtn);

        // adding on click listener for detect button.
        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to
                // detect a text .
                detectTxt();
            }
        });
        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to capture our image.
                dispatchTakePictureIntent();
                img.setVisibility(View.GONE);
            }
        });
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        // in the method we are displaying an intent to capture our image.
        String name="photo";
        File storage=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(name,".jpg",storage);
            current=imageFile.getAbsolutePath();
            imageUri=FileProvider.getUriForFile(Scanner.this,"com.rajaryan.analytica.fileprovider",imageFile);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // calling on activity result method.
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .


            bitmap= BitmapFactory.decodeFile(current);

            // below line is to set the
            // image bitmap to our image.
            cropImageView.setImageBitmap(bitmap);
            snapBtn.setText("Crop");
            textview.setText("Select Total Bill Amount");
            snapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cropped = cropImageView.getCroppedImage();
                    img.setImageBitmap(cropped);

                }
            });

        }
    }

    private void detectTxt() {
        TextRecognizer recognizer=new TextRecognizer.Builder(this).build();
        if(!recognizer.isOperational()){
            Toast.makeText(Scanner.this, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Frame frame=new Frame.Builder().setBitmap(cropImageView.getCroppedImage()).build();
            SparseArray<TextBlock> textBlockSparseArray=recognizer.detect(frame);
            StringBuilder stringBuilder=new StringBuilder();
            for(int i=0;i<textBlockSparseArray.size();i++){
                TextBlock textBlock=textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");

            }
            Toast.makeText(Scanner.this,stringBuilder.toString(),Toast.LENGTH_SHORT).show();
            textview.setText("Billing Amount : "+stringBuilder.toString());
                cost=stringBuilder.toString();


            snapBtn.setText("Retake");
            snapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                    img.setVisibility(View.GONE);
                }
            });
            detectBtn.setText("Done");
            detectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseStorage storage;
                    StorageReference storageReference;
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.getReference();
                    StorageReference ref
                            = storageReference
                            .child(
                                    "images/"
                                            + UUID.randomUUID().toString());

                    // adding listeners on upload
                    // or failure of image
                    ref.putFile(imageUri)
                            .addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        @Override
                                        public void onSuccess(
                                                UploadTask.TaskSnapshot taskSnapshot)
                                        {

                                            // Image uploaded successfully
                                            // Dismiss dialog

                                            Toast
                                                    .makeText(Scanner.this,
                                                            "Image Uploaded!!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    String fileLink = task.getResult().toString();
                                                    BottomSheetDialogFragment bottomSheet=new Done();
                                                    Bundle b = new Bundle();
                                                    String link= String.valueOf(fileLink);
                                                    b.putString("cost", String.valueOf(cost));
                                                    b.putString("image_link",link);
                                                    bottomSheet.setArguments(b);
                                                    bottomSheet.show(Scanner.this.getSupportFragmentManager(), bottomSheet.getTag());
                                                }
                                            });

                                        }
                                    })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {

                                    // Error, Image not uploaded

                                    Toast
                                            .makeText(Scanner.this,
                                                    "Failed " + e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .addOnProgressListener(
                                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                                        // Progress Listener for loading
                                        // percentage on the dialog box
                                        @Override
                                        public void onProgress(
                                                UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            Toast.makeText(getApplicationContext(),"Image Uploading",Toast.LENGTH_SHORT).show();
                                            double progress
                                                    = (100.0
                                                    * taskSnapshot.getBytesTransferred()
                                                    / taskSnapshot.getTotalByteCount());
                                        }
                                    });

                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,Ground.class);
        startActivity(i);
    }
}