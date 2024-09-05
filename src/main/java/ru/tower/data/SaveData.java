package ru.tower.data;

import ru.tower.component.CharacterComponent;
import ru.tower.component.FloorComponent;

public class SaveData {

    private CharacterComponent player;
    private FloorComponent currentFloor;

    public SaveData(CharacterComponent player, FloorComponent currentFloor) {
        this.player = player;
        this.currentFloor = currentFloor;
    }

    public CharacterComponent getPlayer() {
        return player;
    }

    public void setPlayer(CharacterComponent player) {
        this.player = player;
    }

    public FloorComponent getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(FloorComponent currentFloor) {
        this.currentFloor = currentFloor;
    }
}
