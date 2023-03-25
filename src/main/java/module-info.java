module fr.sae402.sharedpaint {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens fr.sae402.sharedpaint to javafx.fxml;
    opens fr.sae402.sharedpaint.ui.window to javafx.fxml;
    exports fr.sae402.sharedpaint.ui.window to javafx.fxml;
    exports fr.sae402.sharedpaint;
    opens fr.sae402.sharedpaint.metier to com.google.gson;
    opens fr.sae402.sharedpaint.metier.shape to com.google.gson;
}