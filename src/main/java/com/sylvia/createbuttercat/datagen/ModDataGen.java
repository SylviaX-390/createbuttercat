package com.sylvia.createbuttercat.datagen;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.datagen.other.ModLang;
import com.sylvia.createbuttercat.datagen.other.ModLangZHCN;
import com.sylvia.createbuttercat.datagen.recipe.*;
import com.sylvia.createbuttercat.register.ModItems;
import com.sylvia.createbuttercat.register.ModPotions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import java.util.concurrent.CompletableFuture;



@EventBusSubscriber(modid = CreateButterCat.MODID)
public class ModDataGen {
    @SubscribeEvent
    public static void create(GatherDataEvent event) {
        String MODID = CreateButterCat.MODID;
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new ModLang(output, MODID, "en_us"));
        generator.addProvider(event.includeServer(), new ModLangZHCN(output, MODID, "zh_cn"));

        generator.addProvider(event.includeServer(), new ModCompactingRecipeGen(output, lookupProvider, MODID));
        generator.addProvider(event.includeServer(), new ModMixingRecipeGen(output, lookupProvider,MODID));
        generator.addProvider(event.includeServer(), new ModSequencedAssemblyRecipeGen(output, lookupProvider,MODID));
        generator.addProvider(event.includeServer(), new ModFillingRecipeGen(output, lookupProvider,MODID));
        generator.addProvider(event.includeServer(), new ModRecipeGen(output, lookupProvider));

    }
    @SubscribeEvent
    public static void onBrewingRecipeReg(RegisterBrewingRecipesEvent event){
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, ModItems.INCOMPLETE_SUPER_BUTTER.get(),ModPotions.ROTATION);
        builder.addMix(ModPotions.ROTATION, ModItems.SUPER_BUTTER.get(),ModPotions.SUPER_ROTATION);
    }
}
