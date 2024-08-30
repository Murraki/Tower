package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.InventoryComponent;
import ru.tower.component.ItemComponent;
import ru.tower.enums.character.inventory.EquipSlot;

public class InventoryService {

    public void equip(CharacterComponent character, ItemComponent item) {
        InventoryComponent inventory = (InventoryComponent) character.getInventory();
        if (item.isEquip()) {
            EquipSlot slot = determineEquipSlot(item);
            if (slot != null) {
                if (inventory.getEquipment()[slot.ordinal()].item != null) {
                    unequip(character, slot);
                }

                inventory.getEquipment()[slot.ordinal()].item = item;
                inventory.setWeight(inventory.getWeight() + item.getWeight());

                applyItemStats(character, item, true);

                System.out.println("Экипирован " + item.getName() + " в слот " + slot.name() + ".");
            } else {
                System.out.println("Невозможно экипировать " + item.getName() + ". Подходящий слот не найден.");
            }
        } else {
            System.out.println(item.getName() + " не является экипируемым предметом.");
        }
    }

    public void unequip(CharacterComponent character, EquipSlot slot) {
        InventoryComponent inventory = (InventoryComponent) character.getInventory();
        ItemComponent item = inventory.getEquipment()[slot.ordinal()].item;
        if (item != null) {
            inventory.setWeight(inventory.getWeight() - item.getWeight());

            applyItemStats(character, item, false);

            inventory.getEquipment()[slot.ordinal()].item = null;
            System.out.println("Снят " + item.getName() + " из слота " + slot.name() + ".");
        }
    }

    public void useItem(CharacterComponent character, ItemComponent item) {
        if (item.isUse()) {
            character.setHp(Math.min(character.getHp() + item.getAttack(), character.getMaxHp()));
            character.setMp(Math.min(character.getMp() + item.getMagicAttack(), character.getMaxMp()));
            character.getInventory().remove(item);
            System.out.println("Использован предмет: " + item.getName());
        } else {
            System.out.println(item.getName() + " нельзя использовать.");
        }
    }

    private EquipSlot determineEquipSlot(ItemComponent item) {
        if (item.getName().contains("Меч") || item.getName().contains("Топор")) {
            return EquipSlot.RIGHT_HAND;
        } else if (item.getName().contains("Щит")) {
            return EquipSlot.LEFT_HAND;
        } else if (item.getName().contains("Шлем")) {
            return EquipSlot.HEAD;
        } else if (item.getName().contains("Броня")) {
            return EquipSlot.BODY;
        }
        return null;
    }

    private void applyItemStats(CharacterComponent character, ItemComponent item, boolean equipping) {
        int modifier = equipping ? 1 : -1;
        character.setAttack(character.getAttack() + item.getAttack() * modifier);
        character.setDefense(character.getDefense() + item.getDefense() * modifier);
        character.setMagicAttack(character.getMagicAttack() + item.getMagicAttack() * modifier);
        character.setMagicDefense(character.getMagicDefense() + item.getMagicDefense() * modifier);
        character.setCritChance(character.getCritChance() + item.getCriticalChance() * modifier);
        character.setCritDamage(character.getCritDamage() + item.getCriticalDamage() * modifier);
    }


}

