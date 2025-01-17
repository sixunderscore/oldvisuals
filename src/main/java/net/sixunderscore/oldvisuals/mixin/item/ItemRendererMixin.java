package net.sixunderscore.oldvisuals.mixin.item;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.sixunderscore.oldvisuals.config.Config;
import net.sixunderscore.oldvisuals.util.FlatBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private static BakedModel renderItem(BakedModel arg, ModelTransformationMode transformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int[] tints, BakedModel model, RenderLayer layer, ItemRenderState.Glint glint) {
        if (Config.enabledFlatDroppedItems) {
            if (!arg.hasDepth() && transformationMode == ModelTransformationMode.GROUND)
                return new FlatBakedModel(arg);
            else
                return arg;
        }
        return arg;
    }
}
