package ru.tower.component;

import lombok.*;
import ru.tower.data.ItemData;
import ru.tower.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemComponent {

    private ItemData itemData;

    private boolean isEquip;
    private boolean isUse;

    public ItemComponent(ItemData data) {
        this.itemData = data;
    }

    public void scaleToFloor(int floorNumber) {
        double scaleFactor = 1 + (floorNumber * 0.05);
        if (itemData.getAttackBonus() > 0) {
            itemData.setAttackBonus((int)(itemData.getAttackBonus() * scaleFactor));
        }
        if (itemData.getDefenseBonus() > 0) {
            itemData.setDefenseBonus((int)(itemData.getDefenseBonus() * scaleFactor));
        }
    }
}
