package fr.sae402.sharedpaint.metier;

public class Rectangle extends Forme{

    private int longueur;
    private int largeur;

    public Rectangle(int posX, int posY, String couleur, boolean rempli, int longueur, int largeur) {
        super(posX, posY, couleur, rempli);
        this.longueur = longueur;
        this.largeur = largeur;
    }
}
