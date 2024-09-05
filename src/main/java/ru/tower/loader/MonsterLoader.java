package ru.tower.loader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import ru.tower.data.MonsterData;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class MonsterLoader {

    @Getter(lazy = true)
    private static final MonsterLoader instance = new MonsterLoader();

    private static List<MonsterData> monsters;

    static {
        loadMonstersFromJson();
    }

    private static void loadMonstersFromJson() {

        try (FileReader reader = new FileReader("monsters.json")) {
            Type monsterListType = new TypeToken<List<MonsterData>>(){}.getType();
            monsters = new Gson().fromJson(reader, monsterListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MonsterData getRandomMonsterForFloor(int floorNumber) {
        List<MonsterData> suitableMonsters = monsters.stream()
                .filter(m -> m.getLevel() <= floorNumber)
                .collect(Collectors.toList());

        if (suitableMonsters.isEmpty()) {
            return monsters.get(0);
        }

        int randomIndex = (int) (Math.random() * suitableMonsters.size());
        return suitableMonsters.get(randomIndex);
    }
}
