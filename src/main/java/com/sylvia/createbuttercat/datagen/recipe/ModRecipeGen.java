package com.sylvia.createbuttercat.datagen.recipe;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.datagen.other.ModTags;
import com.sylvia.createbuttercat.register.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeGen extends RecipeProvider
{
    public ModRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }
    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider holderLookup) {
        //crafting
        crafting(recipeOutput, "honey_butter", ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HONEY_BUTTER)
                .requires(ModTags.getButters())
                .requires(Items.HONEY_BOTTLE)
                .unlockedBy("has_honey_bottle", has(Items.HONEY_BOTTLE))
        );
    }
    private void crafting(RecipeOutput recipeOutput, String name, RecipeBuilder recipeBuilder ) {
        recipeBuilder.save(recipeOutput, CreateButterCat.rl( "crafting/"+name));
    }
}
