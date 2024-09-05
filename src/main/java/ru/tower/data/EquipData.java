package ru.tower.data;

import ru.tower.component.ItemComponent;
import ru.tower.enums.character.inventory.Equip;

public class EquipData <T extends Equip> {

    public T slot;
    public ItemComponent item;

    public EquipData(T slot) {
        this.slot = slot;
    }

}
