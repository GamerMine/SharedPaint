package fr.sae402.sharedpaint.metier;

public class Ligne extends Forme{
    private int posXFin;
    private int posYFin;
    public Ligne(int posX, int posY, String couleur, boolean rempli, int posXFin, int posYFin) {
        super(posX, posY, couleur, rempli);
        this.posXFin = posXFin;
        this.posYFin = posYFin;
    }
}
