package net.sixunderscore.oldvisuals.mixin.nocooldown;

import net.minecraft.client.player.FirstPersonHandsAndItems;
import net.minecraft.client.player.LocalPlayer;
import net.sixunderscore.oldvisuals.config.RuntimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FirstPersonHandsAndItems.class)
public class FirstPersonHandsAndItemsMixin {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getItemSwapScale(F)F"
            )
    )
    private float redirectGetItemSwapScale(LocalPlayer player, float baseTime) {
        if (RuntimeConfig.enabledNoCooldownAnimation) {
            return 1.0F;
        }

        return player.getAttackStrengthScale(baseTime);
    }
}
