package fr.sae402.sharedpaint.ui.window;

import fr.sae402.sharedpaint.MainController;
import fr.sae402.sharedpaint.SharedPaint;
import fr.sae402.sharedpaint.metier.Utilisateur;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InformationWindowController {

    private static ArrayList<Utilisateur> users;

    private String adresse;
    private MainController ctrl;

    @FXML
    private Label lblAdresse;
    @FXML
    private ListView<String> listeUtilisateur;

    public InformationWindowController() {}

    public InformationWindowController(String adresse, MainController ctrl) {
        this.adresse = adresse;
        this.ctrl = ctrl;
    }

    @FXML
    public void initialize() {
        this.lblAdresse.setText(this.adresse);
    }
    void updateUi() {
        this.ctrl.requestUtilisateurs();
        if (users == null) return;

        Platform.runLater(() -> {
            this.listeUtilisateur.getItems().clear();
            for (Utilisateur utilisateur : users) {
                this.listeUtilisateur.getItems().add(utilisateur.getPseudo());
            }
        });
    }

    public static void setUsers(List<Utilisateur> usersList) {
        users = new ArrayList<>(usersList);
    }


}
