package net.sixunderscore.oldvisuals.mixin.armor;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.sixunderscore.oldvisuals.util.EntityHurtStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {
	@Shadow private EquipmentRenderer equipmentRenderer;

    @Inject(method = "render", at = @At("HEAD"))
	private void injectRender(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BipedEntityRenderState bipedEntityRenderState, float f, float g, CallbackInfo info) {
		if (equipmentRenderer instanceof EntityHurtStateAccessor renderer)
			renderer.setEntityHurt(bipedEntityRenderState.hurt);
	}
}