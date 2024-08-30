package ru.tower.component;

import lombok.Getter;
import lombok.Setter;
import ru.tower.enums.messenger.MessengerTextColor;

@Getter
@Setter
public class MessengerComponent {

    private static final int TEXT_SPEED = 42;

    private int choice;
    private StringBuilder text;

    public static MessengerComponent messenger(MessengerTextColor color) {
        MessengerComponent messenger = new MessengerComponent();
        if (color != null) {
            messenger.text.append(color.getStartCode());
        }
        return messenger;
    }

    public static MessengerComponent messenger() {
        return messenger(null);
    }

    public MessengerComponent() {
        this.text = new StringBuilder();
    }

    public MessengerComponent text(String text, Object... arguments) {
        for (Object next : arguments) {
            text = text.replaceFirst("\\{}", next.toString());
        }
        this.text.append(text);
        return this;
    }

    public void print() {
        String txt = text.toString();
        for (int i = 0; i < txt.length(); i++) {
            char ch = txt.toCharArray()[i];
            System.out.print(ch);
            try {
                Thread.sleep(TEXT_SPEED);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public MessengerComponent newLine() {
        text.append("\n");
        return this;
    }

    public MessengerComponent end() {
        text.append(MessengerTextColor.ANSI_RESET);
        return this;
    }
}
