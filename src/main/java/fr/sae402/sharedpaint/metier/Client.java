package fr.sae402.sharedpaint.metier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Client {
    private UUID idClient;
    private Gson historique;
    private FileWriter file;

    public Client() {
        this.idClient = UUID.randomUUID();
        this.historique = new GsonBuilder().setPrettyPrinting().create();
        try {
            this.file = new FileWriter(this.idClient+".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Forme forme) {
        this.historique.toJson(forme, this.file);
    }

    public void close() {
        try {
            this.file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
