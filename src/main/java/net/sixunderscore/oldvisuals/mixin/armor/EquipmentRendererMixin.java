package net.sixunderscore.oldvisuals.mixin.armor;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sixunderscore.oldvisuals.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.sixunderscore.oldvisuals.util.EntityHurtStateAccessor;

@Mixin(EquipmentRenderer.class)
public class EquipmentRendererMixin implements EntityHurtStateAccessor {
    @Unique private boolean entityHurt;

    @Redirect(
            method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"
            )
    )
    private static RenderLayer redirectGetRenderLayer(Identifier identifier) {
        if (Config.enabledRedArmor())
            return RenderLayer.getEntityTranslucent(identifier);
        else
            return RenderLayer.getArmorCutoutNoCull(identifier);
    }

    @Redirect(
            method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"
            )
    )
    private void redirectRender(Model model, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        if (Config.enabledRedArmor())
            model.render(matrices, vertexConsumer, light, OverlayTexture.packUv(0, OverlayTexture.getV(entityHurt)), color);
        else
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    @Redirect(
            method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;"
            )
    )
    private static RenderLayer redirectGetRenderLayerTrim(boolean decal) {
        if (Config.enabledRedArmor())
            return RenderLayer.getEntityTranslucent(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
        else
            return TexturedRenderLayers.getArmorTrims(decal);
    }

    @Redirect(
            method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"
            )
    )
    private void redirectRenderTrim(Model model, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        if (Config.enabledRedArmor())
            model.render(matrices, vertexConsumer, light, OverlayTexture.packUv(0, OverlayTexture.getV(entityHurt)));
        else
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    }

    @Override
    public void setEntityHurt(boolean hurt) {
        this.entityHurt = hurt;
    }
}
