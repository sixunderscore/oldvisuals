package net.sixunderscore.oldvisuals.config;

import net.sixunderscore.oldvisuals.config.enums.FlatDroppedItemRenderMode;

public class RuntimeConfig {
    public static boolean enabledThirdPersonCrosshair;
    public static boolean enabledRedArmor;
    public static boolean enabledNoCooldownAnimation;
    public static FlatDroppedItemRenderMode flatDroppedItemRenderMode;

    public static void applySetting(String key, String value) {
        switch (key) {
            case ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR -> enabledThirdPersonCrosshair = Boolean.parseBoolean(value);
            case ConfigKeys.ENABLED_RED_ARMOR -> enabledRedArmor = Boolean.parseBoolean(value);
            case ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION -> enabledNoCooldownAnimation = Boolean.parseBoolean(value);
            case ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE -> flatDroppedItemRenderMode = FlatDroppedItemRenderMode.parseEnum(value);
            case null, default -> {}
        }
    }

    public static void toggleSetting(String key) {
        switch (key) {
            case ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR -> enabledThirdPersonCrosshair = !enabledThirdPersonCrosshair;
            case ConfigKeys.ENABLED_RED_ARMOR -> enabledRedArmor = !enabledRedArmor;
            case ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION -> enabledNoCooldownAnimation = !enabledNoCooldownAnimation;
            case ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE -> flatDroppedItemRenderMode = ConfigUtils.getNextEnumValue(flatDroppedItemRenderMode);
            case null, default -> {}
        }
    }
}
