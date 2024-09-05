package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.data.AchievementData;

import java.util.HashMap;
import java.util.Map;

public class AchievementService {

    private Map<String, AchievementData> achievements;

    public AchievementService() {
        achievements = new HashMap<>();
        initializeAchievements();
    }

    private void initializeAchievements() {
        achievements.put("firstKill", new AchievementData("Первая кровь", "Убейте первого монстра"));
        achievements.put("floorMaster", new AchievementData("Покоритель этажей", "Пройдите 10 этажей"));
    }

    public void checkAchievements(CharacterComponent player) {
        if (player.getKillCount() == 1) {
            unlockAchievement(player, "firstKill");
        }
        if (player.getHighestFloor() == 10) {
            unlockAchievement(player, "floorMaster");
        }
    }

    private void unlockAchievement(CharacterComponent player, String achievementKey) {
        AchievementData achievement = achievements.get(achievementKey);
        if (!player.getUnlockedAchievements().contains(achievementKey)) {
            player.getUnlockedAchievements().add(achievementKey);
            System.out.println("Достижение разблокировано: " + achievement.getName());
            System.out.println(achievement.getDescription());
        }
    }
}
