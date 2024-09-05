package ru.tower.utils;

import lombok.experimental.UtilityClass;
import ru.tower.component.CharacterComponent;
import ru.tower.component.FloorComponent;
import ru.tower.data.SaveData;
import ru.tower.service.GameService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

@UtilityClass
public class Utils {

    public Scanner newScanner() {
        return new Scanner(System.in);
    }

    public GameService newGameService() {
        return new GameService();
    }

    public SaveData newSaveData(CharacterComponent component, FloorComponent currentFloor) {
        return new SaveData(component, currentFloor);
    }

    public FileWriter newFileWriter(String path) throws IOException {
        return new FileWriter(path);
    }

    public File newFile(String path) {
        return new File(path);
    }

}
