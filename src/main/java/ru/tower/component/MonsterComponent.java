package ru.tower.component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonsterComponent {

    private int id;
    private String name;
    private String race;
    private int currentHp;
    private int maxHp;
    private int damage;
    private int defense;
    private int level;
    private int dropChance;
    private int skillPoint;

    @JsonCreator
    public MonsterComponent(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("maxHp") int maxHp,
            @JsonProperty("damage") int damage,
            @JsonProperty("defense") int defense,
            @JsonProperty("level") int level,
            @JsonProperty("dropChance") int dropChance,
            @JsonProperty("skillPoint") int skillPoint) {

        this.id = id;
        this.name = name;
        this.race = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;  // Инициализируем currentHp значением maxHp
        this.damage = damage;
        this.defense = defense;
        this.level = level;
        this.dropChance = dropChance;
        this.skillPoint = skillPoint;
    }


    public boolean isMonsterDead() {
        return currentHp < 1;
    }


}
