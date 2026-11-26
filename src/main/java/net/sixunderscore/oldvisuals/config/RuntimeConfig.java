package net.sixunderscore.oldvisuals.config;

import net.sixunderscore.oldvisuals.config.enums.FlatDroppedItemRenderMode;

import java.util.Map;
import java.util.function.Consumer;

public class RuntimeConfig {
    public static boolean enabledThirdPersonCrosshair;
    public static boolean enabledRedArmor;
    public static boolean enabledNoCooldownAnimation;
    public static FlatDroppedItemRenderMode flatDroppedItemRenderMode;

    private static final Map<String, Consumer<String>> SETTERS = Map.of(
            ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR, value -> enabledThirdPersonCrosshair = Boolean.parseBoolean(value),
            ConfigKeys.ENABLED_RED_ARMOR, value -> enabledRedArmor = Boolean.parseBoolean(value),
            ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION, value -> enabledNoCooldownAnimation = Boolean.parseBoolean(value),
            ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE, value -> flatDroppedItemRenderMode = FlatDroppedItemRenderMode.parseEnum(value)
    );

    private static final Map<String, Runnable> TOGGLE_ACTIONS = Map.of(
            ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR, () -> enabledThirdPersonCrosshair = !enabledThirdPersonCrosshair,
            ConfigKeys.ENABLED_RED_ARMOR, () -> enabledRedArmor = !enabledRedArmor,
            ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION, () -> enabledNoCooldownAnimation = !enabledNoCooldownAnimation,
            ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE, () -> flatDroppedItemRenderMode = ConfigUtils.getNextEnumValue(flatDroppedItemRenderMode)
    );

    public static void applySetting(String key, Object value) {
        String str;

        if (value != null) {
            str = value.toString();
        } else {
            str = ConfigKeys.DEFAULT_VALUES.get(key).toString();
        }

        applySetting(key, str);
    }

    public static void applySetting(String key, String value) {
        Consumer<String> setter = SETTERS.get(key);

        if (setter != null) {
            setter.accept(value);
        }
    }

    public static void toggleSetting(String key) {
        Runnable toggleAction = TOGGLE_ACTIONS.get(key);

        if (toggleAction != null) {
            toggleAction.run();
        }
    }
}
