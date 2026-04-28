package com.sylvia.createbuttercat.datagen.recipe;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.datagen.other.ModTags;
import com.sylvia.createbuttercat.register.ModBlocks;
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
        crafting(recipeOutput, "butter_block", ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BUTTER_BLOCK)
                .define('X', ModItems.BUTTER)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_butter", has(ModItems.BUTTER))
        );
        crafting(recipeOutput, "honey_butter_block", ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HONEY_BUTTER_BLOCK)
                .define('X',  ModItems.HONEY_BUTTER)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_honey_butter", has(ModItems.HONEY_BUTTER))
        );
        crafting(recipeOutput, "in_super_butter_block", ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.IN_SUPER_BUTTER_BLOCK)
                .define('X',  ModItems.INCOMPLETE_SUPER_BUTTER)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_incomplete_super_butter", has(ModItems.INCOMPLETE_SUPER_BUTTER))
        );
        crafting(recipeOutput, "super_butter_block", ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUPER_BUTTER_BLOCK)
                .define('X', ModItems.SUPER_BUTTER)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_super_butter", has(ModItems.SUPER_BUTTER))
        );
    }
    private void crafting(RecipeOutput recipeOutput, String name, RecipeBuilder recipeBuilder ) {
        recipeBuilder.save(recipeOutput, CreateButterCat.rl( "crafting/"+name));
    }
}
