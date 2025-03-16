package com.hunor.petrificate;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class PetrificationDeviceEntity extends ThrownItemEntity {

    public PetrificationDeviceEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public PetrificationDeviceEntity(World world, LivingEntity owner) {
        super(Petrificate.PETRIFICATION_WEAPON, owner, world);
    }

    public PetrificationDeviceEntity(World world, double x, double y, double z) {
        super(Petrificate.PETRIFICATION_WEAPON, x, y, z, world);
    }


    @Override
    protected Item getDefaultItem() {
        return ModItems.PETRIFICATION_DEVICE;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 4);
        getDefaultItem();
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        getDefaultItem();
    }
}
