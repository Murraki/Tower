package ru.tower.loader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.tower.data.ItemData;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class ItemLoader {

    private static List<ItemData> items;

    static {
        loadItemsFromJson();
    }

    private static void loadItemsFromJson() {
        try (FileReader reader = new FileReader("items.json")) {
            Type itemListType = new TypeToken<List<ItemData>>(){}.getType();
            items = new Gson().fromJson(reader, itemListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ItemData getRandomItemForFloor(int floorNumber) {
        List<ItemData> suitableItems = items.stream()
                .filter(i -> i.getLevel() <= floorNumber)
                .collect(Collectors.toList());

        if (suitableItems.isEmpty()) {
            return items.get(0);
        }

        int randomIndex = (int) (Math.random() * suitableItems.size());
        return suitableItems.get(randomIndex);
    }
}
