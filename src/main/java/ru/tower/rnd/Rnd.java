package ru.tower.rnd;

import java.util.List;
import java.util.Random;

public class Rnd {

    private static final Random random = new Random(System.currentTimeMillis());

    public static boolean calc(int chance, int limit) {
        return chance > get(limit);
    }

    public static int get(int bound) {return random.nextInt(0, bound + 1);}

    public static int get(int min, int max) {
        return random.nextInt(min, max + 1);
    }

    public static boolean get() {
        return random.nextBoolean();
    }

    public static <T> T get(List<T> list) {
        return list.get(get(list.size() - 1));
    }

    public static <T> T get(T[] array) {
        return array[(get(array.length - 1))];
    }

}
