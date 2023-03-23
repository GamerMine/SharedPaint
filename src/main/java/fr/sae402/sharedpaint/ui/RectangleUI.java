package fr.sae402.sharedpaint.ui;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class RectangleUI extends Rectangle {
    private fr.sae402.sharedpaint.metier.Rectangle rectangle;

    public RectangleUI(int posX, int posY, String couleur, boolean rempli, int longueur, int largeur) {
        this.rectangle = new fr.sae402.sharedpaint.metier.Rectangle( posX,  posY,  couleur,  rempli,  longueur,  largeur);
        this.setX(posX);
        this.setY(posY);
        this.setHeight(largeur);
        this.setWidth(longueur);
    }

    public RectangleUI(fr.sae402.sharedpaint.metier.Rectangle rectangle) {
        this.rectangle = rectangle;
        this.setX(rectangle.getPosX());
        this.setY(rectangle.getPosY());
        this.setHeight(rectangle.getLargeur());
        this.setWidth(rectangle.getLongueur());
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


    public fr.sae402.sharedpaint.metier.Rectangle getElementRectangle() {
        return this.rectangle;
    }
}
