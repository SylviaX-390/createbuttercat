package com.sylvia.createbuttercat;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.sylvia.createbuttercat.register.ModPonder;
import com.sylvia.createbuttercat.register.*;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.RegisterEvent;


@Mod(CreateButterCat.MODID)
public class CreateButterCat {
    public static final String MODID = "createbuttercat";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);
    static {
        REGISTRATE.defaultCreativeTab((ResourceKey<CreativeModeTab>)null);
        REGISTRATE.setTooltipModifierFactory(item -> new  ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public CreateButterCat(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::onRegister);

        REGISTRATE.registerEventListeners(modEventBus);
        ModFluids.register();
        ModBlocks.register();
        ModBlockEnetities.register();
        ModItems.register();
        ModCreativeModeTabs.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON,ModConfigs.COMMON_SPEC);
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new ModPonder());
    }
    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    private void onRegister(final RegisterEvent event) {
        ModArmInteractions.register();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModPartialModels.init();
        }
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
    public static boolean isLoaded(String mod_id ) {return ModList.get().isLoaded(mod_id);}
}
