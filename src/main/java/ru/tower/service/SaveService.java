package ru.tower.service;

import com.google.gson.Gson;
import ru.tower.component.CharacterComponent;
import ru.tower.component.FloorComponent;
import ru.tower.data.SaveData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveService {

    private static final String SAVE_FILE = "save.json";

    public void saveGame(CharacterComponent player, FloorComponent currentFloor) {
        SaveData saveData = new SaveData(player, currentFloor);
        String json = new Gson().toJson(saveData);
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SaveData loadGame() {
        try (FileReader reader = new FileReader(SAVE_FILE)) {
            return new Gson().fromJson(reader, SaveData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteSaveFile() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
