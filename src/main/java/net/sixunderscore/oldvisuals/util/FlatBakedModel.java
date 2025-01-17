package net.sixunderscore.oldvisuals.util;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FlatBakedModel implements BakedModel {
    private final BakedModel model;

    public FlatBakedModel(BakedModel model) {
        this.model = model;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        List<BakedQuad> originalQuads = model.getQuads(null, null, random);
        List<BakedQuad> flatModel = new ArrayList<>(1);

        for (BakedQuad quad : originalQuads) {
            if (quad.getFace() == Direction.SOUTH)
                flatModel.add(quad);
        }

        return flatModel;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return model.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return model.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return model.isSideLit();
    }

    @Override
    public Sprite getParticleSprite() {
        return model.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return model.getTransformation();
    }
}
