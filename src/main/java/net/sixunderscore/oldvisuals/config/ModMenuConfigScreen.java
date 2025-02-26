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
                        Text.translatable("oldvisuals.config.button.crosshair", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledThirdPersonCrosshair())),
                        button -> {
                            RuntimeData.toggleSetting(ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR);

                            changedSettings.put(ConfigKeys.ENABLED_THIRD_PERSON_CROSSHAIR, RuntimeData.enabledThirdPersonCrosshair());
                            button.setMessage(Text.translatable("oldvisuals.config.button.crosshair", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledThirdPersonCrosshair())));
                        }
                )
                .dimensions(this.width / 2 - 105, 5, 210, 20)
                .build();

        ButtonWidget redArmorButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.armor", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledRedArmor())),
                        button -> {
                            RuntimeData.toggleSetting(ConfigKeys.ENABLED_RED_ARMOR);

                            changedSettings.put(ConfigKeys.ENABLED_RED_ARMOR, RuntimeData.enabledRedArmor());
                            button.setMessage(Text.translatable("oldvisuals.config.button.armor", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledRedArmor())));
                        }
                )
                .dimensions(this.width / 2 - 105, 30, 210, 20)
                .build();

        ButtonWidget noCooldownButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.cooldown", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledNoCooldownAnimation())),
                        button -> {
                            RuntimeData.toggleSetting(ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION);

                            changedSettings.put(ConfigKeys.ENABLED_NO_COOLDOWN_ANIMATION, RuntimeData.enabledNoCooldownAnimation());
                            button.setMessage(Text.translatable("oldvisuals.config.button.cooldown", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledNoCooldownAnimation())));
                        }
                )
                .dimensions(this.width / 2 - 105, 55, 210, 20)
                .build();

        ButtonWidget thirdPersonToolButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.tool", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledOldThirdPersonTool())),
                        button -> {
                            RuntimeData.toggleSetting(ConfigKeys.ENABLED_OLD_THIRD_PERSON_TOOL);

                            changedSettings.put(ConfigKeys.ENABLED_OLD_THIRD_PERSON_TOOL, RuntimeData.enabledOldThirdPersonTool());
                            button.setMessage(Text.translatable("oldvisuals.config.button.tool", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledOldThirdPersonTool())));
                        }
                )
                .dimensions(this.width / 2 - 105, 80, 210, 20)
                .build();

        ButtonWidget thirdPersonItemButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.item", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledOldThirdPersonItem())),
                        button -> {
                            RuntimeData.toggleSetting(ConfigKeys.ENABLED_OLD_THIRD_PERSON_ITEM);

                            changedSettings.put(ConfigKeys.ENABLED_OLD_THIRD_PERSON_ITEM, RuntimeData.enabledOldThirdPersonItem());
                            button.setMessage(Text.translatable("oldvisuals.config.button.item", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledOldThirdPersonItem())));
                        }
                )
                .dimensions(this.width / 2 - 105, 105, 210, 20)
                .build();

        ButtonWidget firstPersonRodButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.rod", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledOldFirstPersonRod())),
                        button -> {
                            RuntimeData.toggleSetting(ConfigKeys.ENABLED_OLD_FIRST_PERSON_ROD);

                            changedSettings.put(ConfigKeys.ENABLED_OLD_FIRST_PERSON_ROD, RuntimeData.enabledOldFirstPersonRod());
                            button.setMessage(Text.translatable("oldvisuals.config.button.rod", ConfigUtils.getFriendlyBoolean(RuntimeData.enabledOldFirstPersonRod())));
                        }
                )
                .dimensions(this.width / 2 - 105, 130, 210, 20)
                .build();

        ButtonWidget flatItemModeButton = ButtonWidget
                .builder(
                        Text.translatable("oldvisuals.config.button.flat_item", ConfigUtils.getFriendlyEnum(RuntimeData.flatDroppedItemRenderMode())),
                        button -> {
                            RuntimeData.toggleSetting(ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE);

                            changedSettings.put(ConfigKeys.FLAT_DROPPED_ITEMS_RENDER_MODE, RuntimeData.flatDroppedItemRenderMode());
                            button.setMessage(Text.translatable("oldvisuals.config.button.flat_item", ConfigUtils.getFriendlyEnum(RuntimeData.flatDroppedItemRenderMode())));
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
