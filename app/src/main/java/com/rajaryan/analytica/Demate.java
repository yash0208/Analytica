package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class Demate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText fn,ln,mn,ffn,fmn,fln,mfn,mln,mmn,pob,acn,pcn,acc,cn,ea,fcn,fea,add,log;
    String fn1,ln1,mn1,ffn1,fmn1,fln1,mfn1,mln1,mmn1,pob1,acn1,pcn1,acc1,cn1,ea1,fcn1,fea1,add1,log1,dob1,adhar,pan,bank,sign,selfi,gender,relation,address_type;
    Button dob,adharcard,pancard,check,signature,selfi_but;
    Uri imageUri;
    ProgressDialog progressDialog;
    Button submit;
    FrameLayout frame1,frame2;
    Button demate;
    ProgressBar progressBar;
    double progress;
    String current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demate);
        radioGroup = findViewById(R.id.radioGroup);
        adharcard=findViewById(R.id.adharcard);
        frame1=findViewById(R.id.frame1);
        frame2=findViewById(R.id.frame2);
        demate=findViewById(R.id.demate);
        demate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame1.setVisibility(View.GONE);
                frame2.setVisibility(View.VISIBLE);
            }
        });
        progressBar=findViewById(R.id.progrss_bar);
        pancard=findViewById(R.id.pancard);
        check=findViewById(R.id.check);
        signature=findViewById(R.id.sign);
        selfi_but=findViewById(R.id.selfi);
        fn =findViewById(R.id.fn);
        ln =findViewById(R.id.ln);
        mn =findViewById(R.id.mn);
        fn =findViewById(R.id.fn);
        ffn =findViewById(R.id.ffn);
        fmn =findViewById(R.id.fmn);
        fln =findViewById(R.id.fln);
        mfn =findViewById(R.id.mfn);
        mln =findViewById(R.id.mln);
        mmn =findViewById(R.id.mmn);
        pob =findViewById(R.id.pob);
        acn =findViewById(R.id.acn);
        pcn =findViewById(R.id.pcn);
        acc =findViewById(R.id.acc);
        cn  =findViewById(R.id.cn);
        ea  =findViewById(R.id.ea);
        fcn =findViewById(R.id.fcn);
        fea =findViewById(R.id.fea);
        add =findViewById(R.id.add);
        log =findViewById(R.id.log);
        fn1 =fn.getText().toString();
        ln1 =ln.getText().toString();
        mn1 =mn.getText().toString();
        fn1 =fn.getText().toString();
        ffn1=ffn.getText().toString();
        fmn1=fmn.getText().toString();
        fln1=fln.getText().toString();
        mfn1=mfn.getText().toString();
        mln1=mln.getText().toString();
        mmn1=mmn.getText().toString();
        pob1=pob.getText().toString();
        acn1=acn.getText().toString();
        pcn1=pcn.getText().toString();
        acc1=acc.getText().toString();
        cn1=cn.getText().toString();
        ea1=ea.getText().toString();
        fcn1=fcn.getText().toString();
        fea1 =fea.getText().toString();
        add1=add.getText().toString();
        log1=log.getText().toString();
        progressBar.setVisibility(View.GONE);

        dob=findViewById(R.id.cal);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(fn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid First Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(ln.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Last Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Middle Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(ffn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Father's First Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(fln.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Father's Last Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(fmn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Father's Middle Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mfn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Mother's First Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mln.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Mother's Last Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mmn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Mother's Middle Name",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pob.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Place Of Birth",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(acn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Adharcard Number",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pcn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Pan Card Number",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(acc.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Account Number",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(cn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Contact Number",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(ea.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(fcn.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Family Contact Number",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(fea.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Family Email Address",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(add.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Address",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(fea.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid Family Email Address",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(log.getText().toString())){
                    Toast.makeText(Demate.this,"Enter Valid login id",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(dob1)){
                    Toast.makeText(Demate.this,"Select Valid Date Of Birth",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(adhar)){
                    Toast.makeText(Demate.this,"Upload Adharcard",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pan)){
                    Toast.makeText(Demate.this,"Upload Pancard",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(bank)){
                    Toast.makeText(Demate.this,"Upload Check Photo",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(sign)){
                    Toast.makeText(Demate.this,"Upload Sign Photo",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(sign)){
                    Toast.makeText(Demate.this,"Upload Sign Photo",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(selfi)){
                    Toast.makeText(Demate.this,"Upload Selfie",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(gender)){
                    Toast.makeText(Demate.this,"Select Gender",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(relation)){
                    Toast.makeText(Demate.this,"Select Relation",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(address_type)){
                    Toast.makeText(Demate.this,"Select Address Type",Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("First Name",fn.getText().toString());
                    hashMap.put("Last Name",ln.getText().toString());
                    hashMap.put("Middle Name",mn.getText().toString());
                    hashMap.put("Father's First Name",ffn.getText().toString());
                    hashMap.put("Father's Last Name",fln.getText().toString());
                    hashMap.put("Father's Middle Name",fmn.getText().toString());
                    hashMap.put("Mother's First Name",mfn.getText().toString());
                    hashMap.put("Mother's Last Name",mln.getText().toString());
                    hashMap.put("Mother's Middle Name",mmn.getText().toString());
                    hashMap.put("Place Of Birth",pob.getText().toString());
                    hashMap.put("Adharcard Number",acn.getText().toString());
                    hashMap.put("Pan Card Number",pcn.getText().toString());
                    hashMap.put("Account Number",acc.getText().toString());
                    hashMap.put("Contact Number",cn.getText().toString());
                    hashMap.put("Email Address",ea.getText().toString());
                    hashMap.put("Family Contact Number",fcn.getText().toString());
                    hashMap.put("Family Email Address",fea.getText().toString());
                    hashMap.put("Address",add.getText().toString());
                    hashMap.put("login id",log.getText().toString());
                    hashMap.put("Date Of Birth",dob1);
                    hashMap.put("Adharcard",adhar);
                    hashMap.put("Pancard",pan);
                    hashMap.put("Check Photo",bank);
                    hashMap.put("Sign Photo",sign);
                    hashMap.put("Selfie",selfi);
                    hashMap.put("Relation",relation);
                    hashMap.put("Gender",gender);
                    hashMap.put("Address Type",address_type);
                    DatabaseReference medicines = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Demat_Details");
                    medicines.setValue(hashMap);
                }
            }
        });
        progressDialog=new ProgressDialog(Demate.this);
        progressDialog.setContentView(R.layout.progess);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();
        gender=radioButton.getText().toString();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dob1=currentDateString;
        dob.setText(currentDateString);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
    public void adhar(View view) {
        String name="adhar";
        File storage=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Log.w("path of image from gallery......******************.........", picturePath+"");

            Bitmap bitmap= BitmapFactory.decodeFile(current);
            FirebaseStorage storage1;
            StorageReference storageReference;
            storage1 = FirebaseStorage.getInstance();
            storageReference = storage1.getReference();
            StorageReference ref
                    = storageReference
                    .child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid().toString() +"demate/"+"aadhar/"
                                    + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImage)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    // Image uploaded successfully
                                    // Dismiss dialog

                                    Toast.makeText(Demate.this,
                                            "Image Uploaded!!",
                                            Toast.LENGTH_SHORT)
                                            .show();
                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            String link= String.valueOf(fileLink);
                                            adhar=link;
                                            progressBar.setVisibility(View.GONE);

                                            progressDialog.dismiss();
                                            adharcard.setText("Uploaded");
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
                                    .makeText(Demate.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressDialog.dismiss();
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

                                    progressBar.setVisibility(View.VISIBLE);
                                    progressDialog.show();
                                }
                            });


        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Log.w("path of image from gallery......******************.........", picturePath+"");

            Bitmap bitmap= BitmapFactory.decodeFile(current);
            FirebaseStorage storage1;
            StorageReference storageReference;
            storage1 = FirebaseStorage.getInstance();
            storageReference = storage1.getReference();
            StorageReference ref
                    = storageReference
                    .child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid().toString() +"demate/"+"pan/"
                                    + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImage)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    Toast.makeText(Demate.this,
                                            "Image Uploaded!!",
                                            Toast.LENGTH_SHORT)
                                            .show();
                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            String link= String.valueOf(fileLink);
                                            pan=link;
                                            progressBar.setVisibility(View.GONE);
                                            pancard.setText("Uploaded");
                                            progressDialog.dismiss();

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
                                    .makeText(Demate.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressDialog.dismiss();
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
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressDialog.show();
                                }
                            });


        }
        if (requestCode == 4 && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Log.w("path of image from gallery......******************.........", picturePath+"");

            Bitmap bitmap= BitmapFactory.decodeFile(current);
            FirebaseStorage storage1;
            StorageReference storageReference;
            storage1 = FirebaseStorage.getInstance();
            storageReference = storage1.getReference();
            StorageReference ref
                    = storageReference
                    .child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid().toString() +"demate/"+"sign/"
                                    + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImage)


                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded

                            Toast
                                    .makeText(Demate.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressDialog.dismiss();
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
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressDialog.show();
                                }
                            }).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(
                                UploadTask.TaskSnapshot taskSnapshot)
                        {


                            // Image uploaded successfully
                            // Dismiss dialog
                            Toast.makeText(Demate.this,
                                    "Image Uploaded!!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String fileLink = task.getResult().toString();
                                    String link= String.valueOf(fileLink);
                                    sign=link;
                                    progressBar.setVisibility(View.GONE);
                                    signature.setText("Uploaded");
                                    progressDialog.dismiss();
                                }
                            });

                        }
                    });


        }
        if (requestCode == 5 && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Log.w("path of image from gallery......******************.........", picturePath+"");

            Bitmap bitmap= BitmapFactory.decodeFile(current);
            FirebaseStorage storage1;
            StorageReference storageReference;
            storage1 = FirebaseStorage.getInstance();
            storageReference = storage1.getReference();
            StorageReference ref
                    = storageReference
                    .child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid().toString() +"demate/"+"check/"
                                    + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImage)


                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded

                            Toast
                                    .makeText(Demate.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressDialog.dismiss();
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
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressDialog.show();
                                }
                            }).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(
                                UploadTask.TaskSnapshot taskSnapshot)
                        {

                            // Image uploaded successfully
                            // Dismiss dialog
                            Toast.makeText(Demate.this,
                                    "Image Uploaded!!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String fileLink = task.getResult().toString();
                                    String link= String.valueOf(fileLink);
                                    bank=link;
                                    progressBar.setVisibility(View.GONE);
                                    check.setText("Uploaded");
                                    progressDialog.dismiss();
                                }
                            });

                        }
                    });


        }
        if (requestCode == 6 && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Log.w("path of image from gallery......******************.........", picturePath+"");

            Bitmap bitmap= BitmapFactory.decodeFile(current);
            FirebaseStorage storage1;
            StorageReference storageReference;
            storage1 = FirebaseStorage.getInstance();
            storageReference = storage1.getReference();
            StorageReference ref
                    = storageReference
                    .child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid().toString() +"demate/"+"live/"
                                    + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImage)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded

                            Toast
                                    .makeText(Demate.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressDialog.dismiss();
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
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressDialog.show();
                                }
                            }).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(
                                UploadTask.TaskSnapshot taskSnapshot)
                        {

                            // Image uploaded successfully
                            // Dismiss dialog
                            Toast.makeText(Demate.this,
                                    "Image Uploaded!!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String fileLink = task.getResult().toString();
                                    String link= String.valueOf(fileLink);
                                    selfi=link;
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();
                                    selfi_but.setText("Uploaded");
                                }
                            });

                        }
                    });


        }

    }
    public void pan(View view) {
        String name="adhar";
        File storage=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);
    }
    public void sign(View view) {
        String name="adhar";
        File storage=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4);
    }
    public void check(View view) {
        String name="adhar";
        File storage=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 5);
    }
    public void selfi(View view) {
        String name="adhar";
        File storage=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 6);
    }
    public void checkButton1(View view) {
        RadioGroup radioGroup1=findViewById(R.id.radioGroup1);
        int radioId = radioGroup1.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();
        relation=radioButton.getText().toString();
    }
    public void checkButton2(View view) {
        RadioGroup radioGroup2=findViewById(R.id.radioGroup2);
        int radioId = radioGroup2.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();
        address_type=radioButton.getText().toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,Ground.class);
        startActivity(i);
    }
}