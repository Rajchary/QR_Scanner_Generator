package com.curiosity.qrgeneratorandscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Generator extends AppCompatActivity {
    EditText input,title;
    Button generateQr;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    ImageButton goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        input = findViewById(R.id.inputText);
        title = findViewById(R.id.imageName);
        generateQr =findViewById(R.id.generator);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> {
            Intent home = new Intent(Generator.this,MainActivity.class);
            startActivity(home);
        });
        generateQr.setOnClickListener(v -> {
            String data=input.getText().toString();
            if(!data.isEmpty()&&!title.getText().toString().isEmpty())
            {
                WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
                Display display= manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int dimen = Math.min(width,height);
                dimen = dimen*3/4;
                qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,dimen);
                try{
                    bitmap = qrgEncoder.encodeAsBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    byte[] byteArray = stream.toByteArray();
                    Intent showImage = new Intent(Generator.this,QRGenOutput.class);
                    showImage.putExtra("QRCode",byteArray);
                    showImage.putExtra("title",title.getText().toString());
                    startActivity(showImage);
                }
                catch(WriterException we){
                    Log.e("Writer",we.toString());
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Please provide the data",Toast.LENGTH_LONG).show();
            }
        });
    }
}