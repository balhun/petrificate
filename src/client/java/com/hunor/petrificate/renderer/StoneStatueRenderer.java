package com.hunor.petrificate.renderer;

import com.hunor.petrificate.Petrificate;
import com.hunor.petrificate.entity.StoneStatueEntity;
import com.hunor.petrificate.model.StoneStatueModel;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class StoneStatueRenderer extends MobEntityRenderer<StoneStatueEntity, StoneStatueModel<StoneStatueEntity>> {

    public StoneStatueRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneStatueModel<>(context.getPart(StoneStatueModel.STONESTATUE)), 0.0f);
    }

    @Override
    public Identifier getTexture(StoneStatueEntity entity) {
        return Identifier.of(Petrificate.MOD_ID, "textures/entity/stonestatue.png");
    }



}