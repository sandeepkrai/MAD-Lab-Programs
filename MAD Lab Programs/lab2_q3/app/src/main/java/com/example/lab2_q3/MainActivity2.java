package com.example.lab2_q3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity2 extends AppCompatActivity {
    Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Get references to UI elements
        TextInputEditText editText = findViewById(R.id.editText);
        Button okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Set click listener for the "OK" button
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the URL from the TextInputEditText
                String url = editText.getText().toString();

                // Check if the URL is not empty
                if (!url.isEmpty()) {
                    // Create an Intent to open the URL in a browser
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }
}