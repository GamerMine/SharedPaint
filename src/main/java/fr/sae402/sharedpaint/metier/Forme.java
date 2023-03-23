package fr.sae402.sharedpaint.metier;

import java.io.Serializable;

public abstract class Forme implements Serializable {
    private int posX;
    private int posY;
    private String couleur;
    private boolean rempli;

    public Forme(int posX, int posY, String couleur, boolean rempli) {
        this.posX = posX;
        this.posY = posY;
        this.couleur = couleur;
        this.rempli = rempli;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getCouleur() {
        return couleur;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Forme forme)) return false;
        return this.posX == forme.posX && this.posY == forme.posY && this.couleur.equals(forme.couleur) && this.rempli == forme.rempli;    }
}
