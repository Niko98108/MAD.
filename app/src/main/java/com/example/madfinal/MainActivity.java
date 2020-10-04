package com.example.madfinal;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madfinal.Database.DBHandler;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView taskList;
    TextView wlcme,hello;
    ArrayList dataList;

    ArrayAdapter adapter;
    Button newTask ;
    DBHandler db;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wlcme =findViewById(R.id.textView6);
        hello =findViewById(R.id.textView7);


        taskList = findViewById(R.id.list_item);
        db =  new DBHandler(getApplicationContext());
        dataList = db.readTask();

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,dataList);

        taskList.setAdapter(adapter);

        newTask = findViewById(R.id.new_tsk_btn);

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                String text = taskList.getItemAtPosition(i).toString();
                Intent intent = new Intent(getApplicationContext(),update_task.class);
                intent.putExtra("data",text);

                Toast.makeText(com.example.madfinal.MainActivity.this, "go", Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });


        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),new_task.class);
                startActivity(i);




            }
        });















    }
}