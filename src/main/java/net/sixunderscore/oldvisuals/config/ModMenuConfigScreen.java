package net.sixunderscore.oldvisuals.config;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class ModMenuConfigScreen extends Screen {
    private final Map<String, Object> changedSettings;

    public ModMenuConfigScreen(Screen parent) {
        super(Component.literal("OldVisuals"));
        this.changedSettings = new HashMap<>(ConfigKeys.DEFAULT_VALUES.size());
    }

    @Override
    protected void init() {
        Button crosshairButton = Button.builder(
                Component.literal("Third person crosshair: " + ConfigUtils.getFriendlyBoolean(RuntimeConfig.enabledThirdPersonCrosshair)), button -> {
                    RuntimeConfig.toggleSetting(ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR);

                    changedSettings.put(ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR, RuntimeConfig.enabledThirdPersonCrosshair);
                    button.setMessage(Component.literal("Third person crosshair: " + ConfigUtils.getFriendlyBoolean(RuntimeConfig.enabledThirdPersonCrosshair)));
                })
                .bounds(this.width / 2 - 105, 5, 210, 20)
                .build();

        Button redArmorButton = Button.builder(
                Component.literal("Red armor: " + ConfigUtils.getFriendlyBoolean(RuntimeConfig.enabledRedArmor)), button -> {
                    RuntimeConfig.toggleSetting(ConfigKeys.ENABLED_RED_ARMOR);

                    changedSettings.put(ConfigKeys.ENABLED_RED_ARMOR, RuntimeConfig.enabledRedArmor);
                    button.setMessage(Component.literal("Red armor: " + ConfigUtils.getFriendlyBoolean(RuntimeConfig.enabledRedArmor)));
                })
                .bounds(this.width / 2 - 105, 30, 210, 20)
                .build();

        Button cooldownButton = Button.builder(
                Component.literal("No cooldown animation: " + ConfigUtils.getFriendlyBoolean(RuntimeConfig.enabledNoCooldownAnimation)), button -> {
                    RuntimeConfig.toggleSetting(ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION);

                    changedSettings.put(ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION, RuntimeConfig.enabledNoCooldownAnimation);
                    button.setMessage(Component.literal("No cooldown animation: " + ConfigUtils.getFriendlyBoolean(RuntimeConfig.enabledNoCooldownAnimation)));
                })
                .bounds(this.width / 2 - 105, 55, 210, 20)
                .build();

        Button flatItemModeButton = Button.builder(
                Component.literal("2D Dropped Items: " + ConfigUtils.getFriendlyEnum(RuntimeConfig.flatDroppedItemRenderMode)), button -> {
                    RuntimeConfig.toggleSetting(ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE);

                    changedSettings.put(ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE, RuntimeConfig.flatDroppedItemRenderMode);
                    button.setMessage(Component.literal("2D Dropped Items: " + ConfigUtils.getFriendlyEnum(RuntimeConfig.flatDroppedItemRenderMode)));
                })
                .bounds(this.width / 2 - 105, 80, 210, 20)
                .build();

        Button saveAndExitButton = Button.builder(Component.literal("Done"), _ -> this.onClose())
                .bounds(this.width / 2 - 50, this.height - 30, 100, 20)
                .build();

        this.addRenderableWidget(crosshairButton);
        this.addRenderableWidget(redArmorButton);
        this.addRenderableWidget(cooldownButton);
        this.addRenderableWidget(flatItemModeButton);
        this.addRenderableWidget(saveAndExitButton);

        super.init();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void onClose() {
        if (!this.changedSettings.isEmpty()) {
            Config.editConfigFile(this.changedSettings);
        }

        super.onClose();
    }
}
