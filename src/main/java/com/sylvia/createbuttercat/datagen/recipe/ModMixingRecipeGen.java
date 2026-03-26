package com.sylvia.createbuttercat.datagen.recipe;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.register.ModFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.concurrent.CompletableFuture;

public class ModMixingRecipeGen extends MixingRecipeGen {

    public ModMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    GeneratedRecipe CREAM = create(CreateButterCat.rl("cream"),b->b
            .require((FlowingFluid) NeoForgeMod.MILK.get(),250)
            .output(ModFluids.CREAM.getSource().getSource(),250)
    );

}
