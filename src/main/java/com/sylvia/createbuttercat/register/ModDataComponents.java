package com.sylvia.createbuttercat.register;

import com.mojang.serialization.Codec;
import com.sylvia.createbuttercat.CreateButterCat;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CreateButterCat.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> BUTTER_LEVEL =
            DATA_COMPONENTS.register("butter_level",   () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());
    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }

}
