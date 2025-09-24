package com.hunor.petrificate;

import com.hunor.petrificate.ModItems;
import com.hunor.petrificate.ModSounds;
import com.hunor.petrificate.Petrificate;
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
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;

public class PetrificationWaveEntity extends Entity {
    private ServerPlayerEntity deviceOwner;
    private int ticksAlive = 0;
    private float radius = 0.1f;

    public PetrificationWaveEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public PetrificationWaveEntity(World world, double x, double y, double z, ServerPlayerEntity owner) {
        this(Petrificate.PETRIFICATION_WAVE, world);
        this.setPosition(x, y, z);
        deviceOwner = owner;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive++;

        if (!(getWorld() instanceof ServerWorld serverWorld)) return;

        if (ticksAlive == 1) {
            playSound(ModSounds.petrification_device_sound, 2.0f, 1.0f);
        }


        if (ticksAlive >= 18 && ticksAlive < 30) {
            radius += 0.1f;
            spawnParticles(serverWorld, new Vector3f(0f, 0.5f, 0f), 1.0f, 200); // Dark green phase
        }

        if (ticksAlive >= 30) {
            radius += 0.1f;
            spawnParticles(serverWorld, new Vector3f(0f, 1f, 0f), 2.0f, 300); // Bright green phase
            petrifyEntities(serverWorld);
        }

        if (ticksAlive > 50) {
            returnDevice();
            discard();
        }
    }

    private void spawnParticles(ServerWorld world, Vector3f color, float size, int baseCount) {
        int particleCount = (int) (baseCount * (radius / 5f));
        particleCount = Math.max(50, Math.min(particleCount, 300));

        for (int i = 0; i < particleCount; i++) {
            double theta = random.nextDouble() * 2 * Math.PI;
            double phi = random.nextDouble() * Math.PI;

            double x = Math.sin(phi) * Math.cos(theta) * radius;
            double y = Math.cos(phi) * radius;
            double z = Math.sin(phi) * Math.sin(theta) * radius;

            world.spawnParticles(new DustParticleEffect(color, size),
                    getX() + x, getY() + y, getZ() + z,
                    1, 0.2, 0.2, 0.2, 0.3); // More speed for movement
        }
    }

    private void petrifyEntities(ServerWorld world) {
        List<LivingEntity> entities = world.getEntitiesByClass(
                LivingEntity.class,
                new Box(getPos().add(-radius, -radius, -radius), getPos().add(radius, radius, radius)),
                LivingEntity::isAlive
        );

        for (LivingEntity entity : entities) {
            if (entity instanceof AnimalEntity animal) {
                animal.setAiDisabled(true);
                entity.setSilent(true);
            } else if (entity instanceof MobEntity mob) {
                mob.setAiDisabled(true);
                entity.setSilent(true);
            } else {
                entity.damage(getDamageSources().magic(), Float.MAX_VALUE);
            }
        }
    }

    private void returnDevice() {
        if (deviceOwner != null && !deviceOwner.isCreative()) {
            ItemEntity item = new ItemEntity(getWorld(), getX(), getY(), getZ(),
                    new ItemStack(ModItems.PETRIFICATION_DEVICE));
            getWorld().spawnEntity(item);
        }
    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}
    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}
}