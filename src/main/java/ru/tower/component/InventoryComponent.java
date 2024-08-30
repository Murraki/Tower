package ru.tower.component;

import lombok.Getter;
import lombok.Setter;
import ru.tower.enums.character.inventory.Equip;
import ru.tower.enums.character.inventory.EquipSlot;

@Getter
@Setter
public class InventoryComponent {

    private int maxSize;
    private int gold;
    private int weight;
    private int maxWeight;

    public InventoryComponent(int maxSize, int gold, int weight, int maxWeight) {
        this.maxSize = maxSize;
        this.gold = gold;
        this.weight = weight;
        this.maxWeight = maxWeight;
    }

    public static class EquipData<T extends Equip> {
        public T slot;
        public ItemComponent item;

        public EquipData(T slot) {
            this.slot = slot;
        }
    }

    private EquipData<EquipSlot>[] equipment = new EquipData[EquipSlot.SIZE];

}
