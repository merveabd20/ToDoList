package com.example.yapyap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class KanbanActivity extends AppCompatActivity {
    Context context = this;
    Button kaydet;

    Button git11;
    Button git22;
    Button git33;
    EditText aranacakKelime2;
    Button araBTN;
    KanbanSQLITEHELPER db = new KanbanSQLITEHELPER(context);
    NotlarSQLITEHELPER db2 = new NotlarSQLITEHELPER(context);
    ListView listemiz;
    List<KanbanNot> list;
    ArrayAdapter<String> mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ana_kanban_not);

        db.onCreate(db.getWritableDatabase());
        ekrandaGoster();

        git11 = (Button) findViewById(R.id.btnGit11);
        git33 = (Button) findViewById(R.id.btnGit33);
        kaydet=(Button) findViewById(R.id.kanbanEkle);
        araBTN = (Button) findViewById(R.id.arabutton2);
        aranacakKelime2=(EditText) findViewById(R.id.aramaTXT);

        git11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NotActivity.class);
                startActivity(intent);
            }
        });


        git33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,KanbanActivity.class);
                startActivity(intent);
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustom();
            }
        });

        araBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listemiz=(ListView) findViewById(R.id.listemiz9);
                list= db.Ara(aranacakKelime2.getText().toString());
                List<String> listBaslik = new ArrayList<>();
                for(int i =0; i <list.size(); i++){
                    listBaslik.add(i,list.get(i).getBaslik());
                }
                mAdapter=new ArrayAdapter<String>(context,R.layout.satir_layout,R.id.listMetin,listBaslik);
                listemiz.setAdapter(mAdapter);
            }
        });

        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,KanbanListeleActivity.class);
                intent.putExtra("kanbanID",list.get(position).getId());
                startActivityForResult(intent,1);
            }
        });
    }

    public void ekrandaGoster(){
        //veritabanındaki verileri ekranda göster
        listemiz=(ListView) findViewById(R.id.listemiz9);
        list= db.KanbanNotlariGetir();
        List<String> listBaslik = new ArrayList<>();
        for(int i =0; i <list.size(); i++){
            listBaslik.add(i,list.get(i).getBaslik());
        }
        mAdapter=new ArrayAdapter<String>(context,R.layout.satir_layout,R.id.listMetin,listBaslik);
        listemiz.setAdapter(mAdapter);
    }

    public void showCustom() {
        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);

        // custom dialog elemanlarını tanımla - text,button
        Button btnKaydet = (Button) dialog.findViewById(R.id.button_kaydet);
        Button btnIptal = (Button) dialog.findViewById(R.id.button_iptal);
        TextView tvBaslik = (TextView) dialog.findViewById(R.id.textview_baslik);
        EditText icerikTxt = (EditText) dialog.findViewById(R.id.icerikText);

        // tamam butonunun tıklanma olayları
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.KANBANNOTSAVE(new KanbanNot(icerikTxt.getText().toString()));
                ekrandaGoster();
                dialog.dismiss();
            }
        });

        // iptal butonunun tıklanma olayları
        btnIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        ekrandaGoster();
    }
}
