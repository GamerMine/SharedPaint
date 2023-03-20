package fr.sae402.sharedpaint.metier;

import java.util.ArrayList;

public class OutilForme {
    // Add any tools that you want here
    public static final OutilForme RECTANGLE = new OutilForme(Rectangle.class);

    private Class<? extends Forme> forme;
    private static ArrayList<OutilForme> outilFormes = new ArrayList<>();

    public OutilForme(Class<? extends Forme> forme) {
        this.forme = forme;
        OutilForme.outilFormes.add(this);
    }

    public static int getToolsCount() {
        return OutilForme.outilFormes.size();
    }
}
