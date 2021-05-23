package com.example.yapyap;

public class Notlar {
    private int id;
    private String icerik;
    private int KanbanNotID;
    private String durum;

    public Notlar(String icerik, int kanbanNotID,String durum) {
        this.icerik = icerik;
        this.KanbanNotID = kanbanNotID;
        this.durum=durum;
    }

    public Notlar() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public int getKanbanNotID() {
        return KanbanNotID;
    }

    public void setKanbanNotID(int kanbanNotID) {
        KanbanNotID = kanbanNotID;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
