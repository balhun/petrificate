package com.hunor.petrificate;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Petrificate implements ModInitializer {
	public static final String MOD_ID = "petrificate";

	public static final EntityType<PetrificationDeviceEntity> PETRIFICATION_DEVICE = Registry.register(
			Registries.ENTITY_TYPE,
			Identifier.of(MOD_ID, "petrification_device"),
			EntityType.Builder.<PetrificationDeviceEntity>create(PetrificationDeviceEntity::new, SpawnGroup.MISC)
					.dimensions(0.1f, 0.1f)
					.build());

	public static final EntityType<PetrificationDeviceEntity> PETRIFICATION_WAVE = Registry.register(
			Registries.ENTITY_TYPE,
			Identifier.of(MOD_ID, "petrification_wave"),
			EntityType.Builder.<PetrificationDeviceEntity>create(PetrificationDeviceEntity::new, SpawnGroup.MISC)
					.dimensions(0.1f, 0.1f)
					.build());

	public static final EntityType<StoneStatueEntity> STONE_STATUE = Registry.register(
			Registries.ENTITY_TYPE,
			Identifier.of(MOD_ID, "stone_statue"),
			EntityType.Builder.<StoneStatueEntity>create(StoneStatueEntity::new, SpawnGroup.CREATURE)
					.dimensions(EntityType.PLAYER.getDimensions().width(), EntityType.PLAYER.getDimensions().height())
					.build());

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModSounds.registerSounds();
	}
}