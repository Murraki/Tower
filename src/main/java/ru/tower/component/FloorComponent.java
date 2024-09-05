package ru.tower.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tower.data.EventData;
import ru.tower.data.ItemData;
import ru.tower.data.MonsterData;
import ru.tower.data.TrapData;
import ru.tower.loader.EventLoader;
import ru.tower.loader.ItemLoader;
import ru.tower.loader.MonsterLoader;
import ru.tower.loader.TrapLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class FloorComponent implements Serializable {

    public static final String SAVE_FILE = "save.txt";

    public static int LAST_FLOOR_NUMBER;
    public static int NEXT_FLOOR_NUMBER;

    private int floorNumber;
    private int currentFloorNumber;
    private int quantityMonsters;
    private int quantityChests;
    private int maxMonsters;
    private int maxChests;
    private boolean completed;

    private List<MonsterComponent> monsters;
    private List<EventComponent> events;
    private List<TrapComponent> traps;
    private List<ItemComponent> items;

    public FloorComponent(int floorNumber, int quantityMonsters, int quantityChests, int maxMonsters, int maxChests) {
        this.floorNumber = floorNumber;
        this.quantityMonsters = quantityMonsters;
        this.quantityChests = quantityChests;
        this.maxMonsters = maxMonsters;
        this.maxChests = maxChests;
    }

    public MonsterComponent getRandomMonster() {
        if (monsters.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * monsters.size());
        return monsters.get(randomIndex);
    }

    public void revivePlayer() {
        // Сбросить прогресс этажа
        this.completed = false;

        // Возрождение монстров
        this.monsters = generateMonsters(this.floorNumber);

        // Возрождение предметов
        this.items = generateItems(this.floorNumber);

        // Сбросьте любые ловушки или события, специфичные для этажа.
        resetTrapsAndEvents();
    }

    private List<MonsterComponent> generateMonsters(int floorNumber) {
        List<MonsterComponent> newMonsters = new ArrayList<>();
        int monsterCount = 3 + floorNumber / 2;

        for (int i = 0; i < monsterCount; i++) {
            MonsterData monsterData = MonsterLoader.getRandomMonsterForFloor(floorNumber);
            MonsterComponent monster = new MonsterComponent(monsterData);
            monster.scaleToFloor(floorNumber);
            newMonsters.add(monster);
        }

        return newMonsters;
    }

    private List<ItemComponent> generateItems(int floorNumber) {
        List<ItemComponent> newItems = new ArrayList<>();
        int itemCount = 1 + floorNumber / 3;

        for (int i = 0; i < itemCount; i++) {
            ItemData itemData = ItemLoader.getRandomItemForFloor(floorNumber);
            ItemComponent item = new ItemComponent(itemData);
            item.scaleToFloor(floorNumber);
            newItems.add(item);
        }

        return newItems;
    }

    private void resetTrapsAndEvents() {
        this.traps = generateTraps(this.floorNumber);
        this.events = generateEvents(this.floorNumber);

    }
    private List<TrapComponent> generateTraps(int floorNumber) {
        List<TrapComponent> newTraps = new ArrayList<>();
        int trapCount = floorNumber / 2;

        for (int i = 0; i < trapCount; i++) {
            TrapData trapData = TrapLoader.getRandomTrapForFloor(floorNumber);
            TrapComponent trap = new TrapComponent(trapData);
            newTraps.add(trap);
        }

        return newTraps;
    }

    private List<EventComponent> generateEvents(int floorNumber) {
        List<EventComponent> newEvents = new ArrayList<>();
        int eventCount = 1 + floorNumber / 5;

        for (int i = 0; i < eventCount; i++) {
            EventData eventData = EventLoader.getRandomEventForFloor(floorNumber);
            EventComponent event = new EventComponent(eventData);
            newEvents.add(event);
        }

        return newEvents;
    }

    public TrapComponent getRandomTrap() {
        if (traps.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * traps.size());
        return traps.get(randomIndex);
    }

    public EventComponent getRandomEvent() {
        if (events.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * events.size());
        return events.get(randomIndex);
    }

}
