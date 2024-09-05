package ru.tower.service;

import lombok.Data;
import ru.tower.component.CharacterComponent;
import ru.tower.component.MonsterComponent;
import ru.tower.data.QuestData;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestService {

    private List<QuestData> availableQuests;
    private List<QuestData> activeQuests;

    public QuestService() {
        availableQuests = new ArrayList<>();
        activeQuests = new ArrayList<>();
        initializeQuests();
    }

    private void initializeQuests() {
        availableQuests.add(new QuestData("Охота на гоблинов", "Убейте 5 гоблинов", 5));
        availableQuests.add(new QuestData("Сбор сокровищ", "Найдите 3 редких предмета", 3));
    }

    public void checkQuestProgress(CharacterComponent player, MonsterComponent killedMonster) {
        for (QuestData quest : activeQuests) {
            if (quest.getType().equals("kill") && killedMonster.getData().getName().toLowerCase().contains(quest.getTarget().toLowerCase())) {
                quest.incrementProgress();
                if (quest.isCompleted()) {
                    completeQuest(player, quest);
                }
            }
        }
    }

    private void completeQuest(CharacterComponent player, QuestData quest) {
        player.setExp(player.getExp() + quest.getRewardExp());
        player.setGold(player.getGold() + quest.getRewardGold());
        System.out.println("Квест '" + quest.getName() + "' выполнен! Получено: " + quest.getRewardExp() + " опыта, " + quest.getRewardGold() + " золота.");
        activeQuests.remove(quest);
    }
}
