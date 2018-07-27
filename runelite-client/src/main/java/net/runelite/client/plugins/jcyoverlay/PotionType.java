package net.runelite.client.plugins.jcyoverlay;

import java.util.ArrayList;
import java.util.Arrays;

import static net.runelite.api.ItemID.*;


public enum PotionType {

    OVERLOAD(OVERLOAD_1, OVERLOAD_2, OVERLOAD_3, OVERLOAD_4),
    SUPER_MAGIC(SUPER_MAGIC_POTION_1, SUPER_MAGIC_POTION_2, SUPER_MAGIC_POTION_3, SUPER_MAGIC_POTION_4),
    SUPER_RANGE(SUPER_RANGING_1, SUPER_RANGING_2, SUPER_RANGING_3, SUPER_RANGING_4),
    COMBAT(COMBAT_POTION1, COMBAT_POTION2, COMBAT_POTION3, COMBAT_POTION4);

    private ArrayList<Integer> ids = new ArrayList<>();

    PotionType(Integer... ids) {
        this.ids.addAll(Arrays.asList(ids));
    }

    public static PotionType get(int itemId)
    {
        return Arrays.asList(PotionType.values()).stream().filter(pot -> pot.ids.contains(itemId)).findFirst().get();
    }

}
