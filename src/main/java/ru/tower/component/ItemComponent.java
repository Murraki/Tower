package ru.tower.component;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ItemComponent {

    private int id;
    private String name;
    private int attack;
    private int defense;
    private int magicAttack;
    private int magicDefense;
    private int criticalChance;
    private int criticalDamage;
    private boolean isEquip;
    private boolean isUse;
    private int price;
    private int weight;
    private String description;
    private int healAmount;
    private int manaAmount;

    @Getter
    public List<ItemComponent> items = new ArrayList<>();

    public ItemComponent(int id, String name, int attack, int defense, int magicAttack, int magicDefense, int criticalChance, int criticalDamage, boolean isEquip, boolean isUse, int price, int weight, String description, int healAmount, int manaAmount, List<ItemComponent> items) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.magicAttack = magicAttack;
        this.magicDefense = magicDefense;
        this.criticalChance = criticalChance;
        this.criticalDamage = criticalDamage;
        this.isEquip = isEquip;
        this.isUse = isUse;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.healAmount = healAmount;
        this.manaAmount = manaAmount;
        this.items = items;
    }

    public void addItem(ItemComponent item) {
        items.add(item);
    }

    public boolean isEquip() {
        return isEquip;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setEquip(boolean equip) {
        isEquip = equip;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

}
