package ru.tower.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
public class MonsterData {

        private final int id;
        private final int level;
        private final String name;
        private final String race;
        private final int expReward;
        private final int goldReward;
}
