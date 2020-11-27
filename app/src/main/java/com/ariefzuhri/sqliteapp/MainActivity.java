package com.ariefzuhri.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Map<String, String>> arrayList;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        databaseHelper = new DatabaseHelper(this);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int id = Integer.parseInt(arrayList.get(i).get("id"));
                showDeleteConfirm(id);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        arrayList = databaseHelper.getAllStudents();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList,
                android.R.layout.simple_list_item_2, new String[]{"name", "address"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }

    private void showDeleteConfirm(final int id){
        new AlertDialog.Builder(this)
                .setTitle("Hapus data")
                .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(id);
                    }
                })
                .setNegativeButton("Tidak", null)
                .create().show();
    }

    private void delete(int id){
        databaseHelper.delete(id);
        arrayList.clear();
        loadData();
        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
    }
}