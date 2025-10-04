package com.hunor.petrificate.item;

import com.hunor.petrificate.entity.RevivalFluidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class RevivalFluidItem extends Item {

    public RevivalFluidItem(Settings settings) {
        super(settings.maxCount(16));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            RevivalFluidEntity revivalfluidentity = new RevivalFluidEntity(world, user);
            revivalfluidentity.setItem(itemStack.copy());
            revivalfluidentity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0f, 0.5f, 1.0f);
            world.spawnEntity(revivalfluidentity);

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_SPLASH_POTION_THROW,
                    SoundCategory.PLAYERS,
                    0.5F,
                    0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
            );
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);


        world.emitGameEvent(user, GameEvent.ENTITY_PLACE, user.getPos());
        return TypedActionResult.success(itemStack, world.isClient);
    }


}
