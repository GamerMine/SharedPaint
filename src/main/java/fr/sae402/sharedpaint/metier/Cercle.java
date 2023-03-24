package fr.sae402.sharedpaint.metier;

public class Cercle extends Forme {
    private int rayon;

    private Boolean rempli;
    public Cercle(int posX, int posY, String couleur, boolean rempli, int rayon) {
        super(posX, posY, couleur, rempli);
        this.rayon = rayon;
        this.rempli = rempli;
    }

    public int getRayon() {
        return rayon;
    }

    public Boolean getRempli(){return rempli;}
}
