package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.component.MonsterComponent;
import ru.tower.data.SkillData;
import ru.tower.loader.SkillLoader;
import ru.tower.utils.Utils;

import java.util.Map;

public class SkillService {

    private static SkillService instance;

    private Map<Integer, SkillData> skills;

    public SkillService() {
        skills = SkillLoader.getInstance().loadSkills();
    }

    public void addSkillPoints(CharacterComponent player, int points) {
        player.setSkillPoints(player.getSkillPoints() + points);
    }

    public void showSkillPointsMenu(CharacterComponent player) {
        System.out.println("У вас " + player.getSkillPoints() + " очков навыков.");
        System.out.println("Выберите навык для улучшения:");
        for (Map.Entry<Integer, SkillData> entry : skills.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName() + " (Уровень: " + player.getSkillLevel(entry.getKey()) + ")");
        }
        int choice = Utils.newScanner().nextInt();
        if (skills.containsKey(choice) && player.getSkillPoints() > 0) {
            player.increaseSkillLevel(choice);
            player.setSkillPoints(player.getSkillPoints() - 1);
            System.out.println("Навык " + skills.get(choice).getName() + " улучшен!");
        }
    }

    public void useSkill(CharacterComponent player, MonsterComponent monster) {
        System.out.println("Выберите навык для использования:");
        for (Map.Entry<Integer, SkillData> entry : skills.entrySet()) {
            int skillLevel = player.getSkillLevel(entry.getKey());
            if (skillLevel > 0) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getName() + " (Уровень: " + skillLevel + ")");
            }
        }
        int choice = Utils.newScanner().nextInt();
        if (skills.containsKey(choice) && player.getSkillLevel(choice) > 0) {
            SkillData skill = skills.get(choice);
            if (player.getMp() >= skill.getMpCost()) {
                int damage = calculateSkillDamage(player, skill);
                monster.setCurrentHp(monster.getCurrentHp() - damage);
                player.setMp(player.getMp() - skill.getMpCost());
                System.out.println("Вы использовали " + skill.getName() + " и нанесли " + damage + " урона!");
            } else {
                System.out.println("Недостаточно маны для использования навыка.");
            }
        } else {
            System.out.println("Неверный выбор навыка.");
        }
    }

    private int calculateSkillDamage(CharacterComponent player, SkillData skill) {
        int baseDamage = skill.getBaseDamage();
        int skillLevel = player.getSkillLevel(skill.getId());
        return baseDamage + (skillLevel * 5) + (player.getIntelligence() * 2);
    }

    public void showAvailableSkills(CharacterComponent player) {
        System.out.println("Доступные навыки:");
        for (SkillData skill : skills.values()) {
            if (player.getLevel() >= skill.getRequiredLevel()) {
                System.out.println(skill.getId() + ": " + skill.getName() + " (Уровень: " + player.getSkillLevel(skill.getId()) + ")");
            }
        }
    }

    public static SkillService getInstance() {
        if (instance == null) {
            instance = new SkillService();
        }
        return instance;
    }
}
