package ru.tower.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tower.component.InventoryComponent;
import ru.tower.component.ItemComponent;
import ru.tower.data.ItemData;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ItemEntity {

    InventoryComponent component;
    ItemData data;

    public List<ItemComponent> getEquipped() {
        return Arrays.stream(component.getEquipment())
                .filter(equipData -> equipData.item != null)
                .map(equipData -> equipData.item)
                .toList();
    }

    public String getName() {
        return data.getName();
    }

    public boolean isWeapon() {
        return false;
    }

    public boolean isArmor() {
        return false;
    }

    public boolean isJewel() {
        return false;
    }

    public boolean isAccessory() {
        return false;
    }

    public boolean isConsumables() {
        return !(isWeapon() && isArmor() && isJewel() && isAccessory());
    }

    public boolean isEquippable() {
        return data.getEquip() != null;
    }
}
