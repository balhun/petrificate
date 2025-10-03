package com.hunor.petrificate.item;

import com.hunor.petrificate.sound.ModSounds;
import com.hunor.petrificate.entity.PetrificationDeviceEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PetrificationDevice extends Item {


    public PetrificationDevice(Settings settings) {
        super(settings.maxDamage(50));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (itemStack.getDamage() >= itemStack.getMaxDamage()) return TypedActionResult.fail(itemStack);

        if (!world.isClient) {
            world.playSound(
                    null,                            // null = minden játékos hallja
                    user.getX(), user.getY(), user.getZ(), // pontos pozíció
                    ModSounds.flight_sound,   // a hang
                    SoundCategory.HOSTILE,           // a játékosok hallják
                    1F,                            // hangerő, minimum 1.0
                    1.0F                             // pitch
            );
        }

        user.getItemCooldownManager().set(this, 20); // 300/20 = 15s

        if (!world.isClient) {
            PetrificationDeviceEntity petrificationDeviceEntity = new PetrificationDeviceEntity(world, user);
            petrificationDeviceEntity.setOriginalStack(itemStack.copy());
            petrificationDeviceEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(petrificationDeviceEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

}
