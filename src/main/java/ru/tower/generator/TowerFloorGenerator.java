package ru.tower.generator;


import lombok.Getter;
import ru.tower.component.FloorComponent;
import ru.tower.component.ItemComponent;
import ru.tower.component.MonsterComponent;
import ru.tower.loader.ItemLoader;
import ru.tower.loader.MonsterLoader;
import ru.tower.utils.Rnd;

import java.util.ArrayList;
import java.util.List;

public class TowerFloorGenerator {

    @Getter(lazy = true)
    private static final TowerFloorGenerator instance = new TowerFloorGenerator();

    public FloorComponent generateFloor(int floorNumber) {
        // Увеличиваем номер этажа
        int monsterCount = Rnd.get(3, 7);
        int chestCount = Rnd.get(1, 3);
        List<MonsterComponent> monsters = generateMonsters(monsterCount, floorNumber);
        List<ItemComponent> items = generateItems(chestCount, floorNumber);
        return new FloorComponent(floorNumber, monsters, items);
    }

    private List<MonsterComponent> generateMonsters(int count, int floorNumber) {
        List<MonsterComponent> monsters = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MonsterComponent monster = MonsterLoader.getInstance().getRandomMonster();
            monster.scaleToFloor(floorNumber);
            monsters.add(monster);
        }
        return monsters;
    }

    private List<ItemComponent> generateItems(int count, int floorNumber) {
        List<ItemComponent> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ItemComponent item = ItemLoader.getInstance().getRandomItem();
            item.scaleToFloor(floorNumber);
            items.add(item);
        }
        return items;
    }

}
