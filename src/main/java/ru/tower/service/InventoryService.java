package ru.tower.service;

import lombok.Getter;
import ru.tower.component.CharacterComponent;
import ru.tower.component.InventoryComponent;
import ru.tower.component.ItemComponent;
import ru.tower.enums.character.inventory.EquipSlot;
import ru.tower.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {

    private static final int MAX_INVENTORY_SIZE = 20;

    InventoryComponent inventory;

    @Getter(lazy = true)
    private static final InventoryService instance = new InventoryService();

    public void equip(CharacterComponent character, ItemComponent item) {
        inventory = character.getEquipment();
        if (item.isEquip()) {
            EquipSlot slot = determineEquipSlot(item);
            if (slot != null) {
                if (inventory.getEquipment()[slot.ordinal()].item != null) {
                    unequip(character, slot);
                }

                inventory.getEquipment()[slot.ordinal()].item = item;
                inventory.setWeight(inventory.getWeight() + item.getItemData().getWeight());

                applyItemStats(character, item, true);

                System.out.println("Экипирован " + item.getItemData().getName() + " в слот " + slot.name() + ".");
            } else {
                System.out.println("Невозможно экипировать " + item.getItemData().getName() + ". Подходящий слот не найден.");
            }
        } else {
            System.out.println(item.getItemData().getName() + " не является экипируемым предметом.");
        }
    }

    public void unequip(CharacterComponent character, EquipSlot slot) {
        ItemComponent item = inventory.getEquipment()[slot.ordinal()].item;
        if (item != null) {
            inventory.setWeight(inventory.getWeight() - item.getItemData().getWeight());

            applyItemStats(character, item, false);

            inventory.getEquipment()[slot.ordinal()].item = null;
            System.out.println("Снят " + item.getItemData().getName() + " из слота " + slot.name() + ".");
        }
    }

    public void useItem(CharacterComponent character, ItemComponent item) {
        if (item.isUse()) {
            character.setHp(Math.min(character.getHp() + item.getItemData().getHeal(), character.getMaxHp()));
            character.setMp(Math.min(character.getMp() + item.getItemData().getManaRestore(), character.getMaxMp()));
            character.getEquipment().getItems().remove(item);
            System.out.println("Использован предмет: " + item.getItemData().getName());
        } else {
            System.out.println(item.getItemData().getName() + " нельзя использовать.");
        }
    }

    public ItemComponent chooseItem(CharacterComponent character) {
        System.out.println("Выберите предмет для использования:");
        List<ItemComponent> items = character.getEquipment().getItems();
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getItemData().getName());
        }
        int choice = Utils.newScanner().nextInt() - 1;
        return items.get(choice);
    }

    private EquipSlot determineEquipSlot(ItemComponent item) {
        if (item.getItemData().getName().contains("Меч") || item.getItemData().getName().contains("Топор")) {
            return EquipSlot.RIGHT_HAND;
        } else if (item.getItemData().getName().contains("Щит")) {
            return EquipSlot.LEFT_HAND;
        } else if (item.getItemData().getName().contains("Шлем")) {
            return EquipSlot.HEAD;
        } else if (item.getItemData().getName().contains("Броня")) {
            return EquipSlot.BODY;
        }
        return null;
    }

    private void applyItemStats(CharacterComponent character, ItemComponent item, boolean equipping) {
        int modifier = equipping ? 1 : -1;
        character.setAttack(character.getAttack() + item.getItemData().getAttack() * modifier);
        character.setDefense(character.getDefense() + item.getItemData().getDefense() * modifier);
        character.setMagicAttack(character.getMagicAttack() + item.getItemData().getMagicAttack() * modifier);
        character.setMagicDefense(character.getMagicDefense() + item.getItemData().getMagicDefense() * modifier);
        character.setCritChance((character.getCritChance() + item.getItemData().getCriticalChance() * modifier));
        character.setCritDamage((character.getCritDamage() + item.getItemData().getCriticalDamage() * modifier));
    }

    public void addItem(CharacterComponent character, ItemComponent item) {
        if (character.getEquipment().getItems().size() < MAX_INVENTORY_SIZE) {
            character.getEquipment().getItems().add(item);
            System.out.println("Вы получили: " + item.getItemData().getName());
        } else {
            System.out.println("Инвентарь полон. Невозможно добавить " + item.getItemData().getName());
        }
    }

    public void removeItem(CharacterComponent character, ItemComponent item) {
        if (character.getEquipment().getItems().remove(item)) {
            System.out.println("Вы удалили: " + item.getItemData().getName());
        } else {
            System.out.println("Предмет не найден в инвентаре");
        }
    }

    public void showInventory(CharacterComponent character) {
        System.out.println("Инвентарь:");
        List<ItemComponent> items = character.getEquipment().getItems();
        for (int i = 0; i < items.size(); i++) {
            ItemComponent item = items.get(i);
            System.out.println((i + 1) + ". " + item.getItemData().getName());
        }
    }

    public void showUsableItems(CharacterComponent player) {
        List<ItemComponent> usableItems = player.getEquipment().getItems().stream()
                .filter(ItemComponent::isUse)
                .collect(Collectors.toList());

        System.out.println("Доступные для использования предметы:");
        for (int i = 0; i < usableItems.size(); i++) {
            System.out.println((i + 1) + ". " + usableItems.get(i).getItemData().getName());
        }
    }

    // Метод для проверки наличия предмета в инвентаре
    public boolean hasItem(CharacterComponent character, String itemName) {
        return character.getEquipment().getItems().stream()
                .anyMatch(item -> item.getItemData().getName().equals(itemName));
    }

    // Метод для получения списка используемых предметов
    public List<ItemComponent> getUsableItems(CharacterComponent character) {
        return character.getEquipment().getItems().stream()
                .filter(ItemComponent::isUse)
                .collect(Collectors.toList());
    }
}

