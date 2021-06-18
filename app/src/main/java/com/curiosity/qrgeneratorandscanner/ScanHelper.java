package com.curiosity.qrgeneratorandscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class ScanHelper extends AppCompatActivity {
    Button camera, galley;
    boolean cameraView = false;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_helper);

        camera = findViewById(R.id.camera);
        galley = findViewById(R.id.gallery);

        camera.setOnClickListener(v -> {
            cameraView = true;
            IntentIntegrator intentIntegrator = new IntentIntegrator(ScanHelper.this);
            intentIntegrator.setPrompt("For flash use volume up key");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(Capture.class);
            intentIntegrator.initiateScan();
        });
        galley.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1000);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (cameraView) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult.getContents() != null) {
                if(isValidUrl(intentResult.getContents()))
                    openBrowser(intentResult.getContents());
                else
                    openActivity(intentResult.getContents());
            } else
                Toast.makeText(getApplicationContext(), "You haven't scanned anything", Toast.LENGTH_SHORT).show();
        } else {
            if (resultCode == RESULT_OK) {

                try {

                    assert data != null;
                    final Uri imageUri = data.getData();

                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    try {
                        String contents;
                        int[] intArray = new int[selectedImage.getWidth() * selectedImage.getHeight()];
                        selectedImage.getPixels(intArray, 0, selectedImage.getWidth(), 0, 0, selectedImage.getWidth(), selectedImage.getHeight());
                        LuminanceSource source = new RGBLuminanceSource(selectedImage.getWidth(), selectedImage.getHeight(), intArray);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Reader reader = new MultiFormatReader();
                        Result result = reader.decode(bitmap);
                        contents = result.getText();
                        if(isValidUrl(contents))
                        {
                            openBrowser(contents);
                        }
                        else
                        {
                            openActivity(contents);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(ScanHelper.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(ScanHelper.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean isValidUrl(String data)
    {
        try{
            new URL(data).toURI();
            return true;
        } catch(Exception e){
            return false;
        }
    }
    private void openBrowser(String url)
    {
        Toast.makeText(getApplicationContext(),"Opening URl in browser",Toast.LENGTH_LONG).show();
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
    }
    private void openActivity(String contents)
    {
        Intent showResult = new Intent(ScanHelper.this, Scanner.class);
        showResult.putExtra("content", contents);
        startActivity(showResult);
    }
}