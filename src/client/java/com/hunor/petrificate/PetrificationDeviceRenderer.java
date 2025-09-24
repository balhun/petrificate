package com.hunor.petrificate;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class PetrificationDeviceRenderer extends EntityRenderer<PetrificationDeviceEntity> {

    public PetrificationDeviceRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(PetrificationDeviceEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // Kisebb méret (0.5 = fele akkora)
        float scale = 1f;
        matrices.scale(scale, scale, scale);

        // Opcionális: lassú pörgés
        float spin = (entity.age + tickDelta) * 20f;
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(spin));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(spin));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(spin));

        // Item renderelése
        ItemStack stack = entity.getStack();
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                stack, ModelTransformationMode.GROUND,
                light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,
                entity.getWorld(), entity.getId()
        );

        matrices.pop();
    }

    @Override
    public Identifier getTexture(PetrificationDeviceEntity entity) {
        return null;
    }
}