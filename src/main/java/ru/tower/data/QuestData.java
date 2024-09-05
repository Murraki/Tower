package ru.tower.data;

import lombok.Data;

@Data
public class QuestData {
    private String name;
    private String description;
    private String type;
    private String target;
    private int targetAmount;
    private int currentProgress;
    private int rewardExp;
    private int rewardGold;

    public QuestData(String name, String description, int targetAmount) {
        this.name = name;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentProgress = 0;
    }

    public void incrementProgress() {
        currentProgress++;
    }

    public boolean isCompleted() {
        return currentProgress >= targetAmount;
    }
}
