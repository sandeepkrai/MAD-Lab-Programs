package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAdd, buttonSub, buttonMul, buttonDiv;
    Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;

    EditText editText1, editText2;
    TextView textView;
    int int1, int2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.but_add);
        buttonSub = findViewById(R.id.but_sub);
        buttonMul = findViewById(R.id.but_mul);
        buttonDiv = findViewById(R.id.but_div);
        num0 = findViewById(R.id.num0);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);



        editText1 = findViewById(R.id.number1);
        editText2 = findViewById(R.id.number2);
        textView = findViewById(R.id.answer);

        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
    }

    public int getEditText(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter valid number", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        EditText currentEditText;
        if (editText1.hasFocus()) {
            currentEditText = editText1;
        } else if (editText2.hasFocus()) {
            currentEditText = editText2;
        } else {
            // If neither has focus, set a default (you can customize this part)
            currentEditText = editText1;
        }

        // Handle number buttons
        int id = view.getId();
        if (id == R.id.num0) {
            currentEditText.append("0");
        } else if (id == R.id.num1) {
            currentEditText.append("1");
        } else if (id == R.id.num2) {
            currentEditText.append("2");
        } else if (id == R.id.num3) {
            currentEditText.append("3");
        } else if (id == R.id.num4) {
            currentEditText.append("4");
        } else if (id == R.id.num5) {
            currentEditText.append("5");
        } else if (id == R.id.num6) {
            currentEditText.append("6");
        } else if (id == R.id.num7) {
            currentEditText.append("7");
        } else if (id == R.id.num8) {
            currentEditText.append("8");
        } else if (id == R.id.num9) {
            currentEditText.append("9");
        }
        int1 = getEditText(editText1);
        int2 = getEditText(editText2);
        if (id == R.id.but_add) {
            textView.setText("Answer = " + (int1 + int2));
        } else if (id == R.id.but_sub) {
            textView.setText("Answer = " + (int1 - int2));
        } else if (id == R.id.but_mul) {
            textView.setText("Answer = " + (int1 * int2));
        } else if (id == R.id.but_div) {
            if (int2 == 0) {
                Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
            } else {
                textView.setText("Answer = " + ((float) int1 / int2));
            }
        }

    }
}
