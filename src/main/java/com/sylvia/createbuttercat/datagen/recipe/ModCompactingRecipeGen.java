package com.sylvia.createbuttercat.datagen.recipe;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import com.sylvia.createbuttercat.register.ModFluids;
import com.sylvia.createbuttercat.register.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.concurrent.CompletableFuture;

public class ModCompactingRecipeGen extends CompactingRecipeGen {
    public ModCompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }
    GeneratedRecipe BUTTER = create(ModItems.BUTTER.getId(), b -> b
            .require((FlowingFluid) ModFluids.CREAM.getSource().getSource(),250)
            .output(ModItems.BUTTER,1)
            //.output(Fluids.WATER,250)
    );
}
