package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.utils.Utils;

public class LevelService {

    private static final int BASE_EXP_REQUIRED = 100;
    private static final double EXP_GROWTH_RATE = 1.5;

    public static void checkLevelUp(CharacterComponent player) {
        int currentLevel = player.getLevel();
        int expRequired = calculateExpRequired(currentLevel);

        if (player.getExp() >= expRequired) {
            levelUp(player);
        }
    }

    private static void levelUp(CharacterComponent player) {
        player.setLevel(player.getLevel() + 1);
        player.setExp(player.getExp() - calculateExpRequired(player.getLevel() - 1));
        player.setSkillPoints(player.getSkillPoints() + 3);
        player.setAttributePoints(player.getAttributePoints() + 5);

        System.out.println("Поздравляем! Вы достигли " + player.getLevel() + " уровня!");
        System.out.println("Получено 3 очка навыков и 5 очков характеристик.");
    }

    private static int calculateExpRequired(int level) {
        return (int) (BASE_EXP_REQUIRED * Math.pow(EXP_GROWTH_RATE, level - 1));
    }

    public static void distributeAttributePoints(CharacterComponent player) {
        while (player.getAttributePoints() > 0) {
            System.out.println("У вас " + player.getAttributePoints() + " очков характеристик.");
            System.out.println("1. Сила (" + player.getStrength() + ")");
            System.out.println("2. Ловкость (" + player.getDexterity() + ")");
            System.out.println("3. Интеллект (" + player.getIntelligence() + ")");
            System.out.println("4. Выносливость (" + player.getEndurance() + ")");
            System.out.println("0. Выход");

            int choice = Utils.newScanner().nextInt();
            if (choice == 0) break;

            if (choice >= 1 && choice <= 4) {
                player.increaseAttribute(choice);
                player.setAttributePoints(player.getAttributePoints() - 1);
            } else {
                System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
