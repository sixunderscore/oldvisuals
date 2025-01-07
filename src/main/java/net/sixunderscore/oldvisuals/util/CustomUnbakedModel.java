package net.sixunderscore.oldvisuals.util;

import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.item.ModelTransformationMode;

public class CustomUnbakedModel {
    public static BakedModel bake(UnbakedModel model, Baker baker, ModelBakeSettings settings, OldTransformationType type) {
        ModelTextures modelTextures = UnbakedModel.buildTextures(model, baker.getModelNameSupplier());
        boolean ambientOcclusion = UnbakedModel.getAmbientOcclusion(model);
        boolean isSideLit = UnbakedModel.getGuiLight(model).isSide();
        ModelTransformation modelTransformation = switch (type) {
            case ITEM -> applyItemTransformations(model);
            case FISHING_ROD -> applyFishingRodTransformations(model);
            case HANDHELD -> applyHandheldTransformations(model);
        };

        return model.bake(modelTextures, baker, settings, ambientOcclusion, isSideLit, modelTransformation);
    }

    private static ModelTransformation applyItemTransformations(UnbakedModel model) {
        Transformation thirdPersonLeftHand = UnbakedModel.getTransformation(model, ModelTransformationMode.THIRD_PERSON_LEFT_HAND);
        thirdPersonLeftHand.rotation.add(8, -6,9);
        thirdPersonLeftHand.translation.add(-0.05f,-0.01f,0.015f);
        Transformation thirdPersonRightHand = UnbakedModel.getTransformation(model, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND);
        thirdPersonRightHand.rotation.add(8, -6,9);
        thirdPersonRightHand.translation.add(-0.05f,-0.01f,0.015f);
        Transformation firstPersonLeftHand = UnbakedModel.getTransformation(model, ModelTransformationMode.FIRST_PERSON_LEFT_HAND);
        Transformation firstPersonRightHand = UnbakedModel.getTransformation(model, ModelTransformationMode.FIRST_PERSON_RIGHT_HAND);
        Transformation head = UnbakedModel.getTransformation(model, ModelTransformationMode.HEAD);
        Transformation gui = UnbakedModel.getTransformation(model, ModelTransformationMode.GUI);
        Transformation ground = UnbakedModel.getTransformation(model, ModelTransformationMode.GROUND);
        Transformation fixed = UnbakedModel.getTransformation(model, ModelTransformationMode.FIXED);
        return new ModelTransformation(thirdPersonLeftHand, thirdPersonRightHand, firstPersonLeftHand, firstPersonRightHand, head, gui, ground, fixed);
    }

    private static ModelTransformation applyFishingRodTransformations(UnbakedModel model) {
        Transformation thirdPersonLeftHand = UnbakedModel.getTransformation(model, ModelTransformationMode.THIRD_PERSON_LEFT_HAND);
        Transformation thirdPersonRightHand = UnbakedModel.getTransformation(model, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND);
        Transformation firstPersonLeftHand = UnbakedModel.getTransformation(model, ModelTransformationMode.FIRST_PERSON_LEFT_HAND);
        firstPersonLeftHand.scale.add(-0.25f,-0.25f, -0.25f);
        firstPersonLeftHand.translation.add(0,0.17f,0);
        Transformation firstPersonRightHand = UnbakedModel.getTransformation(model, ModelTransformationMode.FIRST_PERSON_RIGHT_HAND);
        firstPersonRightHand.scale.add(-0.25f,-0.25f, -0.25f);
        firstPersonRightHand.translation.add(0,0.17f,0);
        Transformation head = UnbakedModel.getTransformation(model, ModelTransformationMode.HEAD);
        Transformation gui = UnbakedModel.getTransformation(model, ModelTransformationMode.GUI);
        Transformation ground = UnbakedModel.getTransformation(model, ModelTransformationMode.GROUND);
        Transformation fixed = UnbakedModel.getTransformation(model, ModelTransformationMode.FIXED);
        return new ModelTransformation(thirdPersonLeftHand, thirdPersonRightHand, firstPersonLeftHand, firstPersonRightHand, head, gui, ground, fixed);
    }

    private static ModelTransformation applyHandheldTransformations(UnbakedModel model) {
        Transformation thirdPersonLeftHand = UnbakedModel.getTransformation(model, ModelTransformationMode.THIRD_PERSON_LEFT_HAND);
        thirdPersonLeftHand.translation.add(0, -0.011f, 0.013f);
        thirdPersonLeftHand.rotation.add(0,0, 3);
        Transformation thirdPersonRightHand = UnbakedModel.getTransformation(model, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND);
        thirdPersonRightHand.translation.add(0, -0.011f, 0.013f);
        thirdPersonRightHand.rotation.add(0,0, -3);
        Transformation firstPersonLeftHand = UnbakedModel.getTransformation(model, ModelTransformationMode.FIRST_PERSON_LEFT_HAND);
        Transformation firstPersonRightHand = UnbakedModel.getTransformation(model, ModelTransformationMode.FIRST_PERSON_RIGHT_HAND);
        Transformation head = UnbakedModel.getTransformation(model, ModelTransformationMode.HEAD);
        Transformation gui = UnbakedModel.getTransformation(model, ModelTransformationMode.GUI);
        Transformation ground = UnbakedModel.getTransformation(model, ModelTransformationMode.GROUND);
        Transformation fixed = UnbakedModel.getTransformation(model, ModelTransformationMode.FIXED);
        return new ModelTransformation(thirdPersonLeftHand, thirdPersonRightHand, firstPersonLeftHand, firstPersonRightHand, head, gui, ground, fixed);
    }
}
