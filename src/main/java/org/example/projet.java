package org.example;


public class projet {
    private int id;
    private String nom;
    private double budget;
    private int idDepartement;

    public projet(int id, String nom, double budget, int idDepartement) {
        this.id = id;
        this.nom = nom;
        this.budget = budget;
        this.idDepartement = idDepartement;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    public double getBudget() {
        return budget;
    }
    public int getIdDepartement() {
        return idDepartement;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }
}
