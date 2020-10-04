package com.example.madfinal;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.madfinal.Database.DBHandler;
import java.util.Calendar;
import java.util.List;

public class update_task extends AppCompatActivity {
    TextView datetext;
    EditText nickName,test;
    Button updateTask, deleteTask;
    DatePickerDialog.OnDateSetListener setListener;
//    String pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        nickName = findViewById(R.id.updt_nick_name);
        updateTask = findViewById(R.id.update_task);
        deleteTask = findViewById(R.id.delete_task);
        datetext = findViewById(R.id.dateText);

        test = findViewById(R.id.test_1);
        test.setEnabled(false);



        try {
            final String value = getIntent().getExtras().getString("data");
            nickName.setText(value);
            final  DBHandler dbHandler = new DBHandler(getApplicationContext());

            List data = dbHandler.readTask(nickName.getText().toString());

            test.setText(data.get(2).toString());
            nickName.setText(data.get(0).toString());
            datetext.setText(data.get(1).toString());

            updateTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHandler dbHandler1 = new DBHandler(getApplicationContext());

                    boolean status = dbHandler1.updateTask(test.getText().toString(),nickName.getText().toString(), datetext.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(intent);


                    if (status) {
                        Toast.makeText(update_task.this, "Task Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(update_task.this, " Updated Failed", Toast.LENGTH_SHORT).show();


                    }


                }
            });
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                dbHandler.deleteTask(nickName.getText().toString());

                Toast.makeText(update_task.this, " Task Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        update_task.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener, year, month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month =month+1;
                String date = day+"/"+month+"/"+year;
                datetext.setText(date);

            }
        };

    }


}
