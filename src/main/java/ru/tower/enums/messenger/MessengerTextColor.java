package ru.tower.enums.messenger;

import lombok.Getter;

@Getter
public enum MessengerTextColor implements Messenger {

    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    CYAN("\u001B[36m");

    public static final String ANSI_RESET = "\u001B[0m";
    private final String startCode;

    MessengerTextColor(String startCode) {
        this.startCode = startCode;
    }

    @Override
    public int getChoice() {
        return 0;
    }

    @Override
    public String getMessengerText() {
        return "";
    }
}
