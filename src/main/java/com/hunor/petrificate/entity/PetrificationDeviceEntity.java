package com.hunor.petrificate.entity;

import com.hunor.petrificate.sound.ModSounds;
import com.hunor.petrificate.Petrificate;
import com.hunor.petrificate.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PetrificationDeviceEntity extends ThrownItemEntity {
    ServerPlayerEntity deviceOwner = null;

    public PetrificationDeviceEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public PetrificationDeviceEntity(World world, LivingEntity owner) {
        super(Petrificate.PETRIFICATION_DEVICE, owner, world);
    }

    public PetrificationDeviceEntity(World world, double x, double y, double z) {
        super(Petrificate.PETRIFICATION_DEVICE, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PETRIFICATION_DEVICE_ACTIVATING;
    }


    private ItemStack originalStack = ItemStack.EMPTY;

    public void setOriginalStack(ItemStack stack) {
        this.originalStack = stack.copy(); // teljes másolat NBT-vel
    }

    public ItemStack getOriginalStack() {
        return originalStack;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            // Saját collision check
            HitResult hitResult = ProjectileUtil.getCollision(this,
                    this::canHit, // default szűrés
                    0.0 // expand érték, 0.0 = nincs extra buffer
            );

            if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            }

            if (this.age % 60 == 0) {
                this.getWorld().playSound(
                        null,                  // ha null, mindenki hallja
                        this.getX(), this.getY(), this.getZ(),    // hely, ahonnan szól
                        ModSounds.flight_sound, // ide jön a saját hangod
                        SoundCategory.AMBIENT, // vagy AMBIENT / HOSTILE, ahova szeretnéd
                        1f,                  // hangerő
                        1.0f                   // pitch
                );
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (!this.getWorld().isClient) {
            PetrificationWaveEntity wave = new PetrificationWaveEntity(
                    this.getWorld(),
                    this.getX(), this.getY(), this.getZ(),
                    getOwner() instanceof ServerPlayerEntity ? (ServerPlayerEntity) getOwner() : null
            );
            wave.setOriginalStack(this.getOriginalStack());
            wave.setRadius(0.1f); // Start very small
            this.getWorld().spawnEntity(wave);
        }

        this.discard();
    }

}
