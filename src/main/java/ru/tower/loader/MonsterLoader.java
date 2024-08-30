package ru.tower.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import ru.tower.component.MonsterComponent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MonsterLoader {

    public static List<MonsterComponent> loadMonstersFromJson() {
        InputStream inputStream = MonsterLoader.class.getClassLoader().getResourceAsStream("monsters.json");
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found!");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Читаем JSON-файл и преобразуем его в объект Java
            MonstersWrapper wrapper = objectMapper.readValue(inputStream, MonstersWrapper.class);
            return wrapper.getMonsters();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Вспомогательный класс для обертки списка монстров
    @Setter
    @Getter
    private static class MonstersWrapper {
        private List<MonsterComponent> monsters;

    }
}
