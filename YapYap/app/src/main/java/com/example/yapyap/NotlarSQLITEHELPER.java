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

public class NotlarSQLITEHELPER extends SQLiteOpenHelper {
    private static final Integer database_VERSION=1;
    private static final String database_NAME = "NotDB";//database'in adı
    private static final String table_NAME="Notlar"; //tablonun adı
    private static final String not_ID="id";//alanların adı
    private static final String not_ICERIK="notIcerik";
    private static final String not_DURUM="durum";
    private static final String not_KANBANID="KanbanNotID";
    private static final String[] COLUMNS = {not_ID,not_ICERIK,not_KANBANID,not_DURUM};
    private static final String CREATE_NOT_TABLE="CREATE TABLE "
            + table_NAME +"("
            + not_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + not_ICERIK +" TEXT, " +
            not_KANBANID + " INTEGER, " +
            not_DURUM + " TEXT )" ;

    public NotlarSQLITEHELPER(Context context) {
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

    public void nNOTSAVE(Notlar not){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(not_ICERIK,not.getIcerik());
        values.put(not_KANBANID,not.getKanbanNotID());
        values.put(not_DURUM,not.getDurum());
        db.insert(table_NAME,null,values);
        db.close();
    }

    public List<Notlar> nNotlariGetir(int id){
        List<Notlar> notlar = new ArrayList<>();
        Notlar not;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor dolas= db.query(table_NAME,COLUMNS,"KanbanNotID = ?",new String[] {String.valueOf(id)},null,null,null);
        if(dolas.moveToFirst()){
            do{
                not = new Notlar();
                not.setId(Integer.parseInt(dolas.getString(0)));
                not.setIcerik(dolas.getString(1));
                not.setKanbanNotID(Integer.parseInt(dolas.getString(2)));
                not.setDurum(dolas.getString(3));
                notlar.add(not);
            }while(dolas.moveToNext());
        }
        db.close();
        return  notlar;
    }

    public List<Notlar> nNotlariGetir(int id,String durum){
        List<Notlar> notlar = new ArrayList<>();
        Notlar not;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor dolas= db.query(table_NAME,COLUMNS,"KanbanNotID = ? AND durum = ? ",new String[] { String.valueOf(id) , durum },null,null,null);
        if(dolas.moveToFirst()){
            do{
                not = new Notlar();
                not.setId(Integer.parseInt(dolas.getString(0)));
                not.setIcerik(dolas.getString(1));
                not.setKanbanNotID(Integer.parseInt(dolas.getString(2)));
                not.setDurum(dolas.getString(3));
                notlar.add(not);
            }while(dolas.moveToNext());
        }
        db.close();
        return  notlar;
    }



    public Notlar nNotOku(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_NAME,COLUMNS," id = ?",new String[] {String.valueOf(id)},null,null,null);
        if(cursor != null){
            cursor.moveToFirst();        }
        Notlar not = new Notlar();
        not.setId(Integer.parseInt(cursor.getString(0)));
        not.setIcerik(cursor.getString(1));
        not.setKanbanNotID(Integer.parseInt(cursor.getString(2)));
        not.setDurum(cursor.getString(3));
        db.close();
        return not;
    }

    public void nNotSil(Notlar not){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_NAME,not_ID+" = ?",new String[] {String.valueOf(not.getId())});
        db.close();
        db.close();
    }

    public void knNotSil(int kbnID){//kanbanın tüm notlarını sil
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_NAME,not_KANBANID+" = ?",new String[] {String.valueOf(kbnID)});
        db.close();
    }

    public int nNotGuncelle(Notlar not){
        //geri dönüş değeri etkilenen kayıt sayısıdır.
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("notIcerik",not.getIcerik());
        values.put("KanbanNotID",not.getKanbanNotID());
        values.put("durum",not.getDurum());
        int i =db.update(table_NAME,values,not_ID+" = ?",new String[] {String.valueOf(not.getId())});
        db.close();
        return i;
    }

}
