package com.hunor.petrificate;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class PetrificateClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(Petrificate.PETRIFICATION_DEVICE, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(Petrificate.PETRIFICATION_WAVE, EmptyEntityRenderer::new);
		//EntityRendererRegistry.register(Petrificate.STONE_STATUE, (context) -> new StoneStatueRenderer(context, false));

	}
}