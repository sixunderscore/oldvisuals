package net.sixunderscore.oldvisuals.mixin.flatitems;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.ItemQuads;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.sixunderscore.oldvisuals.config.RuntimeConfig;
import net.sixunderscore.oldvisuals.config.enums.FlatDroppedItemRenderMode;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(CuboidItemModelWrapper.class)
public class CuboidItemModelWrapperMixin {
    @Shadow @Final private ModelRenderProperties properties;
    @Shadow @Final private ItemQuads itemQuads;
    @Unique private ItemQuads flatItemQuads;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void injectConstructor(final List<ItemTintSource> tints, final QuadCollection quads, final ModelRenderProperties properties, final Matrix4fc transformation, CallbackInfo ci) {
        if (!this.properties.usesBlockLight()) {
            this.flatItemQuads = new ItemQuads(
                    filterFaces(this.itemQuads.all()),
                    filterFaces(this.itemQuads.solid()),
                    filterFaces(this.itemQuads.translucent())
            );
        }
    }

    @Redirect(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;setQuads(Lnet/minecraft/client/resources/model/geometry/ItemQuads;)V"
            )
    )
    private void redirectSetQuads(ItemStackRenderState.LayerRenderState instance, ItemQuads quads, @Local(ordinal = 0) ItemDisplayContext displayContext)  {
        if (RuntimeConfig.flatDroppedItemRenderMode != FlatDroppedItemRenderMode.OFF && displayContext == ItemDisplayContext.GROUND && !this.properties.usesBlockLight()) {
            instance.setQuads(this.flatItemQuads);
        } else {
            instance.setQuads(quads);
        }
    }


    @Unique
    private static List<BakedQuad> filterFaces(List<BakedQuad> quads) {
        List<BakedQuad> flat = new ArrayList<>(quads.size());

        for (BakedQuad quad : quads) {
            if (quad.direction() == Direction.SOUTH) {
                flat.add(quad);
            }
        }

        return flat;
    }
}
