package ru.tower;

import ru.tower.component.CharacterComponent;
import ru.tower.component.FloorComponent;
import ru.tower.service.BeginningService;
import ru.tower.service.GameService;

public class Main {

    public static void main(String[] args) {
        BeginningService beginningService = new BeginningService();
        GameService gameService = new GameService();

        beginningService.showIntroduction();

        CharacterComponent player = beginningService.createCharacter();

        while (true) {
            FloorComponent currentFloor = gameService.generateFloor();
            gameService.playFloor(player, currentFloor);

            if (player.isCharacterDead()) {
                System.out.println("Game Over!");
                break;
            }

            if (gameService.wantsToSaveAndQuit()) {
                gameService.saveGame(player, currentFloor);
                break;
            }
        }
    }
}
