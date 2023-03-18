module fr.sae402.sharedpaint {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens fr.sae402.sharedpaint to javafx.fxml;
    exports fr.sae402.sharedpaint;
}