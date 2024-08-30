package ru.tower.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class FloorComponent implements Serializable {

    public static final String SAVE_FILE = "save.txt";

    public static int LAST_FLOOR_NUMBER;
    public static int NEXT_FLOOR_NUMBER;

    private int floorNumber;
    private int quantityMonsters;
    private int quantityChests;
    private int size;
    private int maxMonsters;
    private int maxChests;

    private List<ItemComponent> floorItems;

    List<MonsterComponent> monsters = new ArrayList<>();

    public FloorComponent(int floorNumber, int quantityMonsters, int quantityChests, int size, int maxMonsters, int maxChests) {
        this.floorNumber = floorNumber;
        this.quantityMonsters = quantityMonsters;
        this.quantityChests = quantityChests;
        this.size = size;
        this.maxMonsters = maxMonsters;
        this.maxChests = maxChests;
    }

    public boolean isCompleted() {
        return monsters.stream().allMatch(MonsterComponent::isMonsterDead);
    }

    public void addItem(ItemComponent item) {
        floorItems.add(item);
    }

}
