package com.hunor.petrificate;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;

public class PetrificationWaveEntity extends Entity {
    private ServerPlayerEntity deviceOwner = null;
    private int ticksAlive = 0;
    private float radius = 0f; // Start small

    public PetrificationWaveEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public PetrificationWaveEntity(World world, double x, double y, double z, ServerPlayerEntity owner) {
        this(Petrificate.PETRIFICATION_WAVE, world); // Register in your mod entity registry
        this.setPosition(x, y, z);
        deviceOwner = owner;
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive++;

        // Expand radius over time// Increase per tick

        if (this.getWorld() instanceof ServerWorld serverWorld) {

            // Get all entities in a spherical area
            List<LivingEntity> entities = serverWorld.getEntitiesByClass(
                    LivingEntity.class,
                    new Box(this.getPos().add(-radius, -radius, -radius), this.getPos().add(radius, radius, radius)),
                    LivingEntity::isAlive
            );


            if (ticksAlive == 1) {
                this.getWorld().playSound(
                        null, // No specific source entity
                        this.getBlockPos(), // The position the sound originates from
                        ModSounds.petrification_device_sound1, // Your custom sound event
                        SoundCategory.BLOCKS, // Sound category (adjust if needed)
                        2.0f, // Volume
                        1.0f  // Pitch (adjust as needed)
                );
            }

            // Small dark green circle starts at tick 5
            if (ticksAlive >= 18 && ticksAlive < 30) {
                radius += 0.05f;

                for (int i = 0; i < 50; i++) { // Fewer particles for inner effect
                    double theta = random.nextDouble() * 2 * Math.PI;
                    double phi = random.nextDouble() * Math.PI;

                    double xOffset = Math.sin(phi) * Math.cos(theta) * radius;
                    double yOffset = Math.cos(phi) * radius;
                    double zOffset = Math.sin(phi) * Math.sin(theta) * radius;

                    serverWorld.spawnParticles(new DustParticleEffect(new Vector3f(0f, 0.5f, 0f), 1.0f), // Dark green
                            this.getX() + xOffset, this.getY() + yOffset, this.getZ() + zOffset,
                            1, 0, 0, 0, 2.0);
                }
            }

            /*

                MUTE THE ANIMALS
                CUSTOM OPAQUE PARTICLE


            */

            if (ticksAlive >= 30) {
                radius += 0.05f;

                for (LivingEntity entity : entities) {

                    // Store the death position
                    Vec3d deathPos = entity.getPos();

                    // Check if the entity is an animal (or any non-hostile entity you want to disable AI for)
                    if (entity instanceof AnimalEntity) {
                        // Disable AI
                        ((AnimalEntity) entity).setAiDisabled(true);
                    } else if (entity instanceof MobEntity) {
                        // Disable AI
                        ((MobEntity) entity).setAiDisabled(true);
                    } else {
                        // Kill other entities instantly
                        entity.damage(this.getDamageSources().magic(), Float.MAX_VALUE);
                    }

                    /* Spawn a statue entity at their death location
                    StoneStatueEntity statue = new StoneStatueEntity(Petrificate.STONE_STATUE, serverWorld);
                    statue.refreshPositionAndAngles(deathPos.x, deathPos.y, deathPos.z, entity.getYaw(), entity.getPitch());*/
                }

                // Spawn particles for visibility
                for (int i = 0; i < 100; i++) {
                    double theta = random.nextDouble() * 2 * Math.PI; // Random angle around the Y-axis
                    double phi = random.nextDouble() * Math.PI; // Random angle from the top (0) to the bottom (π)

                    // Convert spherical coordinates to Cartesian
                    double xOffset = Math.sin(phi) * Math.cos(theta) * radius;
                    double yOffset = Math.cos(phi) * radius;
                    double zOffset = Math.sin(phi) * Math.sin(theta) * radius;

                    serverWorld.spawnParticles(new DustParticleEffect(new Vector3f(0f, 1f, 0f), 1.5f),
                            this.getX() + xOffset, this.getY() + yOffset, this.getZ() + zOffset,
                            1, 0, 0, 0, 3.0);
                }
            }
        }

        // After some time, remove the wave entity
        if (ticksAlive > 50) { // Effect lasts 0.25 second
            this.discard();
            // Drop item at impact location
            if (deviceOwner != null) {
                if (!deviceOwner.isCreative()) {
                    ItemStack itemStack = new ItemStack(ModItems.PETRIFICATION_DEVICE);
                    ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
                    this.getWorld().spawnEntity(itemEntity);
                }
            }
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}

    @Override
    public int getMaxAir() {
        return 512;
    }
}
