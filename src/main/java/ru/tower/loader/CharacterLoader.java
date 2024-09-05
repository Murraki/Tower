package ru.tower.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ru.tower.component.CharacterComponent;

import java.io.IOException;
import java.io.InputStream;

public class CharacterLoader {

    @Getter(lazy = true)
    private static final CharacterLoader character = new CharacterLoader();

    public CharacterComponent loadCharacterFromJson(int id) {
        InputStream inputStream = CharacterLoader.class.getClassLoader().getResourceAsStream("character.json");
        if (inputStream == null) {
            throw new IllegalArgumentException("файл не найден!");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Читаем JSON-файл и преобразуем его в объект Java
            JsonNode rootNode = objectMapper.readTree(inputStream);
            JsonNode charactersNode = rootNode.get("character");

            for (JsonNode characterNode : charactersNode) {
                if (characterNode.get("id").asInt() == id) {
                    return objectMapper.treeToValue(characterNode, CharacterComponent.class);
                }
            }

            throw new IllegalArgumentException("Персонаж с id " + id + " не найден");
        } catch (IOException e) {
            System.err.println("Ошибка загрузки персонажа." + e.getMessage());
            return null;
        }
    }
}
