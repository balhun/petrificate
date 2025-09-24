package com.hunor.petrificate;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PetrificateClient implements ClientModInitializer {
	public static final Identifier MENU_MUSIC = Identifier.of("petrificate", "fantasyvsscience");
	public static final RegistryEntry<SoundEvent> MENU_MUSIC_EVENT = RegistryEntry.of(SoundEvent.of(MENU_MUSIC));

	@Override
	public void onInitializeClient() {
		Registry.register(Registries.SOUND_EVENT, MENU_MUSIC, SoundEvent.of(MENU_MUSIC));

		EntityRendererRegistry.register(Petrificate.PETRIFICATION_DEVICE, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(Petrificate.PETRIFICATION_WAVE, EmptyEntityRenderer::new);
		//EntityRendererRegistry.register(Petrificate.STONE_STATUE, (context) -> new StoneStatueRenderer(context, false));

	}
}