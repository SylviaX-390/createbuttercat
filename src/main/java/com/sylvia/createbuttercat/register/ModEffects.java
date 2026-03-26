package com.sylvia.createbuttercat.register;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.mob_effect.ButterRotationEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, CreateButterCat.MODID);

    public static final DeferredHolder<MobEffect, ButterRotationEffect> BUTTER_ROTATION_EFFECT =
            EFFECTS.register("rotation", ButterRotationEffect::new);

    public static void register(IEventBus modBus) {
        EFFECTS.register(modBus);
    }
}
