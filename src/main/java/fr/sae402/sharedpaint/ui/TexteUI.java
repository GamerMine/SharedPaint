package fr.sae402.sharedpaint.ui;

import fr.sae402.sharedpaint.metier.Texte;
import javafx.geometry.Point2D;
import javafx.scene.text.Text;

public class TexteUI extends Text {
    private Texte texte;

    public TexteUI(int posX, int posY, String couleur, String texte ) {
        new Texte( posX,  posY,  couleur,  texte );
        this.setX(posX);
        this.setY(posY);
        this.setText(texte);
    }

    public Point2D getCoordonnee() {
        return new Point2D(this.getX(), this.getY());
    }

    public String getTexte() {
        return this.getText();
    }
}
