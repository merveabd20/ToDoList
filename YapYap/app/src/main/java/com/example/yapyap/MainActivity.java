package com.example.yapyap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    Button git1;
    Button git2;
    Button git3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        git1 = (Button) findViewById(R.id.btnGit1);
        git3 = (Button) findViewById(R.id.btnGit3);


        git1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NotActivity.class);
                startActivity(intent);
            }
        });


        git3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,KanbanActivity.class);
                startActivity(intent);
            }
        });
    }
}