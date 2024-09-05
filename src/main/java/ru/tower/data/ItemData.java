package ru.tower.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tower.enums.ItemType;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ItemData {

    private final int id;
    private final String name;
    private final ItemType type;
    private final int level;
    private int attack;
    private int defense;
    private double criticalChance;
    private double criticalDamage;
    private int attackBonus;
    private int defenseBonus;
    private int magicAttack;
    private int magicDefense;
    private int criticalChanceBonus;
    private int criticalDamageBonus;
    private int price;
    private int weight;
    private final String description;
    private final int healAmount;
    private final int manaAmount;
    private int strengthBoost;

    public ItemData(int id, String name, ItemType type, int level, int attackBonus, int defenseBonus, int magicAttack, int magicDefense, int criticalChanceBonus, int criticalDamageBonus, int price, int weight, String description, int healAmount, int manaAmount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.magicAttack = magicAttack;
        this.magicDefense = magicDefense;
        this.criticalChanceBonus = criticalChanceBonus;
        this.criticalDamageBonus = criticalDamageBonus;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.healAmount = healAmount;
        this.manaAmount = manaAmount;
    }

}
