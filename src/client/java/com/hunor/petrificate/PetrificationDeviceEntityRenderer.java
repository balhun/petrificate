package com.hunor.petrificate;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PetrificationDeviceEntityRenderer extends EntityRenderer<PetrificationDeviceEntity> {

    private static final Identifier TEXTURE = Identifier.of(Petrificate.MOD_ID, "petrification_device");

    public PetrificationDeviceEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(PetrificationDeviceEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        // Apply transformations and render the entity
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(PetrificationDeviceEntity entity) {
        return TEXTURE;
    }
}
