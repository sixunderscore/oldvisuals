package net.sixunderscore.oldvisuals.config;

import net.sixunderscore.oldvisuals.config.enums.FlatDroppedItemRenderMode;

import java.util.Map;
import java.util.Set;

public class ConfigKeys {
    public static final String ENABLED_THIRD_PERSON_CROSSHAIR = "EnabledThirdPersonCrosshair";
    public static final String ENABLED_RED_ARMOR = "EnabledRedArmor";
    public static final String ENABLED_NO_COOLDOWN_ANIMATION = "EnabledNoCooldownAnimation";
    public static final String ENABLED_OLD_THIRD_PERSON_TOOL = "EnabledOldThirdPersonTool";
    public static final String ENABLED_OLD_THIRD_PERSON_ITEM = "EnabledOldThirdPersonItem";
    public static final String ENABLED_OLD_FIRST_PERSON_ROD = "EnabledOldThirdPersonRod";
    public static final String FLAT_DROPPED_ITEMS_RENDER_MODE = "FlatDroppedItemsRenderMode";
    private static final Map<String, Object> DEFAULT_VALUES = Map.of(
            ENABLED_THIRD_PERSON_CROSSHAIR, true,
            ENABLED_RED_ARMOR, true,
            ENABLED_NO_COOLDOWN_ANIMATION, true,
            ENABLED_OLD_THIRD_PERSON_TOOL, true,
            ENABLED_OLD_THIRD_PERSON_ITEM, true,
            ENABLED_OLD_FIRST_PERSON_ROD, true,
            FLAT_DROPPED_ITEMS_RENDER_MODE, FlatDroppedItemRenderMode.FULL_ROTATION
    );

    public static Set<String> getAllKeys() {
        return DEFAULT_VALUES.keySet();
    }

    public static Object getDefaultValue(String key) {
        return DEFAULT_VALUES.get(key);
    }
}
