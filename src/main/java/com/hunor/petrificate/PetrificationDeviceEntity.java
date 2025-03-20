package com.hunor.petrificate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
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
        return ModItems.PETRIFICATION_DEVICE;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (!this.getWorld().isClient) {
            Entity owner = this.getOwner();
            // Drop the item at the collision point
            if (owner instanceof ServerPlayerEntity player) {
                deviceOwner = player;
            }


            // Start the expanding effect
            this.getWorld().spawnEntity(new PetrificationWaveEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), deviceOwner));

            // Remove the projectile
            this.discard();
        }

        super.onCollision(hitResult);
    }
}
