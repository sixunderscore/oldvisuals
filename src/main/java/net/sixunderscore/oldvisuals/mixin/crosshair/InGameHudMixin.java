package net.sixunderscore.oldvisuals.mixin.crosshair;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import net.sixunderscore.oldvisuals.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Redirect(
            method = "renderCrosshair",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/GameOptions;getPerspective()Lnet/minecraft/client/option/Perspective;"
            )
    )
    private Perspective redirectGetPerspective(GameOptions options) {
        if (Config.enabledThirdPersonCrosshair()) {
            return Perspective.FIRST_PERSON;
        }
        return options.getPerspective();
    }
}
