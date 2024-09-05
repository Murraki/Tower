package ru.tower.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tower.data.EquipData;
import ru.tower.enums.ItemType;
import ru.tower.enums.character.inventory.EquipSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class InventoryComponent {

    List<ItemComponent> items;

    private int maxSize;
    private int gold;
    private int weight;
    private int maxWeight;
    private int capacity;

    private EquipData<EquipSlot>[] equipment = new EquipData[EquipSlot.SIZE];

    public InventoryComponent(int maxSize, int gold, int weight, int maxWeight) {
        this.maxSize = maxSize;
        this.gold = gold;
        this.weight = weight;
        this.maxWeight = maxWeight;
    }
    public InventoryComponent(int capacity) {
        this.items = new ArrayList<>();
        this.capacity = capacity;
    }

    public boolean addItem(ItemComponent item) {
        if (items.size() < capacity) {
            items.add(item);
            return true;
        }
        return false;
    }

    public void removeItem(ItemComponent item) {
        items.remove(item);
    }

    public boolean hasItem(String itemName) {
        return items.stream().anyMatch(item -> item.getItemData().getName().equals(itemName));
    }

    public ItemComponent getItem(String itemName) {
        return items.stream()
                .filter(item -> item.getItemData().getName().equals(itemName))
                .findFirst()
                .orElse(null);
    }

    public List<ItemComponent> getUsableItems() {
        return items.stream()
                .filter(item -> item.getItemData().getType() == ItemType.CONSUMABLE)
                .collect(Collectors.toList());
    }

    public List<ItemComponent> getAllItems() {
        return new ArrayList<>(items);
    }

    public int getSize() {
        return items.size();
    }
}
