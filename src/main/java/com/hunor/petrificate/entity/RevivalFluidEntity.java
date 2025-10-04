package com.hunor.petrificate.entity;

import com.hunor.petrificate.Petrificate;
import com.hunor.petrificate.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class RevivalFluidEntity extends ThrownItemEntity {

    public RevivalFluidEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public RevivalFluidEntity(World world, LivingEntity owner) {
        super(Petrificate.REVIVAL_FLUID, owner, world);
    }

    public RevivalFluidEntity(World world, double x, double y, double z) {
        super(Petrificate.REVIVAL_FLUID, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.REVIVAL_FLUID;
    }

    @Override
    protected double getGravity() {
        return 0.05;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.getWorld().isClient) {
            this.applySplashEffect();

            // Ez mindig ugyanazt a potion effektet produkálja
            this.getWorld().syncWorldEvent(WorldEvents.SPLASH_POTION_SPLASHED, this.getBlockPos(), 0xFFFFFF);

            this.discard();
        }
    }

    private void applySplashEffect() {
        // Define the splash radius (same as splash potions)
        double radius = 4.0;
        Box box = new Box(this.getBlockPos()).expand(radius);

        // Find all StoneStatue entities in radius
        for (StoneStatueEntity statue : this.getWorld().getNonSpectatingEntities(StoneStatueEntity.class, box)) {
            double distanceSquared = this.squaredDistanceTo(statue);

            if (distanceSquared < radius * radius) {
                // Unpetrify the statue - you'll need to implement this method
                statue.unpetrify();
            }
        }
    }
}
