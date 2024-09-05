package ru.tower.service;

import ru.tower.component.*;
import ru.tower.data.NpcData;
import ru.tower.data.RandomEventData;
import ru.tower.data.SaveData;
import ru.tower.generator.TowerFloorGenerator;
import ru.tower.loader.MonsterLoader;
import ru.tower.utils.Rnd;
import ru.tower.utils.Utils;

import java.io.*;
import java.util.List;

/**
 * Класс GameService отвечает за основную логику игры, включая прохождение этажей,
 * сражения с монстрами, управление инвентарем и сохранение/загрузку игры.
 */
public class GameService {

    private CharacterComponent player;
    private FloorComponent currentFloor;
    private ConsoleUiService uiService;
    private SaveService saveService;
    private DialogService dialogService;
    private CombatService combatService;
    private SkillService skillService;
    private QuestService questService;
    private AchievementService achievementService;
    private CraftingService craftingService;
    private RandomEventService randomEventService;
    private TowerFloorGenerator floorGenerator;
    private int currentFloorNumber;
    /**
     * Конструктор класса GameService.
     * Инициализирует генератор этажей башни, загрузчик монстров и сервис инвентаря.
     */
    public GameService() {
        uiService = new ConsoleUiService();
        saveService = new SaveService();
        dialogService = DialogService.getInstance();
        floorGenerator = new TowerFloorGenerator();
        combatService = new CombatService();
        skillService = new SkillService();
        questService = new QuestService();
        achievementService = new AchievementService();
        craftingService = new CraftingService();
        randomEventService = new RandomEventService();
    }

    public void startGame() {
        uiService.showMainMenu();
        int choice = uiService.getPlayerChoice();
        if (choice == 1) {
            startNewGame();
        } else if (choice == 2) {
            loadGame();
        }
        gameLoop();
    }

    public void startNewGame(CharacterComponent player) {
        this.player = player;
        this.currentFloorNumber = 1;
        this.currentFloor = floorGenerator.generateFloor(currentFloorNumber);
    }

    private void startNewGame() {
         dialogService.startDialog("Введение", null, new NpcData("Хранитель башни"));
        String playerName = uiService.getPlayerName();
        int classChoice = uiService.getPlayerClassChoice();
        player = CharacterService.createCharacter(playerName, classChoice);
        saveService.saveGame(player, null);
        currentFloor = floorGenerator.generateFloor(1);
    }

    private void loadGame() {
        SaveData saveData = saveService.loadGame();
        if (saveData != null) {
            player = saveData.getPlayer();
            currentFloor = saveData.getCurrentFloor();
            uiService.showMessage("Игра успешно загружена!");
        } else {
            uiService.showMessage("Ошибка загрузки игры. Начинаем новую игру.");
            startNewGame();
        }
    }

    private void gameLoop() {
        while (true) {
            if (player.isCharacterDead()) {
                handlePlayerDeath();
                if (player.isCharacterDead()) {
                    break;
                }
            }

            uiService.showFloorInfo(currentFloor);
            int action = uiService.getPlayerAction();

            switch (action) {
                case 1:
                    moveForward();
                    break;
                case 2:
                    useInventoryItem();
                    break;
                case 3:
                    showCharacterInfo();
                    break;
                case 4:
                    saveGame();
                    break;
                case 5:
                    if (uiService.confirmQuit()) {
                        return;
                    }
                    break;
            }

            if (currentFloor.isCompleted()) {
                handleFloorCompletion();
            }
        }
    }

    private void moveForward() {
        RandomEventData event = randomEventService.generateEvent();
        if (event != null) {
            event.execute(player);
        } else {
            MonsterComponent monster = currentFloor.getRandomMonster();
            if (monster != null) {
                handleMonsterEncounter(monster);
            } else {
                uiService.showMessage("Вы не встретили никого на своем пути.");
            }
        }
    }

