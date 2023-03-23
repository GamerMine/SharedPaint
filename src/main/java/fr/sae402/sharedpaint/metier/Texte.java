package fr.sae402.sharedpaint.metier;

public class Texte extends Forme {
    private String texte;
    public Texte(int posX, int posY, String couleur, String texte ) {
        super(posX, posY, couleur, true);
        this.texte = texte;
    }

    public String getTexte() {
        return texte;
    }
}