package org.example;

public class departement {
    private int id;
    private String nom;
    private int idResponsable;

    public departement(int id, String nom, int idResponsable) {
        this.id = id;
        this.nom = nom;
        this.idResponsable = idResponsable;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getIdResponsable() {
        return idResponsable;
    }
    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }
}
