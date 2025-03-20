package com.hunor.petrificate;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StoneStatueEntity extends ArmorStandEntity {
    private final SimpleInventory inventory = new SimpleInventory(41); // Stores player items

    public StoneStatueEntity(EntityType<? extends ArmorStandEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true); // Can't be destroyed by explosions
        this.setCustomNameVisible(true); // Show name like a player
        this.setNoGravity(true);
    }


    // Store player's inventory inside the statue
    public void storeLoot(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                inventory.setStack(i, stack.copy());
                player.getInventory().removeStack(i); // Remove items from player
            }
        }
        this.setCustomName(player.getName()); // Set name to match the player
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient && (source.getAttacker() instanceof PlayerEntity)) {
            this.dropLoot(); // Drop stored items
            this.discard(); // Remove statue
        }
        return false; // Prevent normal damage
    }

    // Drops stored loot when the statue is broken
    private void dropLoot() {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                this.dropStack(stack);
            }
        }
    }
/*
    // Serializes the statue's inventory to NBT
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtList nbtList = new NbtList();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                NbtCompound itemTag = new NbtCompound();
                itemTag.putByte("Slot", (byte) i);
                // Store the item stack as a component
                NbtCompound componentData = new NbtCompound();
                stack.writeNbt(componentData);
                itemTag.put("minecraft:custom_data", componentData);
                nbtList.add(itemTag);
            }
        }
        nbt.put("Items", nbtList);
    }

    // Deserializes the statue's inventory from NBT
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        NbtList nbtList = nbt.getList("Items", 10);
        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound itemTag = nbtList.getCompound(i);
            int slot = itemTag.getByte("Slot") & 255;
            // Retrieve the item stack from the component
            NbtCompound componentData = itemTag.getCompound("minecraft:custom_data");
            ItemStack stack = ItemStack.fromNbt(componentData);
            inventory.setStack(slot, stack);
        }
    }*/
}
