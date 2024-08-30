package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.MessengerComponent;

import java.util.Scanner;

public class BeginningService {

    private Scanner scanner = new Scanner(System.in);

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
        String name = scanner.nextLine();

        System.out.println("Выберите свой класс:");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        System.out.println("3. Rogue");
        int classChoice = scanner.nextInt();

        CharacterComponent character;
        switch (classChoice) {
            case 1:
                character = new CharacterComponent(name, 1, 0, 120, 30, 120, 30, 15, 10, 5, 5, 20, 15, 5, 5, 8, 5, 150, 0);
                break;
            case 2:
                character = new CharacterComponent(name, 1, 0, 80, 100, 80, 100, 5, 5, 20, 5, 10, 5, 20, 15, 7, 5, 150, 0);
                break;
            case 3:
                character = new CharacterComponent(name, 1, 0, 100, 50, 100, 50, 10, 15, 10, 10, 15, 10, 10, 10, 12, 10, 200, 0);
                break;
            default:
                character = new CharacterComponent(name, 1, 0, 100, 50, 100, 50, 10, 10, 10, 5, 15, 10, 10, 10, 10, 5, 150, 0);
        }

        MessengerComponent.messenger()
                .text("Добро пожаловать, {}! Ваше приключение начинается прямо сейчас.", name)
                .newLine()
                .print();

        return character;
    }
}
