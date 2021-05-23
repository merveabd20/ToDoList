package com.example.yapyap;

public class HizliNot {
    private int id;
    private String notIcerik;
    private String baslik;

    public HizliNot() {
    }

    public HizliNot(String baslik, String not) {
        this.baslik = baslik;
        this.notIcerik = not;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNot() {
        return notIcerik;
    }

    public void setNot(String not) {
        this.notIcerik = not;
    }

}
