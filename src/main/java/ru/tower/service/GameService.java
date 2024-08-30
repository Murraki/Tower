package ru.tower.service;

import ru.tower.component.*;
import ru.tower.loader.MonsterLoader;
import ru.tower.rnd.Rnd;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class GameService {

    private final List<MonsterComponent> allMonsters;

    public GameService() {
        this.allMonsters = MonsterLoader.loadMonstersFromJson();
    }

    public FloorComponent generateFloor() {
        int floorNumber = FloorComponent.NEXT_FLOOR_NUMBER++;
        int quantityMonsters = Rnd.get(1, 5);
        int quantityChests = Rnd.get(0, 2);
        int size = 10;
        int maxMonsters = 5;
        int maxChests = 2;

        FloorComponent floor = new FloorComponent(floorNumber, quantityMonsters, quantityChests, size, maxMonsters, maxChests);

        for (int i = 0; i < quantityMonsters; i++) {
            floor.getMonsters().add(Rnd.get(allMonsters));
        }

        return floor;
    }

    public void playFloor(CharacterComponent player, FloorComponent floor) {
        MessengerComponent.messenger()
                .text("Вы вошли на этаж {}!", floor.getFloorNumber())
                .newLine()
                .print();

        while (!floor.isCompleted() && !player.isCharacterDead()) {
            MonsterComponent monster = Rnd.get(floor.getMonsters());
            if (!monster.isMonsterDead()) {
                battle(player, monster);
            }

            if (Rnd.calc(30, 100)) { // 30% шанс найти сундук
                openChest(player);
            }
        }

        if (!player.isCharacterDead()) {
            player.setExp(player.getExp() + floor.getFloorNumber() * 10);
            MessengerComponent.messenger()
                    .text("Вы прошли этаж {}!", floor.getFloorNumber())
                    .newLine()
                    .print();
        }
    }

    public boolean wantsToSaveAndQuit() {
        System.out.println("Вы хотите сохранить и выйти? (да/нет)");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    public void saveGame(CharacterComponent player, FloorComponent currentFloor) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FloorComponent.SAVE_FILE))) {
            oos.writeObject(player);
            oos.writeObject(currentFloor);
            System.out.println("Игра успешно сохранена!");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения игры:" + e.getMessage());
        }
    }

    public void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FloorComponent.SAVE_FILE))) {
            CharacterComponent player = (CharacterComponent) ois.readObject();
            FloorComponent currentFloor = (FloorComponent) ois.readObject();

            FloorComponent.LAST_FLOOR_NUMBER = currentFloor.getFloorNumber();
            FloorComponent.NEXT_FLOOR_NUMBER = currentFloor.getFloorNumber() + 1;

            MessengerComponent.messenger()
                    .text("Добро пожаловать, {}!", player.getName())
                    .newLine()
                    .text("Вы находитесь на этаже {}.", currentFloor.getFloorNumber())
                    .newLine()
                    .print();

            playFloor(player, currentFloor);

            System.out.println("Игра успешно загрузилась!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки игры:" + e.getMessage());
        }
    }

    private void battle(CharacterComponent player, MonsterComponent monster) {
        MessengerComponent.messenger()
                .text("Появляется {}!", monster.getName())
                .newLine()
                .print();

        while (!player.isCharacterDead() && !monster.isMonsterDead()) {
            int playerDamage = Math.max(0, player.getAttack() - monster.getDefense());
            int monsterDamage = Math.max(0, monster.getDamage() - player.getDefense());

            monster.setCurrentHp(monster.getCurrentHp() - playerDamage);
            player.setHp(player.getHp() - monsterDamage);

            MessengerComponent.messenger()
                    .text("Вы наносите {} урона. HP монстра: {}", playerDamage, monster.getCurrentHp())
                    .newLine()
                    .text("Монстр наносит {} урона. Ваше здоровье: {}", monsterDamage, player.getHp())
                    .newLine()
                    .print();
        }

        if (monster.isMonsterDead()) {
            MessengerComponent.messenger()
                    .text("Вы победили {}!", monster.getName())
                    .newLine()
                    .print();
        }
    }

    private void openChest(CharacterComponent player) {
        int gold = Rnd.get(10, 50);
        player.setGold(player.getGold() + gold);
        MessengerComponent.messenger()
                .text("Вы нашли сундук! Вы получили {} золота.", gold)
                .newLine()
                .print();
    }

    private void fightMonster(CharacterComponent player, FloorComponent floor) {
        MonsterComponent monster = Rnd.get(floor.getMonsters());
        if (!monster.isMonsterDead()) {
            battle(player, monster);
        } else {
            System.out.println("На этом этаже не осталось монстров, с которыми можно было бы сражаться.");
        }
    }

    private void manageInventory(CharacterComponent player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory:");
        for (int i = 0; i < player.getInventory().size(); i++) {
            System.out.println(i + ": " + player.getInventory().get(i).getName());
        }
        System.out.print("Выберите предмет для использования/экипировки (-1 для отмены): ");
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < player.getInventory().size()) {
            ItemComponent item = player.getInventory().get(choice);
            if (item.isUse()) {
                player.useItem(item);
            } else if (item.isEquip()) {
                player.equipItem(item);
            }
        }
    }

    private void searchForItems(CharacterComponent player, FloorComponent floor) {
        if (!floor.getFloorItems().isEmpty()) {
            ItemComponent item = Rnd.get(floor.getFloorItems());
            player.getInventory().add(item);
            floor.getFloorItems().remove(item);
            System.out.println("Вы нашли:" + item.getName());
        } else {
            System.out.println("Вы не нашли ни одного предмета.");
        }
    }

    private void rest(CharacterComponent player) {
        int healAmount = player.getMaxHp() / 10;
        player.setHp(Math.min(player.getHp() + healAmount, player.getMaxHp()));
        System.out.println("Вы отдохнули и поправились " + healAmount + " HP.");
    }

}
