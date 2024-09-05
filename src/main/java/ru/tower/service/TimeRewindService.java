package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.FloorComponent;
import ru.tower.component.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class TimeRewindService {

    private static final int REWIND_DURATION = 5; // Количество оборотов для перемотки

    public void rewindTime(CharacterComponent player, FloorComponent currentFloor) {
        // Сохранить текущее состояние
        int currentHp = player.getHp();
        int currentMp = player.getMp();
        List<ItemComponent> currentInventory = new ArrayList<>(player.getInventory().getAllItems());

        // Перемотка статистики игрока
        player.setHp(Math.min(player.getMaxHp(), currentHp + (player.getMaxHp() / 4)));
        player.setMp(Math.min(player.getMaxMp(), currentMp + (player.getMaxMp() / 4)));

        // Восстановите израсходованные предметы за последние 5 ходов.
        for (ItemComponent item : currentInventory) {
            if (!player.getInventory().hasItem(item.getItemData().getName())) {
                player.getInventory().addItem(item);
            }
        }

        // Сбросить прогресс этажа
        currentFloor.revivePlayer();

        System.out.println("Время перемотано назад на " + REWIND_DURATION + " ходов!");
        System.out.println("Ваши HP и MP частично восстановлены.");
        System.out.println("Этаж был сброшен, и некоторые ваши предметы были восстановлены.");
    }
}

