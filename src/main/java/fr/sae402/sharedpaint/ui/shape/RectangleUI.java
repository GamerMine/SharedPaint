package fr.sae402.sharedpaint.ui.shape;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleUI extends Rectangle {
    private fr.sae402.sharedpaint.metier.shape.Rectangle rectangle;

    public RectangleUI(fr.sae402.sharedpaint.metier.shape.Rectangle rectangle) {
        this.rectangle = rectangle;
        this.setX(rectangle.getPosX());
        this.setY(rectangle.getPosY());
        this.setHeight(rectangle.getLargeur());
        this.setWidth(rectangle.getLongueur());
        if(rectangle.getRempli() == false){
            this.setFill(Color.TRANSPARENT);
            this.setStroke(Color.web(rectangle.getCouleur()));
        }else{
            this.setFill(Color.web(rectangle.getCouleur()));
        }
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


    public fr.sae402.sharedpaint.metier.shape.Rectangle getElementRectangle() {
        return this.rectangle;
    }
}
