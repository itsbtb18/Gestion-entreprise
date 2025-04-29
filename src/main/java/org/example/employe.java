package org.example;


public class employe {
    private int id;
    private String nom;
    private String prenom;
    private String poste;
    private double salaire;
    private int idDepartement;

    public employe(int id, String nom, String prenom, String poste, double salaire, int idDepartement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.salaire = salaire;
        this.idDepartement = idDepartement;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getPoste() {
        return poste;
    }
    public double getSalaire() {
        return salaire;
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
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setPoste(String poste) {
        this.poste = poste;
    }
    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }


}
