package com.hunor.petrificate.entity;

import com.hunor.petrificate.sound.ModSounds;
import com.hunor.petrificate.Petrificate;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
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

    private ItemStack originalStack = ItemStack.EMPTY;

    public void setOriginalStack(ItemStack stack) {
        this.originalStack = stack.copy();
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive++;

        if (!(getWorld() instanceof ServerWorld serverWorld)) return;

        if (ticksAlive == 1) {
            this.getWorld().playSound(
                    null,                          // ha null, minden játékos hallja
                    this.getX(), this.getY(), this.getZ(), // a pozíció, ahonnan szól
                    ModSounds.petrification_device_sound,  // a te sound event-ed
                    SoundCategory.HOSTILE,         // kategória (pl. PLAYERS / AMBIENT / HOSTILE)
                    4.0f,                          // hangerő
                    1.0f                           // pitch
            );

        }

        if (ticksAlive == 18) {
           /* if (!this.getWorld().isClient) {
                BlockPos pos = this.getBlockPos();

                if (this.getWorld().getBlockState(pos).isAir()) {
                    this.getWorld().setBlockState(pos, Blocks.LIGHT.getDefaultState().with(LightBlock.LEVEL_15, 10));
                }

            }*/

            returnDevice();
        }

        if (ticksAlive >= 18 && ticksAlive < 30) {
            radius += 0.1f;
            spawnParticles(serverWorld, new Vector3f(0f, 0.5f, 0f), 1.0f, 200); // Dark green phase
            petrifyEntities(serverWorld);
        }

        if (ticksAlive >= 30 && ticksAlive <= 50) {
            radius += 0.1f;
            spawnParticles(serverWorld, new Vector3f(0f, 1f, 0f), 2.0f, 300); // Bright green phase
            petrifyEntities(serverWorld);
        }

        if (ticksAlive > 120) {
            /*BlockPos pos = this.getBlockPos();
            if (this.getWorld().getBlockState(pos).isOf(Blocks.LIGHT)) {
                this.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
            }*/
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

            /*entity.addStatusEffect(new StatusEffectInstance(
                    Petrificate.PETRIFICATION_EFFECT,
                    -1,
                    0,
                    false,
                    false,
                    false
            ));*/

            if (entity instanceof AnimalEntity animal) {
                animal.setAiDisabled(true);
                entity.setSilent(true);

                /*this.getWorld().playSound(
                        null,                          // ha null, minden játékos hallja
                        this.getX(), this.getY(), this.getZ(), // a pozíció, ahonnan szól
                        ModSounds.petrificating,  // a te sound event-ed
                        SoundCategory.HOSTILE,         // kategória (pl. PLAYERS / AMBIENT / HOSTILE)
                        2.0f,                          // hangerő
                        1.0f                           // pitch
                );*/

            } else if (entity instanceof MobEntity mob) {
                mob.setAiDisabled(true);
                entity.setSilent(true);

                /*this.getWorld().playSound(
                        null,                          // ha null, minden játékos hallja
                        this.getX(), this.getY(), this.getZ(), // a pozíció, ahonnan szól
                        ModSounds.petrificating,  // a te sound event-ed
                        SoundCategory.HOSTILE,         // kategória (pl. PLAYERS / AMBIENT / HOSTILE)
                        2.0f,                          // hangerő
                        1.0f                           // pitch
                );*/

            } else if (entity instanceof ServerPlayerEntity player) {
                if (!player.isCreative()) {
                    StoneStatueEntity stoneStatueEntity = new StoneStatueEntity(Petrificate.STONE_STATUE, this.getWorld());
                    stoneStatueEntity.setPosition(player.getX(), player.getY(), player.getZ());
                    stoneStatueEntity.setAngles(player.getYaw(), player.getPitch());
                    stoneStatueEntity.setHeadYaw(player.getHeadYaw());

                    this.getWorld().spawnEntity(stoneStatueEntity);

                    System.out.println("SPAWN");
                    entity.damage(entity.getDamageSources().magic(), Float.MAX_VALUE);
                }
            }
        }

    }


    private void returnDevice() {
        if (deviceOwner != null && !deviceOwner.isCreative()) {
            originalStack.setDamage(originalStack.getDamage() + 5);

            ItemEntity item = new ItemEntity(getWorld(), getX(), getY(), getZ(), originalStack.copy());
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