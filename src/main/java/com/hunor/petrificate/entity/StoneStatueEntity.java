package com.hunor.petrificate.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StoneStatueEntity extends MobEntity {

    public float headyaw, headpitch;

    public StoneStatueEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
    }


    public static DefaultAttributeContainer.Builder createStoneStatueAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0.0) // Add this!
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_ARMOR, 100.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

    // Prevent falling
    @Override
    public boolean hasNoGravity() {
        return false; // Or false if you want it to be affected by gravity
    }

    // Optional: Make it immune to status effects
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    // Optional: Prevent being pushed by fluids
    @Override
    public boolean isPushedByFluids() {
        return true;
    }

    // Optional: Make it not despawn
    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    // Make completely invincible to all damage
    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource == this.getDamageSources().outOfWorld() ||
                damageSource == this.getDamageSources().genericKill() ||
                damageSource.getName().equals("kill")) {
            return false; // Not invulnerable to /kill
        }
        return true; // Invulnerable to everything else
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        // Use default push behavior but reduce the force for heavy feel
        if (!this.isConnectedThroughVehicle(entity)) {
            super.pushAwayFrom(entity);
            // Reduce the push effect to maintain heavy feel
            this.setVelocity(this.getVelocity().multiply(0.3));
        }
    }



    @Override
    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement()) {
            // Apply friction to simulate weight and resistance
            Vec3d currentVelocity = this.getVelocity();

            // Heavy objects slow down faster (more friction)
            double friction = 0.98; // Higher friction = feels heavier
            double horizontalFriction = this.isOnGround() ? friction * 0.91 : 0.91;

            Vec3d newVelocity = new Vec3d(
                    currentVelocity.x * horizontalFriction,
                    currentVelocity.y * 0.98, // Vertical friction
                    currentVelocity.z * horizontalFriction
            );

            // Prevent very slow movement (simulates static friction)
            if (Math.abs(newVelocity.x) < 0.003) newVelocity = new Vec3d(0, newVelocity.y, newVelocity.z);
            if (Math.abs(newVelocity.z) < 0.003) newVelocity = new Vec3d(newVelocity.x, newVelocity.y, 0);

            this.setVelocity(newVelocity);
            this.updateLimbs(false);
        }

        super.travel(movementInput);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public boolean startRiding(Entity entity) {
        return false;
    }

    public void unpetrify() {
        this.discard();

        // Play sound effect
        this.getWorld().playSound(null, this.getBlockPos(),
                SoundEvents.BLOCK_STONE_BREAK, SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }
}
