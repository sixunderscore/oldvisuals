package net.sixunderscore.oldvisuals.mixin.item;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.sixunderscore.oldvisuals.config.RuntimeData;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
    @Unique private EntityRenderDispatcher dispatcher;
    @Unique private ItemEntityRenderState renderState;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void getDispatcher(EntityRendererFactory.Context context, CallbackInfo info) {
        this.dispatcher = context.getRenderDispatcher();
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void getRenderState(ItemEntityRenderState itemEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        if (this.renderState != itemEntityRenderState)
            this.renderState = itemEntityRenderState;
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V"
            )
    )
    private void redirectMatrixMultiply(MatrixStack matrixStack, Quaternionf rotation) {
        if (!renderState.itemRenderState.hasDepth()) {
            switch (RuntimeData.flatDroppedItemRenderMode()) {
                case FULL_ROTATION -> matrixStack.multiply(this.dispatcher.getRotation());
                case Y_AXIS_ONLY_ROTATION -> {
                    Quaternionf playerRotation = new Quaternionf(this.dispatcher.getRotation());
                    playerRotation.x = 0;
                    playerRotation.z = 0;
                    matrixStack.multiply(playerRotation);
                }
                case OFF -> matrixStack.multiply(rotation);
            }
        }
        else {
            matrixStack.multiply(rotation);
        }
    }
}
