package com.hunor.petrificate;

import com.hunor.petrificate.model.StoneStatueModel;
import com.hunor.petrificate.renderer.PetrificationDeviceRenderer;
import com.hunor.petrificate.renderer.StoneStatueRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PetrificateClient implements ClientModInitializer {
	public static final Identifier FANTASY_VS_SCIENCE = Identifier.of("petrificate", "fantasyvsscience");

	@Override
	public void onInitializeClient() {
		Registry.register(Registries.SOUND_EVENT, FANTASY_VS_SCIENCE, SoundEvent.of(FANTASY_VS_SCIENCE));

		EntityRendererRegistry.register(Petrificate.PETRIFICATION_DEVICE, PetrificationDeviceRenderer::new);
		EntityRendererRegistry.register(Petrificate.PETRIFICATION_WAVE, EmptyEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(StoneStatueModel.STONESTATUE, StoneStatueModel::getTexturedModelData);
		EntityRendererRegistry.register(Petrificate.STONE_STATUE, StoneStatueRenderer::new);

	}
}