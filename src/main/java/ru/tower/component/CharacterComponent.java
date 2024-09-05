package ru.tower.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tower.service.LevelService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterComponent implements Serializable {

    private InventoryComponent equipment;

    private int id;
    private String name;
    private int level;
    private int exp;
    private int hp;
    private int mp;
    private int maxHp;
    private int maxMp;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private int endurance;
    private int luck;
    private int attack;
    private int defense;
    private int magicAttack;
    private int magicDefense;
    private int speed;
    private double critChance;
    private double critDamage;
    private int gold;
    private int skillPoints;
    private int attributePoints;
    private int takeDamage;

    public InventoryComponent inventoryComponent;

    private Map<Integer, Integer> skillLevels = new HashMap<>();

    public CharacterComponent(int id, String name, int level, int exp, int hp, int mp, int maxHp, int maxMp, int strength, int agility, int intelligence, int luck, int attack, int defense, int magicAttack, int magicDefense, int speed, int critChance, int critDamage, int gold) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.hp = hp;
        this.mp = mp;
        this.maxHp = maxHp;
        this.maxMp = maxMp;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.luck = luck;
        this.attack = attack;
        this.defense = defense;
        this.magicAttack = magicAttack;
        this.magicDefense = magicDefense;
        this.speed = speed;
        this.critChance = critChance;
        this.critDamage = critDamage;
        this.gold = gold;
    }

    public void increaseSkillLevel(int skillId) {
        int currentLevel = skillLevels.getOrDefault(skillId, 0);
        skillLevels.put(skillId, currentLevel + 1);
    }

    public int getSkillLevel(int skillId) {
        return skillLevels.getOrDefault(skillId, 0);
    }

    public final boolean isCharacterDead() {
        return maxHp < 1;
    }

    public InventoryComponent getInventory() {
        return inventoryComponent;
    }

    public void useItem(ItemComponent item) {
        // Реализация логики использования элементов
        inventoryComponent.removeItem(item);
    }

    public void rest() {
        hp = maxHp;
        mp = maxMp;
    }

    public boolean hasTimeRewindPotion() {
        return inventoryComponent.hasItem("Зелье перемотки времени");
    }

    public void useTimeRewindPotion() {
        inventoryComponent.removeItem(inventoryComponent.getItem("Зелье перемотки времени"));
        // Реализуйте логику перемотки времени
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(damage - defense, 0);
        hp -= actualDamage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public void useMp(int amount) {
        this.mp -= amount;
        if (this.mp < 0) {
            this.mp = 0;
        }
    }

    public void increaseAttribute(int choice) {
        switch (choice) {
            case 1:
                strength++;
                break;
            case 2:
                dexterity++;
                break;
            case 3:
                intelligence++;
                break;
            case 4:
                endurance++;
                break;
        }
    }

    public void addExperience(int expReward) {
        this.exp += expReward;
        LevelService.checkLevelUp(this);
    }

    public void addGold(int goldReward) {
        this.gold += goldReward;
    }

}
