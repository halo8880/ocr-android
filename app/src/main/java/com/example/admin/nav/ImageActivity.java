package com.example.admin.nav;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView, imageView1;
    ImageButton next, rotate_left, rotate_right;
    Bitmap bitmap;
    int requestCode;
    Uri myUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        imageView = findViewById(R.id.imageView1);
        requestCode = Integer.parseInt(intent.getStringExtra("requestCode"));
        if (requestCode == 0) {
            bitmap = intent.getParcelableExtra("Image");

//            byte[] byteArray = getIntent().getByteArrayExtra("Image");
//            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


            imageView.setImageBitmap(bitmap);


//            myUri = Uri.parse(intent.getStringExtra("Image"));
//            imageView.setImageURI(myUri);
        } else if (requestCode == 1) {
            myUri = Uri.parse(intent.getStringExtra("imageUri"));
//            imageView.setImageBitmap(uriToBitmap(myUri));
            imageView.setImageURI(myUri);
        }
        //Button Rotate left
        rotate_left = findViewById(R.id.rotate_left);
        rotate_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageView.setRotation(imageView.getRotation() - 90);
                bitmap = rotateBitmap(bitmap, -90);
                imageView.setImageBitmap(bitmap);
//                imageView1 = findViewById(R.id.imageView);
            }
        });
        //Button Rotate right
        rotate_right = findViewById(R.id.rotate_right);
        rotate_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageView.setRotation(imageView.getRotation() + 90);
//                imageView1 = findViewById(R.id.imageView);
                bitmap = rotateBitmap(bitmap, +90);
                imageView.setImageBitmap(bitmap);


//                bitmap.
            }
        });
        //Button Next
        next = findViewById(R.id.next_edit_image);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView1 = findViewById(R.id.imageView1);
                String strImg = encodeImageToBase64(imageView1);
//                String strImg = "";
//                if (requestCode == 0){
//                    strImg = convert(bitmap);
//                } else if (requestCode == 1){
//                    try {
//                        strImg = convert(uriToBitmap(myUri));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                System.out.println(strImg);
                Intent intent1 = new Intent(ImageActivity.this, TextActivity.class);

                ExtendedDataHolder extras = ExtendedDataHolder.getInstance();
                extras.putExtra("extra", strImg);
//                intent1.putExtra("text_send_strImg", strImg);
                startActivity(intent1);
            }
        });
    }

    public String encodeImageToBase64(ImageView imageView1) {
        BitmapDrawable drawable = (BitmapDrawable) imageView1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public Bitmap uriToBitmap(Uri uri) {
        try {
//            uri.get
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    public String convert(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
