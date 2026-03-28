package com.sylvia.createbuttercat.datagen.recipe;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.register.ModFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.common.ForgeMod;

public class ModMixingRecipeGen extends MixingRecipeGen {

    public ModMixingRecipeGen(PackOutput output,  String defaultNamespace) {
        super(output, defaultNamespace);
    }

    GeneratedRecipe CREAM = create(CreateButterCat.rl("cream"),b->b
            .require((FlowingFluid) ForgeMod.MILK.get(),250)
            .output(ModFluids.CREAM.getSource().getSource(),250)
    );

}
