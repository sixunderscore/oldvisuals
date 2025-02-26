package net.sixunderscore.oldvisuals.config;

import net.sixunderscore.oldvisuals.config.enums.FlatDroppedItemRenderMode;

import java.util.Map;
import java.util.function.Consumer;

public class RuntimeData {
    private static boolean enabledThirdPersonCrosshair;
    private static boolean enabledRedArmor;
    private static boolean enabledNoCooldownAnimation;
    private static boolean enabledOldThirdPersonTool;
    private static boolean enabledOldThirdPersonItem;
    private static boolean enabledOldFirstPersonRod;
    private static FlatDroppedItemRenderMode flatDroppedItemRenderMode;

    private static final Map<String, Consumer<String>> SETTERS = Map.of(
            ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR, value -> enabledThirdPersonCrosshair = Boolean.parseBoolean(value),
            ConfigKeys.ENABLED_RED_ARMOR, value -> enabledRedArmor = Boolean.parseBoolean(value),
            ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION, value -> enabledNoCooldownAnimation = Boolean.parseBoolean(value),
            ConfigKeys.ENABLED_OLD_THIRD_PERSON_TOOL, value -> enabledOldThirdPersonTool = Boolean.parseBoolean(value),
            ConfigKeys.ENABLED_OLD_THIRD_PERSON_ITEM, value -> enabledOldThirdPersonItem = Boolean.parseBoolean(value),
            ConfigKeys.ENABLED_OLD_FIRST_PERSON_ROD, value -> enabledOldFirstPersonRod = Boolean.parseBoolean(value),
            ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE, value -> flatDroppedItemRenderMode = FlatDroppedItemRenderMode.parseEnum(value)
    );
    private static final Map<String, Runnable> TOGGLE_ACTIONS = Map.of(
            ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR, () -> enabledThirdPersonCrosshair = !enabledThirdPersonCrosshair,
            ConfigKeys.ENABLED_RED_ARMOR, () -> enabledRedArmor = !enabledRedArmor,
            ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION, () -> enabledNoCooldownAnimation = !enabledNoCooldownAnimation,
            ConfigKeys.ENABLED_OLD_THIRD_PERSON_TOOL, () -> enabledOldThirdPersonTool = !enabledOldThirdPersonTool,
            ConfigKeys.ENABLED_OLD_THIRD_PERSON_ITEM, () -> enabledOldThirdPersonItem = !enabledOldThirdPersonItem,
            ConfigKeys.ENABLED_OLD_FIRST_PERSON_ROD, () -> enabledOldFirstPersonRod = !enabledOldFirstPersonRod,
            ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE, () -> flatDroppedItemRenderMode = ConfigUtils.getNextEnumValue(flatDroppedItemRenderMode)
    );

    public static void applySetting(String key, Object value) {
        applySetting(key, value.toString());
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

    public static boolean enabledThirdPersonCrosshair() {
        return enabledThirdPersonCrosshair;
    }

    public static boolean enabledRedArmor() {
        return enabledRedArmor;
    }

    public static boolean enabledNoCooldownAnimation() {
        return enabledNoCooldownAnimation;
    }

    public static boolean enabledOldThirdPersonTool() {
        return enabledOldThirdPersonTool;
    }

    public static boolean enabledOldThirdPersonItem() {
        return enabledOldThirdPersonItem;
    }

    public static boolean enabledOldFirstPersonRod() {
        return enabledOldFirstPersonRod;
    }

    public static FlatDroppedItemRenderMode flatDroppedItemRenderMode() {
        return flatDroppedItemRenderMode;
    }
}
