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

public class NotActivity extends AppCompatActivity {

    Button olustur;
    Button araBTN;
    Button kanban;
    Button notAlBTN;
    EditText aranacakKelime;
    Context context = this;
    SQLLiteHelper db = new SQLLiteHelper(context);
    ListView listemiz;
    List<HizliNot> list;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ana_not_layout);

        olustur = (Button) findViewById(R.id.olusturCBTN);
        araBTN = (Button) findViewById(R.id.arabutton);
        kanban=(Button) findViewById(R.id.kanbanAl);
        notAlBTN = (Button) findViewById(R.id.notAl);
        aranacakKelime=(EditText) findViewById(R.id.aramaTXT);


        olustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustom();
            }
        });

        notAlBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NotActivity.class);
                startActivity(intent);

            }
        });


        kanban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,KanbanActivity.class);
                startActivity(intent);

            }
        });


        araBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listemiz=(ListView) findViewById(R.id.listemiz9);
                list= db.Ara(aranacakKelime.getText().toString());
                List<String> listBaslik = new ArrayList<>();
                for(int i =0; i <list.size(); i++){
                    listBaslik.add(i,list.get(i).getBaslik());
                }
                mAdapter=new ArrayAdapter<String>(context,R.layout.satir_layout,R.id.listMetin,listBaslik);
                listemiz.setAdapter(mAdapter);
            }
        });

        db.onCreate(db.getWritableDatabase());
        ekrandaGoster();

        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NotEkleActivity.class);
                intent.putExtra("not",list.get(position).getId());
                startActivityForResult(intent,1);
            }
        });
    }

    public void ekrandaGoster(){
        //veritabanındaki verileri ekranda göster
        listemiz=(ListView) findViewById(R.id.listemiz9);
        list= db.notlariGetir();
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
                db.NOTSAVE(new HizliNot(icerikTxt.getText().toString(),""));
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

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        ekrandaGoster();
    }
}
