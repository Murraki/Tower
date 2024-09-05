package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.utils.Rnd;

import java.util.ArrayList;
import java.util.List;

public class RandomEventService {

    private List<RandomEventService> events;

    public RandomEventService() {
        events = new ArrayList<>();
        initializeEvents();
    }

    private void initializeEvents() {
        events.add(new RandomEventService("Сундук с сокровищами", "Вы нашли сундук с сокровищами!") {
            @Override
            public void execute(CharacterComponent player) {
                int gold = Rnd.get(10, 50);
                player.setGold(player.getGold() + gold);
                System.out.println("Вы нашли " + gold + " золота!");
            }
        });
        events.add(new RandomEventService("Ловушка", "Вы попали в ловушку!") {
            @Override
            public void execute(CharacterComponent player) {
                int damage = Rnd.get(5, 15);
                player.setHp(player.getHp() - damage);
                System.out.println("Вы получили " + damage + " урона от ловушки!");
            }
        });
    }

    public RandomEventService generateEvent() {
        if (Rnd.get(100) < 20) {  // 20% шанс на случайное событие
            return events.get(Rnd.get(events.size()));
        }
        return null;
    }
}
