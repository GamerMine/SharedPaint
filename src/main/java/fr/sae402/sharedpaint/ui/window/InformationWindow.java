package fr.sae402.sharedpaint.ui.window;

import fr.sae402.sharedpaint.MainController;
import fr.sae402.sharedpaint.SharedPaint;
import fr.sae402.sharedpaint.metier.Utilisateur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InformationWindow {

    private Stage stage;
    private Thread updateThread;
    private InformationWindowController iwc;

    public InformationWindow(String adresse, MainController ctrl) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SharedPaint.class.getResource("information_view.fxml"));
        this.iwc = new InformationWindowController(adresse, ctrl);
        fxmlLoader.setController(iwc);
        Scene scene = new Scene(fxmlLoader.load(), 450, 400);
        this.stage = new Stage();

        this.stage.setTitle("Information");
        this.stage.setResizable(false);
        this.stage.setScene(scene);
        this.stage.setOnCloseRequest(this::onClose);
    }

    private void onClose(WindowEvent e) {
        this.updateThread.interrupt();
    }

    public void show() {
        this.stage.show();
        this.updateThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.iwc.updateUi();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        this.updateThread.start();
    }
}
