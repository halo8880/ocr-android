package com.example.admin.nav;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TextActivity extends AppCompatActivity {
    TextView textView;
    ImageButton text_home, text_copy, text_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ExtendedDataHolder extras = ExtendedDataHolder.getInstance();
        String text_send_strImg = "";
        if (extras.hasExtra("extra")) {
            text_send_strImg = (String) extras.getExtra("extra");
        }
//        Intent intent =  getIntent();
//        String text_send_strImg = intent.getStringExtra("text_send_strImg");
        sendData(text_send_strImg);
        textView = findViewById(R.id.text_result);
        textView.setMovementMethod(new ScrollingMovementMethod());
        //ImageButton Home
        text_home = findViewById(R.id.text_home);
        text_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMain();
            }
        });
        //ImageButton Copy
        text_copy = findViewById(R.id.text_copy);
        text_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy_text", textView.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(TextActivity.this, "Text copied to clipboard ", Toast.LENGTH_SHORT).show();
            }
        });
        //ImageButton Save
        text_save = findViewById(R.id.text_save);
        text_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText name_text;
                Button btnOk, btnCancel;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TextActivity.this);
                LayoutInflater inflater = TextActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_name_text, null);
                name_text = dialogView.findViewById(R.id.name_text);
                btnOk = dialogView.findViewById(R.id.btnOk);
                btnCancel = dialogView.findViewById(R.id.btnCancel);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveData(name_text.getText().toString());
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Lưu văn bản");
                AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String sendData(String data) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://103.114.107.103:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        CallService service = retrofit.create(CallService.class);
        Call<ResponseBody> call = service.getTextFromImage(new ImageData(data));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
//                        if (response.body().string().isEmpty()){
//                            showAlertDialog();
//                        }
                        textView.setText(response.body().string() + "\nthanh cong");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TextActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return "fault";
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image to Text");
        builder.setMessage("Đoạn văn bản rỗng. Quay về trang chủ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(TextActivity.this, "Cancel exit", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startMain();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void startMain() {
        //Khoi tao lai Activity main
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void saveData(String simpleFileName) {
        String data = textView.toString();
        try {
            // Mở một luồng ghi file.
            FileOutputStream out = this.openFileOutput(simpleFileName, MODE_PRIVATE);
            // Ghi dữ liệu.
            out.write(data.getBytes());
            out.close();
            Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void readData(String simpleFileName) {
        try {
            // Mở một luồng đọc file.
            FileInputStream in = this.openFileInput(simpleFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
            this.textView.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
