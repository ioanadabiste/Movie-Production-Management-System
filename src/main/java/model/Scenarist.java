package model;

import java.io.Serializable;
import java.util.UUID;

public class Scenarist implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nume;
    private String prenume;
    private int anNastere;
    private String nationalitate;

    public Scenarist() {
        this.id = UUID.randomUUID().toString();
    }

    public Scenarist(String nume, String prenume, int anNastere, String nationalitate) {
        this.id = UUID.randomUUID().toString();
        this.nume = nume;
        this.prenume = prenume;
        this.anNastere = anNastere;
        this.nationalitate = nationalitate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getAnNastere() {
        return anNastere;
    }

    public void setAnNastere(int anNastere) {
        this.anNastere = anNastere;
    }

    public String getNationalitate() {
        return nationalitate;
    }

    public void setNationalitate(String nationalitate) {
        this.nationalitate = nationalitate;
    }

    public String getNumeComplet() {
        return prenume + " " + nume;
    }

    @Override
    public String toString() {
        return getNumeComplet();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Scenarist scenarist = (Scenarist) obj;
        return id.equals(scenarist.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}