package com.example.mc_proj1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    //Initialize Variable
    ImageView imageView;
    Button btOpen;
    Button btUpload;
    String [] categories = {"Person","Object","Animal","Scenery","Indoor","Plant"};
    AutoCompleteTextView dropdownMenu;
    ArrayAdapter<String> adapterItems;
    TextInputLayout spinnerLayout;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("Page 2");
        context = this;
        //Assign Variable
        imageView = findViewById(R.id.image_view);
        btOpen = findViewById(R.id.bt_open);
        btUpload = findViewById(R.id.bt_upload);
        spinnerLayout = findViewById(R.id.category_spinner);
        dropdownMenu = findViewById(R.id.dropdown_menu);

        adapterItems = new ArrayAdapter<>(this,R.layout.list_item,categories);
        dropdownMenu.setText(categories[0]);
        dropdownMenu.setAdapter(adapterItems);
        dropdownMenu.setEnabled(false);

        //Request for camera permission
        if (ContextCompat.checkSelfPermission( MainActivity2.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity2.this,
                    new String [] {
                            Manifest.permission.CAMERA
                    }, 100);
        }

        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            //Get capture image
            adapterItems = new ArrayAdapter<>(this,R.layout.list_item,categories);
            dropdownMenu.setText(categories[0]);
            dropdownMenu.setAdapter(adapterItems);
            dropdownMenu.setEnabled(false);

            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            File img = savebitmap(captureImage);

            //Set capture image to image view
            imageView.setImageBitmap(captureImage);
            btUpload.setVisibility(View.VISIBLE);
            spinnerLayout.setVisibility(View.VISIBLE);
            dropdownMenu.setEnabled(true);
        }
    }

    public void upload(View v){
        String postUrl= "http://192.168.0.192:88/image";
        //
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String category = dropdownMenu.getText().toString();
        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
//                .addFormDataPart("image", "androidFlask.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .addFormDataPart("category", category)
                .addFormDataPart("image", "assignment1Image.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();
        //
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBodyImage)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                Log.d("Api_error_tag", "onFailure: "+ e.getMessage());
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Context context = getApplicationContext();
                        CharSequence text = "Connection Error";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int duration = Toast.LENGTH_SHORT;
                            String text = response.body().string();
                            System.out.println(text);
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        Intent i = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(i);
    }

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");
        }
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

}