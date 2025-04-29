-- Creation de l'utilisateur
CREATE USER BDDAdmin IDENTIFIED BY TPAdmin;
GRANT ALL PRIVILEGES TO BDDAdmin;

-- Se connecter en tant que BDDAdmin ensuite

-- Création des tables
CREATE TABLE Departement (
                             id_departement NUMBER PRIMARY KEY,
                             nom_departement VARCHAR2(100),
                             id_responsable NUMBER
);

CREATE TABLE Employe (
                         id_employe NUMBER PRIMARY KEY,
                         nom VARCHAR2(100),
                         prenom VARCHAR2(100),
                         poste VARCHAR2(100),
                         salaire NUMBER,
                         id_departement NUMBER,
                         FOREIGN KEY (id_departement) REFERENCES Departement(id_departement)
);

CREATE TABLE Projet (
                        id_projet NUMBER PRIMARY KEY,
                        nom_projet VARCHAR2(100),
                        budget NUMBER,
                        id_departement NUMBER,
                        FOREIGN KEY (id_departement) REFERENCES Departement(id_departement)
);

CREATE TABLE Travail (
                         id_employe NUMBER,
                         id_projet NUMBER,
                         date_affectation DATE,
                         PRIMARY KEY (id_employe, id_projet),
                         FOREIGN KEY (id_employe) REFERENCES Employe(id_employe),
                         FOREIGN KEY (id_projet) REFERENCES Projet(id_projet)
);
