package net.sixunderscore.oldvisuals.mixin.item;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.sixunderscore.oldvisuals.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Unique private float swingProgress;

    @Redirect(
            method = "updateHeldItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"
            )
    )
    private float redirectGetAttackCooldownProgress(ClientPlayerEntity clientPlayerEntity, float baseTime) {
        if (Config.enabledNoCooldownAnimation())
            return 1.0F;
        return clientPlayerEntity.getAttackCooldownProgress(baseTime);
    }

    @Inject(method = "swingArm", at = @At("HEAD"))
    private void getSwingProgress(float swingProgress, float equipProgress, MatrixStack matrices, int armX, Arm arm, CallbackInfo info) {
        this.swingProgress = swingProgress;
    }

    @Redirect(
            method = "swingArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V"
            )
    )
    private void redirectApplyEquipOffset(HeldItemRenderer heldItemRenderer, MatrixStack matrices, Arm arm, float equipProgress) {
        if (Config.enabledNoCooldownAnimation()) {
            if (swingProgress != 0.0)
                this.applyEquipOffset(matrices, arm, 0.0F);
            else
                this.applyEquipOffset(matrices, arm, equipProgress);
        }
        else
            this.applyEquipOffset(matrices, arm, equipProgress);
    }

    @Shadow
    private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {}
}
