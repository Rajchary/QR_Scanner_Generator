package com.curiosity.qrgeneratorandscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Scanner extends AppCompatActivity {
    TextView result;
    Button copy;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        String content = getIntent().getExtras().getString("content");

        result = findViewById(R.id.result);
        result.setText(content);
        copy = findViewById(R.id.copy);
        copy.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext().getSystemService(
                    Context.CLIPBOARD_SERVICE
            );
            ClipData clipData = ClipData.newPlainText("text",content);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(getApplicationContext(),"Text Copied",Toast.LENGTH_LONG).show();
        });
        back = findViewById(R.id.goBackScreen);
        back.setOnClickListener(v -> {
           Intent backScreen = new Intent(Scanner.this,MainActivity.class);
           startActivity(backScreen);
        });
    }
}
