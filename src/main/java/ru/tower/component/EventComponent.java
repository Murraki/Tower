package ru.tower.component;

import lombok.Getter;
import lombok.Setter;
import ru.tower.data.EventData;

@Getter
@Setter
public class EventComponent {

    private EventData data;

    public EventComponent(EventData data) {
        this.data = data;
    }

    public void trigger(CharacterComponent character) {
        // Логика запуска события
        System.out.println("Событие: " + data.getDescription());
        // Здесь можно добавить различные эффекты события на персонажа
    }
}
