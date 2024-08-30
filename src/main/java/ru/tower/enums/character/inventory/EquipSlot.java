package ru.tower.enums.character.inventory;

public enum EquipSlot implements Equip {

    ACCESSORY_HEAD_LEFT,
    HEAD,
    ACCESSORY_HEAD_RIGHT,
    RIGHT_HAND,
    BODY,
    LEFT_HAND,
    GLOVES,
    LEGS,
    FEETS,
    ACCESSORY,
    NECK,
    EAR_LEFT,
    EAR_RIGHT,
    FINGER_LEFT,
    FINGER_RIGHT;

    public static final int SIZE = values().length;

    @Override
    public int id() {
        return 0;
    }

    @Override
    public String getEquipName() {
        return "";
    }
}
