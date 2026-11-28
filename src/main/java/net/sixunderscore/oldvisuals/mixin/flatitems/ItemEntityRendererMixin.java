package net.sixunderscore.oldvisuals.mixin.flatitems;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.sixunderscore.oldvisuals.config.RuntimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
    @Unique private static final float PI_f = (float) Math.PI;
    @Unique private static final float PI_OVER_2 = PI_f / 2f;

    @Redirect(
            method = "submit",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;rotate(Lcom/mojang/math/Axis;F)V"
            )
    )
    public void rotate(PoseStack instance, Axis axis, float angle, @Local(ordinal = 0) final ItemEntityRenderState state, @Local(ordinal = 0) final CameraRenderState camera) {
        if (state.item.getModelBoundingBox().getZsize() > 0.0625) {
            // keep original spin animation for blocks
            instance.rotate(axis, angle);
            return;
        }

        switch (RuntimeConfig.flatDroppedItemRenderMode) {
            case Y_AXIS_ONLY_ROTATION -> {
                float vecX = (float) (camera.pos.x - state.x);
                float vecZ = (float) (camera.pos.z - state.z);
                instance.rotate(Axis.YP, fastAtan2(vecX, vecZ));
            }
            case FULL_ROTATION -> instance.rotate(camera.orientation);
            case OFF -> instance.rotate(axis, angle);
        }
    }

    /*
     * https://math.stackexchange.com/questions/1098487/atan2-faster-approximation
     * */
    @Unique
    private static float fastAtan2(float y, float x) {
        float ax = Math.abs(x);
        float ay = Math.abs(y);
        float a = Math.min(ax, ay) / Math.max(ax, ay);
        float s = a * a;
        float r = (((-0.0464964749f * s + 0.15931422f) * s - 0.327622764f) * s * a + a);

        if (ay > ax) {
            r = PI_OVER_2 - r;
        }
        if (x < 0) {
            r = PI_f - r;
        }

        return y < 0 ? -r : r;
    }
}
