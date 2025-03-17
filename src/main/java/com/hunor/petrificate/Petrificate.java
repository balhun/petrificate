package com.hunor.petrificate;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Petrificate implements ModInitializer {
	public static final String MOD_ID = "petrificate";

	public static final EntityType<PetrificationDeviceEntity> PETRIFICATION_WEAPON = Registry.register(
			Registries.ENTITY_TYPE,
			Identifier.of(MOD_ID, "petrification_weapon"),
			EntityType.Builder.<PetrificationDeviceEntity>create(PetrificationDeviceEntity::new, SpawnGroup.MISC)
					.dimensions(0.15f, 0.15f)
					.build());

	public static final EntityType<PetrificationDeviceEntity> PETRIFICATION_WAVE = Registry.register(
			Registries.ENTITY_TYPE,
			Identifier.of(MOD_ID, "petrification_weapon"),
			EntityType.Builder.<PetrificationDeviceEntity>create(PetrificationDeviceEntity::new, SpawnGroup.MISC)
					.dimensions(0.5f, 0.5f)
					.build());

	@Override
	public void onInitialize() {
		ModItems.initialize();
	}
}