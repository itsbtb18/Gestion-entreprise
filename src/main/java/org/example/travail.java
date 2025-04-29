package org.example;

import java.sql.Date;

public class travail {
    private int idEmploye;
    private int idProjet;
    private Date dateAffectation;

    public travail(int idEmploye, int idProjet, Date dateAffectation) {
        this.idEmploye = idEmploye;
        this.idProjet = idProjet;
        this.dateAffectation = dateAffectation;
    }

    public int getIdEmploye() {
        return idEmploye;
    }
    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }
    public int getIdProjet() {
        return idProjet;
    }
    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }
    public Date getDateAffectation() {
        return dateAffectation;
    }
    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

}
