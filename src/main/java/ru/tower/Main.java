package ru.tower;

import ru.tower.service.GameService;
import ru.tower.utils.Utils;

public class Main {

    public static void main(String[] args) {
        Utils.newGameService().startGame();
    }
}
