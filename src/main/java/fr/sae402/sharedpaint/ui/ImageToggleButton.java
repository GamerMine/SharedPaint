package fr.sae402.sharedpaint.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class ImageToggleButton extends ToggleButton {
    private final int ICON_SIZE = 32;

    public ImageToggleButton(InputStream inputStream, ToggleGroup toggleGroup) {
        super("");
        ImageView imageView = new ImageView(new Image(inputStream));
        imageView.setFitHeight(ICON_SIZE - 5);
        imageView.setFitWidth(ICON_SIZE - 5);
        this.setGraphic(imageView);
        if (toggleGroup != null) this.setToggleGroup(toggleGroup);
        this.setMaxWidth(ICON_SIZE);
        this.setMaxHeight(ICON_SIZE);
        this.setMinWidth(ICON_SIZE);
        this.setMinHeight(ICON_SIZE);
        this.setPadding(new Insets(2, 2, 2, 2));
    }

    public ImageToggleButton(InputStream inputStream) {
        this(inputStream, null);
    }
}
