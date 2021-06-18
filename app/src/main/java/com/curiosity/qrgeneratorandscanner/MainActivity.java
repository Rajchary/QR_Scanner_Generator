package com.curiosity.qrgeneratorandscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    Button generate,scan;
    public static final int STORAGE_REQUEST = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermission();
        //Initializing objects
        generate = findViewById(R.id.generate);
        scan = findViewById(R.id.scan);

        generate.setOnClickListener(v -> {
            Intent generateQr = new Intent(MainActivity.this,Generator.class);
            startActivity(generateQr);
        });
        scan.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
            intentIntegrator.setPrompt("For flash use volume up key");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(Capture.class);
            intentIntegrator.initiateScan();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null)
        {
            Intent showResult = new Intent(MainActivity.this,Scanner.class);
            showResult.putExtra("content",intentResult.getContents());
            startActivity(showResult);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"You haven't scanned anything",Toast.LENGTH_SHORT).show();
        }
    }
    public void requestNeededPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA)||
                ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)||ActivityCompat.shouldShowRequestPermissionRationale(
                        MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        )
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for saving your qr code images")
                    .setPositiveButton("Allow", (dialog, which) -> ActivityCompat.requestPermissions(
                            MainActivity.this,new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            STORAGE_REQUEST))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(
                    this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            }, STORAGE_REQUEST);
        }

    }
    public void checkAndRequestPermission()
    {
        if(PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) + ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            requestNeededPermissions();
        }
    }
}