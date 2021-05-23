package com.example.yapyap;

public class KanbanNot {

    private int id;
    private String baslik;

    public KanbanNot(String baslik) {
        this.baslik = baslik;
    }

    public KanbanNot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }
}
