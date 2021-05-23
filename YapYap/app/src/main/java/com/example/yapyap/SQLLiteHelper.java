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

public class SQLLiteHelper extends SQLiteOpenHelper {
    private static final Integer database_VERSION=1;
    private static final String database_NAME = "NotDB";//database'in adı
    private static final String table_NAME="hizliNotlar"; //tablonun adı
    private static final String not_ID="id";//alanların adı
    private static final String not_ICERIK="notIcerik";
    private static final String not_BASLIK="baslik";
    private static final String[] COLUMNS = {not_ID,not_BASLIK,not_ICERIK};
    private static final String CREATE_NOT_TABLE="CREATE TABLE "
            + table_NAME +"("
            + not_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + not_BASLIK +" TEXT, " +
            not_ICERIK + " TEXT )" ;

    public SQLLiteHelper(Context context) {
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

    public void NOTSAVE(HizliNot not){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(not_BASLIK,not.getBaslik());
        values.put(not_ICERIK,not.getNot());
        db.insert(table_NAME,null,values);
        db.close();
    }

    public List<HizliNot> notlariGetir(){
        List<HizliNot> notlar = new ArrayList<>();
        HizliNot not;
        String query ="SELECT * FROM "+table_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor dolas= db.rawQuery(query,null);
        if(dolas.moveToFirst()){
            do{
                not = new HizliNot();
                not.setId(Integer.parseInt(dolas.getString(0)));
                not.setBaslik(dolas.getString(1));
                not.setNot(dolas.getString(2));
                notlar.add(not);
            }while(dolas.moveToNext());
        }
        db.close();
        return  notlar;
    }

    public HizliNot notOku(int id){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.query(table_NAME,COLUMNS," id = ?",new String[] {String.valueOf(id)},null,null,null);
        if(cursor != null){
            cursor.moveToFirst();        }
        HizliNot not = new HizliNot();
        not.setId(Integer.parseInt(cursor.getString(0)));
        not.setBaslik(cursor.getString(1));
        not.setNot(cursor.getString(2));
        db.close();
        return not;
    }

    public List<HizliNot> Ara(String ara){
        List<HizliNot> notlar = new ArrayList<>();
        HizliNot not;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql = "SELECT * FROM " + table_NAME + " WHERE "+ not_BASLIK + " LIKE '%"+ara+"%'";
        Cursor dolas = db.rawQuery(sql,null);
        if(dolas.moveToFirst()){
            do{
                not = new HizliNot();
                not.setId(Integer.parseInt(dolas.getString(0)));
                not.setBaslik(dolas.getString(1));
                not.setNot(dolas.getString(2));
                notlar.add(not);
            }while(dolas.moveToNext());
        }
        db.close();
        return  notlar;
    }

    public void notSil(HizliNot not){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_NAME,not_ID+" = ?",new String[] {String.valueOf(not.getId())});
        db.close();
    }

    public int notGuncelle(HizliNot not){
        //geri dönüş değeri etkilenen kayıt sayısıdır.
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("baslik",not.getBaslik());
        values.put("notIcerik",not.getNot());
        int i =db.update(table_NAME,values,not_ID+" = ?",new String[] {String.valueOf(not.getId())});
        db.close();
        return i;
    }
}
