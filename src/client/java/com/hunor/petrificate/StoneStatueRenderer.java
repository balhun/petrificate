package com.hunor.petrificate;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;

public class StoneStatueRenderer extends PlayerEntityRenderer {

    public StoneStatueRenderer(EntityRendererFactory.Context ctx, boolean slim) {
        super(ctx, slim);
    }

    @Override
    public Identifier getTexture(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        return Identifier.of("petrificate", "textures/entity/stone_statue/stone_statue.png");
    }
}
