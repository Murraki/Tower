package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.ItemComponent;
import ru.tower.component.MonsterComponent;
import ru.tower.utils.Utils;

import java.util.List;

public class CombatService {

    public void startCombat(CharacterComponent player, MonsterComponent monster) {
        System.out.println("Начало боя с " + monster.getData().getName() + "!");

        while (!player.isCharacterDead() && !monster.isMonsterDead()) {
            displayCombatStatus(player, monster);
            playerTurn(player, monster);
            if (!monster.isMonsterDead()) {
                monsterTurn(player, monster);
            }
        }
        if (player.isCharacterDead()) {
            System.out.println("Вы проиграли бой!");
        } else {
            handleVictory(player, monster);
        }
    }

    private void displayCombatStatus(CharacterComponent player, MonsterComponent monster) {
        System.out.println("-------------------");
        System.out.println("Игрок: HP " + player.getHp() + "/" + player.getMaxHp() + " | MP " + player.getMp() + "/" + player.getMaxMp());
        System.out.println(monster.getData().getName() + ": HP " + monster.getCurrentHp() + "/" + monster.getMaxHp());
        System.out.println("-------------------");
    }

    private void playerTurn(CharacterComponent player, MonsterComponent monster) {
        System.out.println("Ваш ход! Выберите действие:");
        System.out.println("1. Атаковать");
        System.out.println("2. Использовать навык");
        System.out.println("3. Использовать предмет");
        int choice = Utils.newScanner().nextInt();
        switch (choice) {
            case 1:
                performAttack(player, monster);
                break;
            case 2:
                SkillService skillService = new SkillService();
                skillService.useSkill(player, monster);
                break;
            case 3:
                InventoryService inventoryService = InventoryService.getInstance();
                inventoryService.useItem(player, inventoryService.chooseItem(player));

                break;
            default:
                System.out.println("Неверный выбор. Пропуск хода.");
        }
    }

    private void performAttack(CharacterComponent player, MonsterComponent monster) {
        int damage = calculateDamage(player.getAttack(), monster.getDefense());
        if (isCriticalHit(player.getCritChance())) {
            damage *= (int) player.getCritDamage();
            System.out.println("Критический удар!");
        }
        monster.takeDamage(damage);
        System.out.println("Вы нанесли " + damage + " урона!");
    }

    private void useSkill(CharacterComponent player, MonsterComponent monster) {
        SkillService skillService = SkillService.getInstance();
        skillService.showAvailableSkills(player);
        System.out.println("Выберите номер навыка для использования:");
        int skillChoice = Utils.newScanner().nextInt();
        skillService.useSkill(player, skillChoice, monster);
    }

    private void useItem(CharacterComponent player) {
        InventoryService inventoryService = InventoryService.getInstance();
        List<ItemComponent> usableItems = inventoryService.getUsableItems(player);
        inventoryService.showUsableItems(player);
        System.out.println("Выберите номер предмета для использования:");
        int itemChoice = Utils.newScanner().nextInt();
        if (itemChoice > 0 && itemChoice <= usableItems.size()) {
            ItemComponent selectedItem = usableItems.get(itemChoice - 1);
            inventoryService.useItem(player, selectedItem);
        } else {
            System.out.println("Неверный выбор предмета.");
        }
    }

    private void monsterTurn(CharacterComponent player, MonsterComponent monster) {
        int damage = calculateDamage(monster.getDamage(), player.getDefense());
        player.takeDamage(damage);
        System.out.println(monster.getData().getName() + " наносит вам " + damage + " урона!");
    }

    private int calculateDamage(int attack, int defense) {
        return Math.max(0, attack - defense);
    }

    private boolean isCriticalHit(double criticalChance) {
        return Math.random() < criticalChance;
    }

    private static void handleVictory(CharacterComponent player, MonsterComponent monster) {
        int expReward = monster.getExpReward();
        int goldReward = monster.getGoldReward();
        player.addExperience(expReward);
        player.addGold(goldReward);
        System.out.println("Вы победили " + monster.getData().getName() + "!");
        System.out.println("Получено " + expReward + " опыта и " + goldReward + " золота");

        // Добавление выпадения предметов
        ItemComponent droppedItem = monster.getRandomDrop();
        if (droppedItem != null) {
            System.out.println("Вы получили предмет: " + droppedItem.getItemData().getName());
            player.getEquipment().addItem(droppedItem);
        }
    }
}
