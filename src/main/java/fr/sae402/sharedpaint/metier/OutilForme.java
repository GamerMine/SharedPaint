package fr.sae402.sharedpaint.metier;

import fr.sae402.sharedpaint.SharedPaint;

import java.io.InputStream;
import java.util.ArrayList;

public class OutilForme {
    private Class<? extends Forme> forme;
    private final InputStream icon;
    private static final ArrayList<OutilForme> outilFormes = new ArrayList<>();

    // Add any tools that you want here
    public static final OutilForme RECTANGLE = new OutilForme(Rectangle.class, SharedPaint.class.getResourceAsStream("icons/rectangle.png"));
    public static final OutilForme CERCLE    = new OutilForme(Cercle.class, SharedPaint.class.getResourceAsStream("icons/circle.png"));
    public static final OutilForme LIGNE     = new OutilForme(Ligne.class, SharedPaint.class.getResourceAsStream("icons/line.png"));
    public static final OutilForme TEXTE     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/text.png"));
    public static final OutilForme UNDO     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/undo.png"));
    public static final OutilForme BLACK     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/black.png"));
    public static final OutilForme WHITE     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/white.png"));
    public static final OutilForme RED     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/red.png"));
    public static final OutilForme BLUE     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/blue.png"));
    public static final OutilForme YELLOW     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/yellow.png"));
    public static final OutilForme GREEN     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/green.png"));


    public OutilForme(Class<? extends Forme> forme, InputStream icon) {
        this.forme = forme;
        this.icon = icon;
        OutilForme.outilFormes.add(this);
    }

    public InputStream getIcon() {
        return this.icon;
    }

    public static ArrayList<OutilForme> getOutils() {
        return new ArrayList<>(OutilForme.outilFormes);
    }
}
