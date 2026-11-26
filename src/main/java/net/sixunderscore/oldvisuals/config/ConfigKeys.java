package net.sixunderscore.oldvisuals.config;

import net.sixunderscore.oldvisuals.config.enums.FlatDroppedItemRenderMode;

import java.util.Map;

public class ConfigKeys {
    public static final String ENABLED_THIRD_PERSON_CROSSHAIR = "EnabledThirdPersonCrosshair";
    public static final String ENABLED_RED_ARMOR = "EnabledRedArmor";
    public static final String ENABLED_NO_COOLDOWN_ANIMATION = "EnabledNoCooldownAnimation";
    public static final String FLAT_DROPPED_ITEMS_RENDER_MODE = "FlatDroppedItemsRenderMode";
    public static final Map<String, Object> DEFAULT_VALUES = Map.of(
            ENABLED_THIRD_PERSON_CROSSHAIR, true,
            ENABLED_RED_ARMOR, true,
            ENABLED_NO_COOLDOWN_ANIMATION, true,
            FLAT_DROPPED_ITEMS_RENDER_MODE, FlatDroppedItemRenderMode.Y_AXIS_ONLY_ROTATION
    );
}
