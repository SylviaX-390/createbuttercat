package com.sylvia.createbuttercat.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.register.ModBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonder implements PonderPlugin {
    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?,?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.addToTag(AllCreatePonderTags.KINETIC_SOURCES)
                .add(ModBlocks.BUTTER_CAT_ENGINE);

    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?,?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.addStoryBoard(ModBlocks.BUTTER_CAT_ENGINE, "butter_cat_engine", ModPonderScenes::butterCatEngine, AllCreatePonderTags.KINETIC_SOURCES);
    }

    @Override
    public String getModId() {
        return CreateButterCat.MODID;
    }
}
