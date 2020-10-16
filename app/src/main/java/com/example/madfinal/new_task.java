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

public class new_task extends AppCompatActivity {

    TextView datetext;
    EditText nickName;
    Button createTask;
    DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        nickName = findViewById(R.id.input_nick_name);

        createTask = findViewById(R.id.task_create);
        datetext = findViewById(R.id.dateText);

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                long newId = dbHandler.addTask(nickName.getText().toString(),datetext.getText().toString());

                Toast.makeText(new_task.this, "Month Task Create", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), com.example.madfinal.MainActivity.class);
                startActivity(i);
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
                        new_task.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener, year, month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                datetext.setText(date);

            }
        };
    }
}