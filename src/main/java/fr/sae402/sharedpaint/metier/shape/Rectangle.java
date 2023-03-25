package fr.sae402.sharedpaint.metier.shape;

public class Rectangle extends Forme{

    private int longueur;
    private int largeur;

    private Boolean rempli;

    public Rectangle(int posX, int posY, String couleur, boolean rempli, int longueur, int largeur) {
        super(posX, posY, couleur, rempli);
        this.rempli = rempli;
        this.longueur = longueur;
        this.largeur = largeur;
    }
    public boolean getRempli(){return this.rempli;}
    public int getLargeur() {
        return largeur;
    }

    public int getLongueur() {
        return longueur;
    }
}
