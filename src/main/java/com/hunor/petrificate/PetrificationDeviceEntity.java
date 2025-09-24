package com.hunor.petrificate;

import com.hunor.petrificate.ModItems;
import com.hunor.petrificate.PetrificationWaveEntity;
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
import net.minecraft.util.math.Vec3d;
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
            PetrificationWaveEntity wave = new PetrificationWaveEntity(
                    this.getWorld(),
                    this.getX(), this.getY(), this.getZ(),
                    getOwner() instanceof ServerPlayerEntity ? (ServerPlayerEntity) getOwner() : null
            );
            wave.setRadius(0.1f); // Start very small
            this.getWorld().spawnEntity(wave);
        }
        this.discard();
    }
}
