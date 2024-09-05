package ru.tower.component;

import lombok.Getter;
import lombok.Setter;
import ru.tower.data.TrapData;

@Getter
@Setter
public class TrapComponent {

    private TrapData data;

    public TrapComponent(TrapData data) {
        this.data = data;
    }

    public void activate(CharacterComponent character) {
        // Логика активации ловушки
        int damage = data.getDamage();
        character.takeDamage(damage);
        System.out.println("Ловушка активирована! Получен урон: " + damage);
    }
}
