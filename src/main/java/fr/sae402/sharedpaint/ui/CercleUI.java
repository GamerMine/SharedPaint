package fr.sae402.sharedpaint.ui;

import fr.sae402.sharedpaint.metier.Cercle;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class CercleUI extends Circle {
    private Cercle cercle;

    public CercleUI(int posX, int posY,String couleur,boolean rempli, int radius) {
        this.cercle = new Cercle(posX, posY, couleur, rempli, radius);
        this.setCenterX(posX);
        this.setCenterY(posY);
        this.setRadius(radius);
    }

    public int getX() {
        return (int) this.getCenterX();
    }
    public int getY() {
        return (int) this.getCenterY();
    }

    public Point2D getCoordonnee(){
        return new Point2D(this.getCenterX(), this.getCenterY());
    }

    public Cercle getElementCercle() {
        return this.cercle;
    }
}