    private void handleMonsterEncounter(MonsterComponent monster) {
        uiService.showMonsterEncounter(monster);
        int choice = uiService.getPlayerChoice();
        if (choice == 1) {
            combatService.startCombat(player, monster);
            if (monster.isMonsterDead()) {
                handleMonsterDefeat(monster);
            }
        } else {
            uiService.showMessage("Вы решили обойти монстра.");
        }
    }

    private void handleMonsterDefeat(MonsterComponent monster) {
        player.setExp(player.getExp() + monster.getExpReward());
        player.setGold(player.getGold() + monster.getGoldReward());
        ItemComponent droppedItem = monster.getRandomDrop();
        if (droppedItem != null) {
            uiService.showItemDrop(droppedItem);
            player.getInventory().addItem(droppedItem);
        }
        skillService.addSkillPoints(player, monster.getSkillPoint());
        uiService.showRewards(monster.getExpReward(), monster.getGoldReward(), monster.getSkillPoint());
    }

    private void useInventoryItem() {
        List<ItemComponent> usableItems = player.inventoryComponent.getItems().getUsableItems();
        ItemComponent selectedItem = uiService.selectInventoryItem(usableItems);
        if (selectedItem != null) {
            player.useItem(selectedItem);
            uiService.showMessage("Вы использовали " + selectedItem.getName());
        }
    }

    private void showCharacterInfo() {
        uiService.showCharacterInfo(player);
    }

    private void saveGame() {
        saveService.saveGame(player, currentFloor);
        uiService.showMessage("Игра успешно сохранена!");
    }

    private void handleFloorCompletion() {
        currentFloorNumber++;
        player.addExperience(currentFloorNumber * 50);
        player.addGold(currentFloorNumber * 100);
        System.out.println("Вы прошли этаж " + (currentFloorNumber - 1) + "!");
        System.out.println("Получено опыта: " + (currentFloorNumber * 50));
        System.out.println("Получено золота: " + (currentFloorNumber * 100));
        currentFloor = new FloorComponent(currentFloorNumber);

        uiService.showFloorCompletion(currentFloor);
        player.setExp(player.getExp() + currentFloor.getFloorNumber() * 10);
        dialogService.startDialog("Торговец", player, new NpcData("Торговец"));
        uiService.showRestOption();
        if (uiService.getPlayerChoice() == 1) {
            player.rest();
        }
        skillService.showSkillPointsMenu(player);
        currentFloor = floorGenerator.generateFloor(currentFloor.getFloorNumber() + 1);
    }

    private void handlePlayerDeath() {
        uiService.showDeathMessage();
        if (player.hasTimeRewindPotion()) {
            if (uiService.confirmUseTimeRewindPotion()) {
                player.useTimeRewindPotion();
                currentFloor.revivePlayer();
                System.out.println("Вы использовали зелье отката времени и возродились!");
            } else {
                System.out.println("Игра окончена. Вы достигли " + currentFloorNumber + " этажа.");
                saveHighScore(player.getName(), currentFloorNumber);
                saveService.deleteSaveFile();
            }
        } else {
            saveService.deleteSaveFile();
        }
    }

    private void saveHighScore(String playerName, int floorReached) {
        // Здесь можно добавить логику сохранения рекорда
        System.out.println("Новый рекорд: " + playerName + " достиг " + floorReached + " этажа!");
    }

    public void playFloor() {
        while (!currentFloor.isCompleted() && !player.isCharacterDead()) {
            // Логика прохождения этажа
            handleEncounter();
            handleTraps();
            handleEvents();
        }

        if (player.isCharacterDead()) {
            handlePlayerDeath();
        } else {
            handleFloorCompletion();
        }
    }

    private void handleEncounter() {
        MonsterComponent monster = currentFloor.getRandomMonster();
        if (monster != null) {
            combatService.startCombat(player, monster);
        }
    }

