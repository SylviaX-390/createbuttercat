package com.sylvia.createbuttercat.register;

import com.sylvia.createbuttercat.CreateButterCat;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateButterCat.MODID);

    public static final Supplier<CreativeModeTab> CBC_TAB = TABS.register(CreateButterCat.MODID+"_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SUPER_BUTTER.get()))
                    .title(Component.translatable("itemGroup." + CreateButterCat.MODID ))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.BUTTER.get());
                        output.accept(ModItems.HONEY_BUTTER.get());
                        output.accept(ModItems.INCOMPLETE_SUPER_BUTTER.get());
                        output.accept(ModItems.SUPER_BUTTER.get());
                        output.accept(ModFluids.CREAM.getBucket().get());
                        output.accept(ModBlocks.BUTTER_CAT_ENGINE.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
