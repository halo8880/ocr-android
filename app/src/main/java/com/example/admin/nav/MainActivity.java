package com.example.admin.nav;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.nav.main_menu.ReleaseNoteDialog;
import com.example.admin.nav.main_menu.ReleaseNotesActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imageView;
    EditText textView;
    Uri imageUri,mImageUri;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bundle = savedInstanceState;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent1 = new Intent(this, ImageActivity.class);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");


//                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
//                byte[] byteArray = bStream.toByteArray();

                intent1.putExtra("requestCode", "0");
                intent1.putExtra("Image", bitmap);
//                intent1.putExtra("Image", mImageUri.toString());
                startActivity(intent1);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancel Camera", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error Camera!", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
               imageUri = data.getData();
               intent1.putExtra("requestCode", "1");
               intent1.putExtra("imageUri", imageUri.toString());
               startActivity(intent1);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancel Gallery", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error Gallery!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemCamera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//                File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                mImageUri = Uri.fromFile(f);
//                System.out.println(mImageUri.toString());
//                System.out.println("12334513534213");

                startActivityForResult(intent, 0);
                return true;
            case R.id.lib_pic:
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_whats_new) {
//            Intent intent1 = new Intent(this, ReleaseNotesActivity.class);
////            startActivity(intent1);
//            ReleaseNoteDialog releaseNoteDialog = new ReleaseNoteDialog();
//            releaseNoteDialog.onCreateDialog(bundle);

        } else if (id == R.id.nav_show_tips) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_exit) {
            showAlertDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image to Text");
        builder.setMessage("Bạn có muốn thoát không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Cancel exit", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Khoi tao lai Activity main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                // Tao su kien ket thuc app
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    private Uri getImageUri(Context context, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
}
