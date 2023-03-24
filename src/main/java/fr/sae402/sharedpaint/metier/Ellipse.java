package fr.sae402.sharedpaint.metier;

public class Ellipse extends Forme {
    private int rayonX;
    private int rayonY;
    public Ellipse(int centreX, int centreY, String couleur, boolean rempli, int rayonX, int rayonY) {
        super(centreX, centreY, couleur, rempli);
        this.rayonX = rayonX;
        this.rayonY = rayonY;
    }

    public int getRayonX() {
        return rayonX;
    }
    public int getRayonY() {return rayonY; }
}

