package com.hunor.petrificate;

import com.hunor.petrificate.effect.PetrificationEffect;
import com.hunor.petrificate.entity.PetrificationDeviceEntity;
import com.hunor.petrificate.entity.RevivalFluidEntity;
import com.hunor.petrificate.entity.StoneStatueEntity;
import com.hunor.petrificate.item.ModItems;
import com.hunor.petrificate.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
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
			Identifier.of(MOD_ID, "stonestatue"),
			EntityType.Builder.create(StoneStatueEntity::new, SpawnGroup.CREATURE)
					.dimensions(EntityType.PLAYER.getDimensions().width(), EntityType.PLAYER.getDimensions().height())
					.build());

	public static final RegistryEntry<StatusEffect> PETRIFICATION_EFFECT;
	static {
		PETRIFICATION_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "petrification"), new PetrificationEffect());
	}

	public static final EntityType<RevivalFluidEntity> REVIVAL_FLUID = Registry.register(
			Registries.ENTITY_TYPE,
			Identifier.of(MOD_ID, "revival_fluid"),
			EntityType.Builder.<RevivalFluidEntity>create(RevivalFluidEntity::new, SpawnGroup.MISC)
					.dimensions(0.1f, 0.1f)
					.maxTrackingRange(4)
					.trackingTickInterval(10)
					.build());

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModSounds.registerSounds();

		FabricDefaultAttributeRegistry.register(STONE_STATUE, StoneStatueEntity.createStoneStatueAttributes());
	}
}