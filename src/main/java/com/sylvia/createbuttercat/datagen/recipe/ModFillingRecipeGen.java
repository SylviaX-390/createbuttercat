package com.sylvia.createbuttercat.datagen.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;
import com.sylvia.createbuttercat.datagen.other.ModTags;
import com.sylvia.createbuttercat.register.ModItems;
import net.minecraft.data.PackOutput;

public class ModFillingRecipeGen extends FillingRecipeGen {
    public ModFillingRecipeGen(PackOutput output, String defaultNamespace) {
        super(output , defaultNamespace);
    }
    GeneratedRecipe HONEY_BUTTER = create("honey_butter",b->b
            .require(ModTags.getButters())
            .require(AllFluids.HONEY.getSource(),50)
            .output(ModItems.HONEY_BUTTER,1));
}
