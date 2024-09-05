package ru.tower.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillData {

    private int id;
    private String name;
    private String description;
    private int baseDamage;
    private int manaCost;
    private int cooldown;
    private int requiredLevel;

}
