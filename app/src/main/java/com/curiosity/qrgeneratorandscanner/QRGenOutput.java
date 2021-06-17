package com.curiosity.qrgeneratorandscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class QRGenOutput extends AppCompatActivity {
    ImageView qrOutput;
    Button download,back;
    Bitmap bmp;
    byte[] byteArray;
    String title = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgen_output);
        byteArray = getIntent().getExtras().getByteArray("QRCode");
        bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        title = getIntent().getExtras().getString("title");
        qrOutput = findViewById(R.id.output);
        qrOutput.setImageBitmap(bmp);
        back = findViewById(R.id.back);
        download = findViewById(R.id.download);

        back.setOnClickListener(v -> {
            Intent goBack = new Intent(QRGenOutput.this,Generator.class);
            startActivity(goBack);
        });

        download.setOnClickListener(v ->{
            saveImageToGallery();
        });
    }
    private void saveImageToGallery(){
        //BitmapDrawable bitmapDrawable = (BitmapDrawable)qrOutput.getDrawable();
        try{
            MainActivity ma = new MainActivity();
            ma.checkAndRequestPermission();
        }catch(Exception e){

            Log.e("Object",e.getStackTrace().toString());
        }
        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath()+"/DCIM/MyQrs");
        boolean created = dir.mkdir();
        System.out.println(created);
        String fileName = String.format(title,System.currentTimeMillis());
        File outFile = new File(dir,fileName+".png");
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            Log.e("Error",e.toString());
        }
        bmp.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        Toast.makeText(getApplicationContext(),"Image saved successfully with name "+title+" ",
                Toast.LENGTH_LONG).show();
        try{
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }catch(Exception e){
            Log.e("Error",e.toString());
        }

    }
}