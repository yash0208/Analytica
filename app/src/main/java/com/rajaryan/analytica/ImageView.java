package com.rajaryan.analytica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

public class ImageView extends AppCompatActivity {
    TextView details,cost,type;
    ImageButton share;
    android.widget.ImageView bill_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        details=findViewById(R.id.details);
        cost=findViewById(R.id.cost);
        type=findViewById(R.id.type);
        share=findViewById(R.id.share);
        bill_image=findViewById(R.id.image);


        cost.setText(getIntent().getStringExtra("cost"));
        details.setText(getIntent().getStringExtra("details"));
        type.setText(getIntent().getStringExtra("type"));
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("image")).into(bill_image);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Drawable mDrawable = bill_image.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
                Uri uri = Uri.parse(path);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share Image"));
            }
        });
    }

    public void share(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(getIntent().getStringExtra("image"));
        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, "Share image using"));
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i;
        i = new Intent(ImageView.this, Base.class);
        i.putExtra("status","2");
        startActivity(i);
    }

}