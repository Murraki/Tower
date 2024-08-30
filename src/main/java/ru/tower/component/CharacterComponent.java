package ru.tower.component;

import lombok.Getter;
import lombok.Setter;
import ru.tower.service.InventoryService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CharacterComponent implements Serializable {

    private String name;
    private int level;
    private int exp;
    private int hp;
    private int mp;
    private int maxHp;
    private int maxMp;
    private int strength;
    private int agility;
    private int intelligence;
    private int luck;
    private int attack;
    private int defense;
    private int magicAttack;
    private int magicDefense;
    private int speed;
    private int critChance;
    private int critDamage;
    private int gold;

    public List<ItemComponent> inventory;
    public InventoryComponent equipmentInventory;
    public InventoryService inventoryService;

    public CharacterComponent(String name, int level, int exp, int hp, int mp, int maxHp, int maxMp, int strength, int agility, int intelligence, int luck, int attack, int defense, int magicAttack, int magicDefense, int speed, int critChance, int critDamage, int gold) {
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
        this.inventory = new ArrayList<>();
        this.equipmentInventory = new InventoryComponent(20, 0, 0, 100);
        this.gold = gold;
    }

    public final boolean isCharacterDead() {
        return maxHp < 1;
    }

    public void useItem(ItemComponent item) {
        if (item.isUse()) {
            // Apply item effects
            this.hp = Math.min(this.hp + item.getHealAmount(), this.maxHp);
            this.mp = Math.min(this.mp + item.getManaAmount(), this.maxMp);
            inventory.remove(item);
        }
    }

    public void equipItem(ItemComponent item) {
        if (item.isEquip()) {
            // Equip item and apply stats
            inventoryService.equip(this, item);
            this.attack += item.getAttack();
            this.defense += item.getDefense();
        }
    }
}
