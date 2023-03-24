package fr.sae402.sharedpaint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class SharedPaint extends Application {
    private FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SharedPaint.class.getResource("main_view.fxml"));
        this.fxmlLoader = fxmlLoader;
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("SharedPaint");
        stage.setScene(scene);
        stage.setOnCloseRequest(this::onStageClose);
        stage.show();
    }

    private void onStageClose(WindowEvent e) {
        ((MainController)this.fxmlLoader.getController()).fermer();
    }

    public static void main(String[] args) {
        launch();
    }
}