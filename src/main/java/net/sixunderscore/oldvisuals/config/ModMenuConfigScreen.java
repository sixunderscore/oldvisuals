package net.sixunderscore.oldvisuals.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class ModMenuConfigScreen extends Screen {
    private final Screen parent;
    private final Map<String, Object> changedSettings = new HashMap<>();
    private final String[] RESOURCE_RELOAD_SETTINGS = {ConfigKeys.ENABLED_OLD_THIRD_PERSON_TOOL, ConfigKeys.ENABLED_OLD_THIRD_PERSON_ITEM, ConfigKeys.ENABLED_OLD_FIRST_PERSON_ROD};

    public ModMenuConfigScreen(Screen parent) {
        super(Text.translatable("oldvisuals.config.screen.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ButtonWidget crosshairButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.crosshair", getFriendlyName(Config.enabledThirdPersonCrosshair)),
                        button -> {
                            Config.enabledThirdPersonCrosshair = !Config.enabledThirdPersonCrosshair;

                            addOrRemoveSettingToMap(ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR, Config.enabledThirdPersonCrosshair);
                            button.setMessage(Text.translatable("oldvisuals.config.button.crosshair", getFriendlyName(Config.enabledThirdPersonCrosshair)));
                        }
                )
                .dimensions(this.width / 2 - 105, 5, 210, 20)
                .build();

        ButtonWidget redArmorButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.armor", getFriendlyName(Config.enabledRedArmor)),
                        button -> {
                            Config.enabledRedArmor = !Config.enabledRedArmor;

                            addOrRemoveSettingToMap(ConfigKeys.ENABLED_RED_ARMOR, Config.enabledRedArmor);
                            button.setMessage(Text.translatable("oldvisuals.config.button.armor", getFriendlyName(Config.enabledRedArmor)));
                        }
                )
                .dimensions(this.width / 2 - 105, 30, 210, 20)
                .build();

        ButtonWidget noCooldownButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.cooldown", getFriendlyName(Config.enabledNoCooldownAnimation)),
                        button -> {
                            Config.enabledNoCooldownAnimation = !Config.enabledNoCooldownAnimation;

                            addOrRemoveSettingToMap(ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION, Config.enabledNoCooldownAnimation);
                            button.setMessage(Text.translatable("oldvisuals.config.button.cooldown", getFriendlyName(Config.enabledNoCooldownAnimation)));
                        }
                )
                .dimensions(this.width / 2 - 105, 55, 210, 20)
                .build();

        ButtonWidget thirdPersonToolButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.tool", getFriendlyName(Config.enabledOldThirdPersonTool)),
                        button -> {
                            Config.enabledOldThirdPersonTool = !Config.enabledOldThirdPersonTool;

                            addOrRemoveSettingToMap(ConfigKeys.ENABLED_OLD_THIRD_PERSON_TOOL, Config.enabledOldThirdPersonTool);
                            button.setMessage(Text.translatable("oldvisuals.config.button.tool", getFriendlyName(Config.enabledOldThirdPersonTool)));
                        }
                )
                .dimensions(this.width / 2 - 105, 80, 210, 20)
                .build();

        ButtonWidget thirdPersonItemButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.item", getFriendlyName(Config.enabledOldThirdPersonItem)),
                        button -> {
                            Config.enabledOldThirdPersonItem = !Config.enabledOldThirdPersonItem;

                            addOrRemoveSettingToMap(ConfigKeys.ENABLED_OLD_THIRD_PERSON_ITEM, Config.enabledOldThirdPersonItem);
                            button.setMessage(Text.translatable("oldvisuals.config.button.item", getFriendlyName(Config.enabledOldThirdPersonItem)));
                        }
                )
                .dimensions(this.width / 2 - 105, 105, 210, 20)
                .build();

        ButtonWidget firstPersonRodButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.rod", getFriendlyName(Config.enabledOldFirstPersonRod)),
                        button -> {
                            Config.enabledOldFirstPersonRod = !Config.enabledOldFirstPersonRod;

                            addOrRemoveSettingToMap(ConfigKeys.ENABLED_OLD_FIRST_PERSON_ROD, Config.enabledOldFirstPersonRod);
                            button.setMessage(Text.translatable("oldvisuals.config.button.rod", getFriendlyName(Config.enabledOldFirstPersonRod)));
                        }
                )
                .dimensions(this.width / 2 - 105, 130, 210, 20)
                .build();

        ButtonWidget flatItemModeButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.flat_item", getFriendlyName(Config.enabledFlatDroppedItems)),
                        button -> {
                            Config.enabledFlatDroppedItems = !Config.enabledFlatDroppedItems;

                            addOrRemoveSettingToMap(ConfigKeys.ENABLED_FLAT_DROPPED_ITEMS, Config.enabledFlatDroppedItems);
                            button.setMessage(Text.translatable("oldvisuals.config.button.flat_item", getFriendlyName(Config.enabledFlatDroppedItems)));
                        }
                )
                .dimensions(this.width / 2 - 105, 155, 210, 20)
                .build();

        ButtonWidget saveButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.done"),
                        button -> {
                            if (!changedSettings.isEmpty())
                                Config.editConfigFile(changedSettings);

                            if (this.client != null) {
                                if (shouldReloadResources())
                                    this.client.reloadResources();

                                this.client.setScreen(parent);
                            }
                        }
                )
                .dimensions(this.width / 2 - 50, this.height - 30, 100, 20)
                .build();

        this.addDrawableChild(crosshairButton);
        this.addDrawableChild(redArmorButton);
        this.addDrawableChild(noCooldownButton);
        this.addDrawableChild(thirdPersonToolButton);
        this.addDrawableChild(thirdPersonItemButton);
        this.addDrawableChild(firstPersonRodButton);
        this.addDrawableChild(flatItemModeButton);
        this.addDrawableChild(saveButton);
    }

    // Prevent unnecessary writes to config file
    private void addOrRemoveSettingToMap(String key, boolean value) {
        if (changedSettings.containsKey(key))
            changedSettings.remove(key);
        else
            changedSettings.put(key, value);
    }

    private String getFriendlyName(boolean value) {
        return value ? "On" : "Off";
    }

    private boolean shouldReloadResources() {
        for (String resourceReloadSetting : RESOURCE_RELOAD_SETTINGS) {
            if (changedSettings.containsKey(resourceReloadSetting))
                return true;
        }

        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
