package ru.tower.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tower.data.MonsterData;
import ru.tower.enums.ItemType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonsterComponent {

    private MonsterData data;
    private int hp;
    private int maxHp;
    private int damage;
    private int defense;
    private int currentHp;
    private int dropChance;
    private int skillPoint;

    public MonsterComponent(MonsterData data) {
        this.data = data;
    }

    public boolean isMonsterDead() {
        return currentHp < 1;
    }

    public int getExpReward() {
        return data.getExpReward();
    }

    public int getGoldReward() {
        return data.getGoldReward();
    }

    public ItemComponent getRandomDrop() {
        // Реализуйте логику удаления здесь
        return new ItemComponent("Random Item", ItemType.CONSUMABLE);
    }

    public void scaleToFloor(int floorNumber) {
        double scaleFactor = 1 + (floorNumber * 0.1);
        setMaxHp((int)(getMaxHp() * scaleFactor));
        setDamage((int)(getDamage() * scaleFactor));
        setDefense((int)(getDefense() * scaleFactor));
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

}
