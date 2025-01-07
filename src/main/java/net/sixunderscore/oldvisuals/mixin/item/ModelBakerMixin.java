package net.sixunderscore.oldvisuals.mixin.item;

import net.minecraft.client.render.model.*;
import net.minecraft.util.Identifier;
import net.sixunderscore.oldvisuals.config.Config;
import net.sixunderscore.oldvisuals.util.OldTransformationType;
import net.sixunderscore.oldvisuals.util.CustomUnbakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.render.model.ModelBaker$BakerImpl")
public class ModelBakerMixin {
    @Unique private static String currentModelId;

    @Inject(method = "bake", at = @At("HEAD"))
    public void getBakedModelId(Identifier id, ModelBakeSettings settings, CallbackInfoReturnable<BakedModel> cir) {
        currentModelId = id.getPath();
    }

    @Redirect(
            method = "bake",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/model/UnbakedModel;bake(Lnet/minecraft/client/render/model/UnbakedModel;Lnet/minecraft/client/render/model/Baker;Lnet/minecraft/client/render/model/ModelBakeSettings;)Lnet/minecraft/client/render/model/BakedModel;"
            )
    )
    private static BakedModel onBakeModel(UnbakedModel unbakedModel, Baker baker, ModelBakeSettings settings) {
        if (Config.enabledOldThirdPersonTool() && currentModelId.contains("sword")) // Applying transformations to all models inheriting from handheld.json, not just swords
            return CustomUnbakedModel.bake(unbakedModel, baker, settings, OldTransformationType.HANDHELD);
        if (Config.enabledOldThirdPersonItem() && currentModelId.equals("item/apple")) // Applying transformations to all models inheriting from generated.json, not just apple
            return CustomUnbakedModel.bake(unbakedModel, baker, settings, OldTransformationType.ITEM);
        if (Config.enabledOldFirstPersonRod() && currentModelId.equals("item/fishing_rod"))
            return CustomUnbakedModel.bake(unbakedModel, baker, settings, OldTransformationType.FISHING_ROD);

        return UnbakedModel.bake(unbakedModel, baker, settings);
    }
}
