package com.hunor.petrificate.item;

import com.hunor.petrificate.Petrificate;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class ModItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Petrificate.MOD_ID, name));
        Item item = itemFactory.apply(settings);
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static final Item PETRIFICATION_DEVICE = new PetrificationDevice(new Item.Settings().maxCount(1));
    public static final Item PETRIFICATION_DEVICE_BROKEN = new Item(new Item.Settings().maxCount(1));
    public static final Item PETRIFICATION_DEVICE_DRAINED = new Item(new Item.Settings().maxCount(1));
    public static final Item PETRIFICATION_DEVICE_ACTIVATING = new Item(new Item.Settings().maxCount(1));


    public static final RegistryKey<ItemGroup> PETRIFICATE_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Petrificate.MOD_ID, "item_group"));
    public static final ItemGroup PETRIFICATE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.PETRIFICATION_DEVICE))
            .displayName(Text.translatable("itemGroup.petrificate"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM, Identifier.of(Petrificate.MOD_ID, "petrification_device"), PETRIFICATION_DEVICE);
        Registry.register(Registries.ITEM, Identifier.of(Petrificate.MOD_ID, "petrification_device_broken"), PETRIFICATION_DEVICE_BROKEN);
        Registry.register(Registries.ITEM, Identifier.of(Petrificate.MOD_ID, "petrification_device_drained"), PETRIFICATION_DEVICE_DRAINED);
        Registry.register(Registries.ITEM, Identifier.of(Petrificate.MOD_ID, "petrification_device_activating"), PETRIFICATION_DEVICE_ACTIVATING);


        Registry.register(Registries.ITEM_GROUP, PETRIFICATE_GROUP_KEY, PETRIFICATE_GROUP);
        ItemGroupEvents.modifyEntriesEvent(PETRIFICATE_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.PETRIFICATION_DEVICE);
            itemGroup.add(ModItems.PETRIFICATION_DEVICE_BROKEN);
            itemGroup.add(ModItems.PETRIFICATION_DEVICE_DRAINED);
        });
    }
}
