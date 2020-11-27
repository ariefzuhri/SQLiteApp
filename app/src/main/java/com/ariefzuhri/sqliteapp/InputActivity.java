package com.ariefzuhri.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {
    private EditText edtName, edtAddress;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        edtName = findViewById(R.id.edt_name);
        edtAddress = findViewById(R.id.edt_address);
        Button btnSubmit = findViewById(R.id.btn_submit);

        databaseHelper = new DatabaseHelper(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private void submitData(){
        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();

        if (name.length() > 0 && address.length() > 0){
            databaseHelper.addStudent(name, address);
            onBackPressed();
        } else Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_SHORT).show();
    }
}