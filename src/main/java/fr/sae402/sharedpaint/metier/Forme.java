package fr.sae402.sharedpaint.metier;

import java.io.Serializable;

public abstract class Forme implements Serializable {
    protected int posX;
    protected int posY;
    protected String couleur;
    protected boolean rempli;

    public Forme(int posX, int posY, String couleur, boolean rempli) {
        this.posX = posX;
        this.posY = posY;
        this.couleur = couleur;
        this.rempli = rempli;
    }
}
