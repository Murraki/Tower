package ru.tower.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;

@UtilityClass
public class Rnd {

    private final Random random = new Random(System.currentTimeMillis());

    public boolean calc(int chance, int limit) {
        return chance > get(limit);
    }

    public int get(int bound) {return random.nextInt(0, bound + 1);}

    public int get(int min, int max) {
        return random.nextInt(min, max + 1);
    }

    public boolean get() {
        return random.nextBoolean();
    }

    public <T> T get(List<T> list) {
        return list.get(get(list.size() - 1));
    }

    public <T> T get(T[] array) {
        return array[(get(array.length - 1))];
    }

}
