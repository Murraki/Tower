package ru.tower.service;

import ru.tower.component.CharacterComponent;
import ru.tower.data.NpcData;
import ru.tower.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogService {

    private static DialogService instance;

    private Map<String, List<String>> dialogues;

    private DialogService() {
        dialogues = new HashMap<>();
        initializeDialogues();
    }

    public static DialogService getInstance() {
        if (instance == null) {
            instance = new DialogService();
        }
        return instance;
    }

    private void initializeDialogues() {
        dialogues.put("Введение", Arrays.asList(
                "Приветствую тебя, путник!",
                "Ты стоишь у подножия великой Башни.",
                "Готов ли ты принять вызов и подняться на ее вершину?"
        ));
        dialogues.put("Торговец", Arrays.asList(
                "Приветствую, герой! Что желаешь приобрести?",
                "У меня есть редкие артефакты и мощные зелья.",
                "Выбирай мудро, ведь от этого может зависеть твоя жизнь в Башне."
        ));
    }

    public void startDialog(String key, CharacterComponent player, NpcData npc) {
        List<String> lines = dialogues.get(key);
        for (String line : lines) {
            System.out.println(npc.getName() + ": " + line);
            Utils.newScanner().nextLine();
        }
    }
}
