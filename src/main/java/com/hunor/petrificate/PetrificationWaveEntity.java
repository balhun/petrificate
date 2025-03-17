package com.hunor.petrificate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class PetrificationWaveEntity extends Entity {
    private ServerPlayerEntity deviceOwner = null;
    private int ticksAlive = 0;
    private float radius = 0.5f; // Start small

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

        // Expand radius over time
        radius += 0.1f; // Increase per tick

        // Spawn particles to show the effect
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (int i = 0; i < 100; i++) { // More particles for visibility
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

        // After some time, remove the wave entity
        if (ticksAlive > 20) { // Effect lasts 0.25 second
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
