package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.FloorComponent;
import ru.tower.component.ItemComponent;
import ru.tower.component.MonsterComponent;
import ru.tower.utils.Utils;

public class ConsoleUiService {

    public void showMainMenu() {
        System.out.println("1. Новая игра");
        System.out.println("2. Загрузить игру");
    }

    public int getPlayerChoice() {
        return Utils.newScanner().nextInt();
    }

    public String getPlayerName() {
        System.out.println("Введите имя персонажа:");
        return Utils.newScanner().next();
    }

    public int getPlayerClassChoice() {
        System.out.println("Выберите класс:");
        System.out.println("1. Воин");
        System.out.println("2. Маг");
        System.out.println("3. Лучник");
        return Utils.newScanner().nextInt();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showFloorInfo(FloorComponent floor) {
        System.out.println("Этаж " + floor.getFloorNumber());
    }

    public void showMonsterEncounter(MonsterComponent monster) {
        System.out.println("Вы встретили " + monster.getData().getName() + "!");
        System.out.println("1. Атаковать");
        System.out.println("2. Попытаться обойти");
    }

    public void showItemDrop(ItemComponent item) {
        System.out.println("Вы нашли " + item.getItemData().getName() + "!");
    }

    public void showRewards(int exp, int gold, int skillPoints) {
        System.out.println("Получено: " + exp + " опыта, " + gold + " золота, " + skillPoints + " очков навыков");
    }

    public void showCharacterInfo(CharacterComponent character) {
        System.out.println("Имя: " + character.getName());
        System.out.println("Уровень: " + character.getLevel());
        System.out.println("HP: " + character.getHp() + "/" + character.getMaxHp());
        System.out.println("MP: " + character.getMp() + "/" + character.getMaxMp());
    }

    public void showFloorCompletion(FloorComponent floor) {
        System.out.println("Вы прошли этаж " + floor.getFloorNumber() + "!");
    }

    public void showRestOption() {
        System.out.println("Хотите отдохнуть? (1 - Да, 2 - Нет)");
    }

    public void showDeathMessage() {
        System.out.println("Вы погибли!");
    }

    public boolean confirmUseTimeRewindPotion() {
        System.out.println("Использовать зелье отката времени? (1 - Да, 2 - Нет)");
        return Utils.newScanner().nextInt() == 1;
    }

    public int getPlayerAction() {
        System.out.println("Выберите действие: ");
        System.out.println("1. Двигайтесь вперед");
        System.out.println("2. Используйте предмет");
        System.out.println("3. Показать информацию о персонаже");
        System.out.println("4. Сохраните игру.");
        System.out.println("5. Выйти");
        return Utils.newScanner().nextInt();
    }

    public boolean confirmQuit() {
        System.out.println("Вы уверены, что хотите выйти? (Y/N)");
        return Utils.newScanner().next().equalsIgnoreCase("Y");
    }
}
