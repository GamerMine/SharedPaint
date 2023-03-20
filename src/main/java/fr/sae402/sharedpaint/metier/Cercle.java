package fr.sae402.sharedpaint.metier;

public class Cercle extends Forme {
    private int rayon;
    public Cercle(int posX, int posY, String couleur, boolean rempli, int rayon) {
        super(posX, posY, couleur, rempli);
        this.rayon = rayon;

    }
}
