package com.sylvia.createbuttercat.datagen.recipe;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import com.sylvia.createbuttercat.register.ModFluids;
import com.sylvia.createbuttercat.register.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.FlowingFluid;

public class ModCompactingRecipeGen extends CompactingRecipeGen {
    public ModCompactingRecipeGen(PackOutput output, String defaultNamespace) {
        super(output, defaultNamespace);
    }
    GeneratedRecipe BUTTER = create(ModItems.BUTTER.getId(), b -> b
            .require((FlowingFluid) ModFluids.CREAM.getSource().getSource(),250)
            .output(ModItems.BUTTER,1)
            //.output(Fluids.WATER,250)
    );
}
