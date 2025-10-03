package com.hunor.petrificate.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.Entity;

public class PetrificationEffect extends StatusEffect {
    public PetrificationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x06d718);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
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

        } else {
            entity.damage(entity.getDamageSources().magic(), Float.MAX_VALUE);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }
}