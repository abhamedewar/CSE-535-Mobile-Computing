package com.example.mc_proj1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    Context context;
    ArrayList<Integer> predictions = new ArrayList<>();
    static int count_file = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("Page 2");

        //Assign Variable
        imageView = findViewById(R.id.image_view);
        btOpen = findViewById(R.id.bt_open);
        btUpload = findViewById(R.id.bt_upload);

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

            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            // File img = savebitmap(captureImage);

            //Set capture image to image view
            imageView.setImageBitmap(captureImage);
            btUpload.setVisibility(View.VISIBLE);
        }
    }

    public void upload(View v){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap[][] bm_splits = splitBitmap(bm, 2, 2);

        String[] postUrls = new String[]{"http://192.168.0.188:5000/image", "http://192.168.0.236:5000/image",
                                         "http://192.168.0.39:5000/image", "http://192.168.0.43:5000/image"};
        int count = 0;
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                uploadImage(createByteArray(bm_splits[i][j]), postUrls[count]);
                count++;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run(){
                int value = maxFreq(predictions, 4);
                System.out.println("Max Value " + Integer.toString(value));
                savebitmap(bm, value);
            }
        }, 2000);


    }

    public int uploadImage(byte[] byteArray, String postUrl){
        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "assignment1Image.png", RequestBody.create(MediaType.parse("image/*png"), byteArray))
                .build();
        //
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBodyImage)
                .build();
        int[] prediction = new int[1];
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
                            String text = response.body().string();
                            predictions.add(Integer.parseInt(text));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return prediction[0];
    }

    public int maxFreq(ArrayList<Integer> arr, int n)
    {

        // using moore's voting algorithm
        int res = 0;
        int count = 1;
        for(int i = 1; i < n; i++) {
            if(arr.get(i) == arr.get(res)) {
                count++;
            } else {
                count--;
            }

            if(count == 0) {
                res = i;
                count = 1;
            }

        }

        return arr.get(res);
    }

    public Bitmap[][] splitBitmap(Bitmap bitmap, int xCount, int yCount) {
        // Allocate a two dimensional array to hold the individual images.
        Bitmap[][] bitmaps = new Bitmap[xCount][yCount];
        int width, height;
        // Divide the original bitmap width by the desired vertical column count
        width = bitmap.getWidth() / xCount;
        // Divide the original bitmap height by the desired horizontal row count
        height = bitmap.getHeight() / yCount;
        // Loop the array and create bitmaps for each coordinate
        for(int x = 0; x < xCount; ++x) {
            for(int y = 0; y < yCount; ++y) {
                // Create the sliced bitmap
                bitmaps[x][y] = Bitmap.createBitmap(bitmap, x * width, y * height, width, height);
            }
        }
        // Return the array
        return bitmaps;
    }

    public byte[] createByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private File savebitmap(Bitmap bmp, int value) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        OutputStream outStream = null;
        // String temp = null;
        File myDir=new File("/sdcard/Pictures/"+Integer.toString(value));
        myDir.mkdirs();
        File file = new File(myDir, "temp-" + Integer.toString(count_file) + "-" + Integer.toString(value)+".png");
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            count_file++;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

}