package net.sixunderscore.oldvisuals.config.enums;

public enum FlatDroppedItemRenderMode {
    FULL_ROTATION,
    Y_AXIS_ONLY_ROTATION,
    OFF;

    public static FlatDroppedItemRenderMode parseEnum(String name) {
        try {
            return FlatDroppedItemRenderMode.valueOf(name);
        } catch (IllegalArgumentException e) {
            return Y_AXIS_ONLY_ROTATION;
        }
    }
}
