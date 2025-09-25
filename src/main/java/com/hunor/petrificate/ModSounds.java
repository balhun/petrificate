package com.hunor.petrificate;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent petrification_device_sound = registerSoundEvent("petrification_device_sound");
    public static final SoundEvent flight_sound = registerSoundEvent("flight_sound");
    public static final SoundEvent petrificating = registerSoundEvent("petrificating");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Petrificate.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {}
}