    private void handleTraps() {
        TrapComponent trap = currentFloor.getRandomTrap();
        if (trap != null) {
            trap.activate(player);
        }
    }

    private void handleEvents() {
        EventComponent event = currentFloor.getRandomEvent();
        if (event != null) {
            event.trigger(player);
        }
    }

//    /**------------------------------------------------------------------------------------------------------------------
//     * Метод для прохождения этажа.
//     * @param player Игрок, проходящий этаж.
//     * @param floor Этаж, который проходит игрок.
//     */
//    public void playFloor(CharacterComponent player, FloorComponent floor) {
//        // Выводим сообщение о входе на этаж
//        MessengerComponent.messenger()
//                .text("Вы вошли на этаж {}!", floor.getFloorNumber())
//                .newLine()
//                .print();
//
//        // Основной цикл прохождения этажа
//        while (!floor.isCompleted() && !player.isCharacterDead()) {
//            // Выбираем случайного монстра на этаже
//            MonsterComponent monster = Rnd.get(floor.getMonsters());
//            if (!monster.isMonsterDead()) {
//                battle(player, monster);
//            }
//
//            // 30% шанс найти сундук
//            if (Rnd.calc(30, 100)) { // 30% шанс найти сундук
//                openChest(player);
//            }
//        }
//
//        // Если игрок выжил, начисляем опыт
//        if (!player.isCharacterDead()) {
//            player.setExp(player.getExp() + floor.getFloorNumber() * 10);
//            MessengerComponent.messenger()
//                    .text("Вы прошли этаж {}!", floor.getFloorNumber())
//                    .newLine()
//                    .print();
//        }
//    }
//
//    /**
//     * Проверяет, хочет ли игрок сохранить игру и выйти.
//     * @return true, если игрок хочет сохранить и выйти, иначе false.
//     */
//    public boolean wantsToSaveAndQuit() {
//        System.out.println("Вы хотите сохранить и выйти? (да/нет)");
//        return Utils.newScanner().nextLine().equalsIgnoreCase("y");
//    }
//
//    /**
//     * Сохраняет текущее состояние игры.
//     * @param player Игрок для сохранения.
//     * @param currentFloor Текущий этаж для сохранения.
//     */
//    public void saveGame(CharacterComponent player, FloorComponent currentFloor) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FloorComponent.SAVE_FILE))) {
//            oos.writeObject(player);
//            oos.writeObject(currentFloor);
//            System.out.println("Игра успешно сохранена!");
//        } catch (IOException e) {
//            System.out.println("Ошибка сохранения игры:" + e.getMessage());
//        }
//    }
//
//    /**
//     * Загружает сохраненную игру.
//     */
//    public void loadGame() {
//
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FloorComponent.SAVE_FILE))) {
//            CharacterComponent player = (CharacterComponent) ois.readObject();
//            FloorComponent currentFloor = (FloorComponent) ois.readObject();
//
//            FloorComponent.LAST_FLOOR_NUMBER = currentFloor.getFloorNumber();
//            FloorComponent.NEXT_FLOOR_NUMBER = currentFloor.getFloorNumber() + 1;
//
//            MessengerComponent.messenger()
//                    .text("Добро пожаловать, {}!", player.getName())
//                    .newLine()
//                    .text("Вы находитесь на этаже {}.", currentFloor.getFloorNumber())
//                    .newLine()
//                    .print();
//
//            playFloor(player, currentFloor);
//
//            System.out.println("Игра успешно загрузилась!");
//        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Ошибка загрузки игры:" + e.getMessage());
//        }
//    }
//
//    /**
//     * Проводит битву между игроком и монстром.
//     * @param player Игрок, участвующий в битве.
//     * @param monster Монстр, участвующий в битве.
//     */
//    private void battle(CharacterComponent player, MonsterComponent monster) {
//        MessengerComponent.messenger()
//                .text("Появляется {}!", monster.getData().getName())
//                .newLine()
//                .print();
//
//        while (!player.isCharacterDead() && !monster.isMonsterDead()) {
//            int playerDamage = Math.max(0, player.getAttack() - monster.getDefense());
//            int monsterDamage = Math.max(0, monster.getDamage() - player.getDefense());
//
//            monster.setCurrentHp(monster.getCurrentHp() - playerDamage);
//            player.setHp(player.getHp() - monsterDamage);
//
//            MessengerComponent.messenger()
//                    .text("Вы наносите {} урона. HP монстра: {}", playerDamage, monster.getCurrentHp())
//                    .newLine()
//                    .text("Монстр наносит {} урона. Ваше здоровье: {}", monsterDamage, player.getHp())
//                    .newLine()
//                    .print();
//        }
//
//        if (monster.isMonsterDead()) {
//            MessengerComponent.messenger()
//                    .text("Вы победили {}!", monster.getData().getName())
//                    .newLine()
//                    .print();
//        }
//    }
//
//    /**
//     * Открывает сундук и добавляет золото игроку.
//     * @param player Игрок, открывающий сундук.
//     */
//    private void openChest(CharacterComponent player) {
//        int gold = Rnd.get(10, 50);
//        player.setGold(player.getGold() + gold);
//        MessengerComponent.messenger()
//                .text("Вы нашли сундук! Вы получили {} золота.", gold)
//                .newLine()
//                .print();
//    }
//
//    /**
//     * Инициирует сражение с случайным монстром на этаже.
//     * @param player Игрок, вступающий в бой.
//     * @param floor Этаж, на котором происходит сражение.
//     */
//    private void fightMonster(CharacterComponent player, FloorComponent floor) {
//        MonsterComponent monster = Rnd.get(floor.getMonsters());
//        if (!monster.isMonsterDead()) {
//            battle(player, monster);
//        } else {
//            System.out.println("На этом этаже не осталось монстров, с которыми можно было бы сражаться.");
//        }
//    }
//
//    /**
//     * Управляет инвентарем игрока.
//     * @param character Персонаж, чьим инвентарем управляют.
//     */
//    private void manageInventory(CharacterComponent character) {
//        System.out.println("Inventory:");
//        for (int i = 0; i < character.equipment.getItems().size(); i++) {
//            System.out.println(i + ": " + character.equipment.getItems().get(i).getItemData().getName());
//        }
//        System.out.print("Выберите предмет для использования/экипировки (-1 для отмены): ");
//        int choice = Utils.newScanner().nextInt();
//        if (choice >= 0 && choice < character.equipment.getItems().size()) {
//            ItemComponent item = character.equipment.getItems().get(choice);
//            if (item.isUse()) {
//                InventoryService.getInstance().useItem(character,item);
//            } else if (item.isEquip()) {
//                InventoryService.getInstance().equip(character, item);
//            }
//        }
//    }
//
//    /**
//     * Ищет предметы на текущем этаже.
//     * @param character Персонаж, ищущий предметы.
//     * @param floor Этаж, на котором происходит поиск.
//     */
//    private void searchForItems(CharacterComponent character, FloorComponent floor) {
//        if (!floor.getItems().isEmpty()) {
//            ItemComponent item = Rnd.get(floor.getItems());
//            character.equipment.getItems().add(item);
//            floor.getItems().remove(item);
//            System.out.println("Вы нашли:" + item.getItemData().getName());
//        } else {
//            System.out.println("Вы не нашли ни одного предмета.");
//        }
//    }
//
//    /**
//     * Позволяет игроку отдохнуть и восстановить здоровье.
//     * @param player Игрок, который отдыхает.
//     */
//    private void rest(CharacterComponent player) {
//        int healAmount = player.getMaxHp() / 10;
//        player.setHp(Math.min(player.getHp() + healAmount, player.getMaxHp()));
//        System.out.println("Вы отдохнули и поправились " + healAmount + " HP.");
//    }

}
