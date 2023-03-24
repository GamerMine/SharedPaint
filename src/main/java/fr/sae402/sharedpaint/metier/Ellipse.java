package fr.sae402.sharedpaint.metier;

public class Ellipse extends Forme {
    private int rayonX;
    private int rayonY;
    private Boolean rempli;
    public Ellipse(int posX, int posY, String couleur, boolean rempli, int rayonX, int rayonY) {
        super(posX, posY, couleur, rempli);
        this.rayonX = rayonX;
        this.rayonY = rayonY;
        this.rempli = rempli;
    }

    public int getRayonX() {
        return rayonX;
    }
    public int getRayonY() {return rayonY; }
    public Boolean getRempli() {return rempli;}
}

