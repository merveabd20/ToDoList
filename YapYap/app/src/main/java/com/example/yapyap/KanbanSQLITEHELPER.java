package com.example.yapyap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class KanbanSQLITEHELPER extends SQLiteOpenHelper {
    private static final Integer database_VERSION=1;
    private static final String database_NAME = "NotDB";
    private static final String table_NAME="KanbanNot";
    private static final String KanbanNot_ID="id";
    private static final String KanbanNot_BASLIK="baslik";
    private static final String[] COLUMNS = {KanbanNot_ID,KanbanNot_BASLIK};
    private static final String CREATE_NOT_TABLE="CREATE TABLE "
            + table_NAME +"("
            + KanbanNot_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KanbanNot_BASLIK +" TEXT ) " ;

    public KanbanSQLITEHELPER(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //veritabanı ilk açılduğında çalışan ilk metot
        try {
            db.execSQL(CREATE_NOT_TABLE);

        } catch (SQLiteException ex) {
            Log.v("Tablo olusturma hatasi tespit edildi", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_NAME);
        onCreate(db);
    }

    //fonskyion isimlerini değiştir
    public void KANBANNOTSAVE(KanbanNot not){//buradaki sınıf isimlerini kendi sınıf isimlerin yap
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KanbanNot_BASLIK,not.getBaslik());//değişir
        db.insert(table_NAME,null,values);
        db.close();
    }

    public List<KanbanNot> KanbanNotlariGetir(){
        List<KanbanNot> notlar = new ArrayList<>();
        KanbanNot not;
        String query ="SELECT * FROM "+table_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor dolas= db.rawQuery(query,null);
        if(dolas.moveToFirst()){
            do{
                not = new KanbanNot();
                not.setId(Integer.parseInt(dolas.getString(0)));
                not.setBaslik(dolas.getString(1));
                notlar.add(not);
            }while(dolas.moveToNext());
        }
        db.close();
        return  notlar;
    }
    public List<KanbanNot> Ara(String ara){
        List<KanbanNot> notlar = new ArrayList<>();
        KanbanNot not;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql = "SELECT * FROM " + table_NAME + " WHERE "+ KanbanNot_BASLIK + " LIKE '%"+ara+"%'";
        Cursor dolas = db.rawQuery(sql,null);
        if(dolas.moveToFirst()){
            do{
                not = new KanbanNot();
                not.setId(Integer.parseInt(dolas.getString(0)));
                not.setBaslik(dolas.getString(1));
                notlar.add(not);
            }while(dolas.moveToNext());
        }
        db.close();
        return  notlar;
    }

    public KanbanNot KanbanNotOku(int id){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.query(table_NAME,COLUMNS," id = ?",new String[] {String.valueOf(id)},null,null,null);
        if(cursor != null){
            cursor.moveToFirst();        }
        KanbanNot not = new KanbanNot();
        not.setId(Integer.parseInt(cursor.getString(0)));
        not.setBaslik(cursor.getString(1));
        db.close();
        return not;
    }

    public void KanbanNotSil(int not){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_NAME,KanbanNot_ID+" = ?",new String[] {String.valueOf(not)});
        db.close();
    }

    public int KanbanNotGuncelle(KanbanNot not){//sınıfları değiştirmeyi unutma
        //geri dönüş değeri etkilenen kayıt sayısıdır.
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("baslik",not.getBaslik());
        int i =db.update(table_NAME,values,KanbanNot_ID+" = ?",new String[] {String.valueOf(not.getId())});
        db.close();
        return i;
    }
}