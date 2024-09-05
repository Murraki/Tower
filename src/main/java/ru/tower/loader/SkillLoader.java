package ru.tower.loader;

import lombok.Getter;
import ru.tower.data.SkillData;

import java.util.HashMap;
import java.util.Map;

public class SkillLoader {
    @Getter(lazy = true)
    private static final SkillLoader instance = new SkillLoader();

    private SkillLoader() {}

    public Map<Integer, SkillData> loadSkills() {
        // Загрузка навыков из файла или базы данных
        return new HashMap<>();
    }

}
