package com.example.yapyap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KanbanListeleActivity extends AppCompatActivity {
    Context context = this;

    public List<String> list_parent;
    public ExpandListViewAdapter expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
    public List<String> yapiliyor_list = new ArrayList<>();
    public List<String> yapilan_list = new ArrayList<>();
    public List<String> yapilacak_list = new ArrayList<>();
    List<Notlar> yapilacak_list_gecici;
    List<Notlar> yapiliyor_list_gecici;
    List<Notlar> yapilan_list_gecici;
    int kbnnID=-1;

    KanbanNot seciliKanban;
    Button btnEkle;
    Button sil;
    TextView txtBaslik;

    NotlarSQLITEHELPER db = new NotlarSQLITEHELPER(context);
    KanbanSQLITEHELPER db2 = new KanbanSQLITEHELPER(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kanban_listele);

        db.onCreate(db.getWritableDatabase());

        Intent intent = getIntent();
        kbnnID= intent.getIntExtra("kanbanID",-1);;

        txtBaslik = (TextView) findViewById(R.id.baslikTXT);
        btnEkle = (Button) findViewById(R.id.olusturBtn);
        sil = (Button) findViewById(R.id.sillBtn);
        seciliKanban=db2.KanbanNotOku(kbnnID);
        txtBaslik.setText(seciliKanban.getBaslik());



        expandlist_view = (ExpandableListView)findViewById(R.id.expand_listview);
        listeleriAyarla();
        expand_adapter = new ExpandListViewAdapter(getApplicationContext(), list_parent, list_child);
        expandlist_view.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view.setClickable(true);


        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,KanbanNotActivity.class);
                intent.putExtra("k",kbnnID);
                intent.putExtra("d",2);
                startActivityForResult(intent,1);
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.knNotSil(kbnnID);
                db2.KanbanNotSil(kbnnID);
                finish();
            }
        });

        expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(context,KanbanNotActivity.class);
                intent.putExtra("k",kbnnID);
                if(groupPosition==0) intent.putExtra("notID",yapilacak_list_gecici.get((int) id).getId());
                if(groupPosition==1) intent.putExtra("notID",yapiliyor_list_gecici.get((int) id).getId());
                if(groupPosition==2) intent.putExtra("notID",yapilan_list_gecici.get((int) id).getId());
                intent.putExtra("d",1);
                startActivityForResult(intent,1);
                return false;
            }

        });
    }

    void listeleriAyarla(){
        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add("Yapılacak İşler");  // ilk başlığı giriyoruz
        list_parent.add("Devam Eden İşler");   // ikinci başlığı giriyoruz
        list_parent.add("Biten İşler");   // üçüncü başlığı giriyoruz




        yapilacak_list_gecici=db.nNotlariGetir(kbnnID,"Yapılacak İş");
        yapiliyor_list_gecici=db.nNotlariGetir(kbnnID,"Devam Eden İş");
        yapilan_list_gecici=db.nNotlariGetir(kbnnID,"Biten İş");

        for (int i = 0; i < yapilacak_list_gecici.size(); i++) {
            yapilacak_list.add(i, yapilacak_list_gecici.get(i).getIcerik());
        }
        for (int i = 0; i < yapiliyor_list_gecici.size(); i++) {
            yapiliyor_list.add(i, yapiliyor_list_gecici.get(i).getIcerik());
        }
        for (int i = 0; i < yapilan_list_gecici.size(); i++) {
            yapilan_list.add(i, yapilan_list_gecici.get(i).getIcerik());
        }

        list_child.put(list_parent.get(0),yapilacak_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1), yapiliyor_list); // ikinci başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(2), yapilan_list); //üçüncü başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
    }

    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        seciliKanban=db2.KanbanNotOku(kbnnID);
        txtBaslik.setText(seciliKanban.getBaslik());
        listeleriAyarla();
    }

}
