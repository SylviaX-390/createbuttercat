package com.sylvia.createbuttercat.datagen.recipe;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.datagen.other.ModTags;
import com.sylvia.createbuttercat.register.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeGen extends RecipeProvider
{
    public ModRecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HONEY_BUTTER.get())
                .requires(ModTags.getButters())
                .requires(Items.HONEY_BOTTLE)
                .unlockedBy("has_honey_bottle", has(Items.HONEY_BOTTLE))
                .save(consumer, CreateButterCat.rl("crafting/honey_butter"));
    }
}
