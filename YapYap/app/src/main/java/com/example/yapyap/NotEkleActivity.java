package com.example.yapyap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NotEkleActivity extends AppCompatActivity {
    EditText txtBaslik,txtIcerik;
    Button silBTN;
    Button guncelleBTN;
    SQLLiteHelper db;
    Context context =this;
    HizliNot seciliNot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_layout);

        txtBaslik= (EditText) findViewById(R.id.eTBaslikHizliNot);
        txtIcerik= (EditText) findViewById(R.id.icerikText);
        silBTN= (Button) findViewById(R.id.btnSil);
        guncelleBTN= (Button) findViewById(R.id.btnGuncelle);


        db=new SQLLiteHelper(context);

        Intent intent = getIntent();
        int id= intent.getIntExtra("not",-1);
        seciliNot=db.notOku(id);
        txtBaslik.setText(seciliNot.getBaslik());
        txtIcerik.setText(seciliNot.getNot());


        silBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.notSil(seciliNot);
                finish();
            }
        });

        guncelleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seciliNot.setBaslik(txtBaslik.getText().toString());
                seciliNot.setNot(txtIcerik.getText().toString());
                db.notGuncelle(seciliNot);
            }
        });
    }
}
