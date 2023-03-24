package fr.sae402.sharedpaint.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class ImageButton extends Button {
    private final int ICON_SIZE = 32;

    public ImageButton(InputStream inputStream) {
        super("");
        ImageView imageView = new ImageView(new Image(inputStream));
        imageView.setFitHeight(ICON_SIZE - 5);
        imageView.setFitWidth(ICON_SIZE - 5);
        this.setGraphic(imageView);
        this.setMaxWidth(ICON_SIZE);
        this.setMaxHeight(ICON_SIZE);
        this.setMinWidth(ICON_SIZE);
        this.setMinHeight(ICON_SIZE);
        this.setPadding(new Insets(2, 2, 2, 2));
    }
}
