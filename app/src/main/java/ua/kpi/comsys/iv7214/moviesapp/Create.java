package ua.kpi.comsys.iv7214.moviesapp;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class Create extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        EditText title = findViewById(R.id.inputTitle);
        EditText type = findViewById(R.id.inputType);
        EditText year = findViewById(R.id.inputYear);

        Button add = (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(MainActivity.TITLE, title.getText().toString());
                data.putExtra(MainActivity.TYPE, type.getText().toString());
                data.putExtra(MainActivity.YEAR, year.getText().toString());
                setResult(2,data);
                finish();
            }
        });

    }
}
