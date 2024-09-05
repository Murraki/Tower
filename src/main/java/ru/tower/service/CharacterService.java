package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.InventoryComponent;
import ru.tower.loader.CharacterLoader;

public class CharacterService {

    static CharacterLoader loader = CharacterLoader.getCharacter();

    public static CharacterComponent createCharacter(String name, int classChoice) {
        CharacterComponent character = switch (classChoice) {
            case 1 -> loader.loadCharacterFromJson(1);
            case 2 -> loader.loadCharacterFromJson(2);
            case 3 -> loader.loadCharacterFromJson(3);
            default -> throw new IllegalStateException("Unexpected value: " + classChoice);
        };
        initializeCharacter(character);
        return character;
    }

    private static void initializeCharacter(CharacterComponent character) {
        character.setLevel(1);
        character.setExp(0);
        character.setGold(0);
        character.setSkillPoints(0);
        character.setEquipment(new InventoryComponent());

        character.setHp(character.getMaxHp());
        character.setMp(character.getMaxMp());
    }
}
