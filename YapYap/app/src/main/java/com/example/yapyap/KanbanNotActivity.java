package com.example.yapyap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class KanbanNotActivity  extends AppCompatActivity {
    EditText txtBaslik,txtIcerik;
    Context context =this;
    Button silBTN;
    Button guncelleBTN;
    NotlarSQLITEHELPER db = new NotlarSQLITEHELPER(context);
    KanbanSQLITEHELPER db2 = new KanbanSQLITEHELPER(context);
    RadioGroup rg;
    RadioButton radioButton;
    Notlar seciliNot = new Notlar();
    KanbanNot kanbanNot = new KanbanNot();
    int not_id=-1;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kanban_not_layout);
        txtBaslik= (EditText) findViewById(R.id.nBaslik);
        txtIcerik= (EditText) findViewById(R.id.nicerikText);
        silBTN= (Button) findViewById(R.id.nbtnSil);
        guncelleBTN= (Button) findViewById(R.id.nbtnGuncelle);
        rg=(RadioGroup) findViewById(R.id.radioDurum);

        Intent intent = getIntent();
        int a= intent.getIntExtra("k",-1);//kanban id
        int d = intent.getIntExtra("d",-1);//durum id
        not_id = intent.getIntExtra("notID",-1);// notID

        kanbanNot = db2.KanbanNotOku(a);
        Durum(d);

        txtBaslik.setText(kanbanNot.getBaslik());
        txtIcerik.setText(seciliNot.getIcerik());


        silBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d==1){//sil
                    db.nNotSil(seciliNot);
                    finish();
                }
                if(d==2){
                    finish();
                }
            }
        });

        guncelleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d==1) {//güncelle
                    int selectedRd= rg.getCheckedRadioButtonId();
                    radioButton= (RadioButton) findViewById(selectedRd);

                    seciliNot.setDurum(radioButton.getText().toString());
                    kanbanNot.setBaslik(txtBaslik.getText().toString());
                    seciliNot.setIcerik(txtIcerik.getText().toString());
                    seciliNot.setId(not_id);
                    db.nNotGuncelle(seciliNot);
                    db2.KanbanNotGuncelle(kanbanNot);
                }
                if(d==2){//ekle
                    int selectedRd= rg.getCheckedRadioButtonId();
                    radioButton= (RadioButton) findViewById(selectedRd);

                    seciliNot.setKanbanNotID(kanbanNot.getId());
                    seciliNot.setIcerik(txtIcerik.getText().toString());
                    seciliNot.setDurum(radioButton.getText().toString());
                    db.nNOTSAVE(seciliNot);
                    kanbanNot.setBaslik(txtBaslik.getText().toString());
                    db2.KanbanNotGuncelle(kanbanNot);
                }
            }
        });
    }

    public void Durum(int a){
        if(a==1){//olanı değiştir
            seciliNot=db.nNotOku(not_id);
            if(seciliNot.getDurum().equals("Yapılacak İş")){
                radioButton = (RadioButton) findViewById(R.id.durum1);
                radioButton.setChecked(true);
            }
            if(seciliNot.getDurum().equals("Devam Eden İş")){
                radioButton = (RadioButton) findViewById(R.id.durum2);
                radioButton.setChecked(true);
            }
            if(seciliNot.getDurum().equals("Biten İş")){
                radioButton = (RadioButton) findViewById(R.id.durum3);
                radioButton.setChecked(true);
            }
        }
        if(a==2){//yeni oluştur
            seciliNot = new Notlar();
        }
    }
}
