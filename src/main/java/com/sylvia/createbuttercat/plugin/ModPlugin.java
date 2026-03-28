package com.sylvia.createbuttercat.plugin;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.block.ButterCatEngineBlock;
import com.sylvia.createbuttercat.block.ButterCatEngineBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModPlugin implements IWailaPlugin {
    public static final String ID = CreateButterCat.MODID;
    public static final ResourceLocation BUTTER_CAT_ENGINE = ResourceLocation.fromNamespaceAndPath(ID, "butter_cat_engine");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(BCEComponentProvider.INSTANCE, ButterCatEngineBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(BCEComponentProvider.INSTANCE, ButterCatEngineBlock.class);
    }
}
