package ru.tower.data;

import lombok.AllArgsConstructor;
import ru.tower.component.CharacterComponent;

@AllArgsConstructor
public abstract class RandomEventData {

    private String name;
    private String description;

    public abstract void execute(CharacterComponent player);
}
