package net.sixunderscore.oldvisuals.config.enums;

import net.sixunderscore.oldvisuals.config.ConfigKeys;

public enum FlatDroppedItemRenderMode {
    FULL_ROTATION,
    Y_AXIS_ONLY_ROTATION,
    OFF;

    public static FlatDroppedItemRenderMode parseEnum(String name) {
        try {
            return FlatDroppedItemRenderMode.valueOf(name);
        } catch (IllegalArgumentException e) {
            return (FlatDroppedItemRenderMode) ConfigKeys.getDefaultValue(ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE);
        }
    }
}
