package fr.sae402.sharedpaint.metier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Client {
    private UUID idClient;
    private Gson gsonBuilder;
    private JsonArray historique;
    private FileWriter file;

    public Client() {
        this.idClient = UUID.randomUUID();
        this.gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        this.historique = new JsonArray();
        try {
            this.file = new FileWriter(this.idClient+".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Forme forme) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(forme.getClass().getName(), this.gsonBuilder.toJsonTree(forme));
        this.historique.add(jsonObject);
    }

    public void close() {
        try {
            this.file.write(this.historique.toString());
            this.file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
