package com.jobdelas.jobdelas.model;

public class Amizade {
    private int id;
    private int id_usuario1;
    private int id_usuario2;
    private boolean status; // true se aceito, false se pendente

    // Construtores, getters e setters

    public Amizade(int id, int id_usuario1, int id_usuario2, boolean status) {
        this.id = id;
        this.id_usuario1 = id_usuario1;
        this.id_usuario2 = id_usuario2;
        this.status = status;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario1() {
        return id_usuario1;
    }

    public void setId_usuario1(int id_usuario1) {
        this.id_usuario1 = id_usuario1;
    }

    public int getId_usuario2() {
        return id_usuario2;
    }

    public void setId_usuario2(int id_usuario2) {
        this.id_usuario2 = id_usuario2;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}


