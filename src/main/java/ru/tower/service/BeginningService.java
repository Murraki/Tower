package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.MessengerComponent;
import ru.tower.loader.CharacterLoader;
import ru.tower.utils.Utils;

public class BeginningService {


    public void showIntroduction() {
        MessengerComponent.messenger()
                .text("Добро пожаловать в игру «Башня»!")
                .newLine()
                .text("Вам предстоит отправиться в опасное путешествие через загадочную башню.")
                .newLine()
                .text("Каждый этаж представляет новые испытания и монстров, которых нужно преодолеть.")
                .newLine()
                .text("Готовы ли вы начать свое приключение?")
                .newLine()
                .print();
    }

    public CharacterComponent createCharacter() {
        MessengerComponent.messenger()
                .text("Давайте создадим вашего персонажа.")
                .newLine()
                .print();

        System.out.print("Введите имя вашего персонажа:");
        String name = Utils.newScanner().nextLine();

        System.out.println("Выберите свой класс:");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        System.out.println("3. Rogue");
        int classChoice = Utils.newScanner().nextInt();

        CharacterLoader loader = CharacterLoader.getCharacter();
        CharacterComponent character = switch (classChoice) {
            case 1 -> loader.loadCharacterFromJson(1);
            case 2 -> loader.loadCharacterFromJson(2);
            case 3 -> loader.loadCharacterFromJson(3);
            default -> throw new IllegalStateException("Unexpected value: " + classChoice);
        };

        MessengerComponent.messenger()
                .text("Добро пожаловать, {}! Ваше приключение начинается прямо сейчас.", name)
                .newLine()
                .print();

        return character;
    }
}
