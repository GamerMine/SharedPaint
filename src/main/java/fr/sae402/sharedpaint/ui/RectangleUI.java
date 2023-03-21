package fr.sae402.sharedpaint.ui;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class RectangleUI extends Rectangle {
    private fr.sae402.sharedpaint.metier.Rectangle rectangle;

    public RectangleUI(int posX, int posY, String couleur, boolean rempli, int longueur, int largeur) {
        new fr.sae402.sharedpaint.metier.Rectangle( posX,  posY,  couleur,  rempli,  longueur,  largeur);
        this.setX(posX);
        this.setY(posY);
        this.setHeight(longueur);
        this.setWidth(largeur);
    }
    public int getLongueur() {
        return (int)this.getHeight();
    }
    public int getLargeur() {
        return (int)this.getWidth();
    }
    public Point2D getCoordonnee() {
        return new Point2D(this.getX(), this.getY());
    }

}
