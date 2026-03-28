package com.sylvia.createbuttercat.datagen.recipe;

import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.sylvia.createbuttercat.register.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.stream.Stream;

public class ModSequencedAssemblyRecipeGen extends SequencedAssemblyRecipeGen {
    public ModSequencedAssemblyRecipeGen(PackOutput output,  String defaultNamespace) {
        super(output, defaultNamespace);
    }
    GeneratedRecipe SUPER_BUTTER = create("super_butter", b -> b
            .require(ModItems.HONEY_BUTTER)
            .transitionTo(ModItems.INCOMPLETE_SUPER_BUTTER.get())
            .addOutput(ModItems.SUPER_BUTTER.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new,
                    rb -> rb.require(Ingredient.fromValues(
                            Stream.of(
                                    new Ingredient.ItemValue(Items.CHORUS_FRUIT.getDefaultInstance()),
                                    new Ingredient.ItemValue(Items.POPPED_CHORUS_FRUIT.getDefaultInstance()))
                    )))
            .addStep(DeployerApplicationRecipe::new,
                    rb -> rb.require(Ingredient.fromValues(
                            Stream.of(new Ingredient.ItemValue(new ItemStack(Items.NETHER_STAR)))
                    )))
            .addStep(PressingRecipe::new, rb -> rb));

}