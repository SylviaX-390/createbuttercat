package com.sylvia.createbuttercat.register;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.datagen.other.ModLang;
import com.sylvia.createbuttercat.datagen.other.ModLangZHCN;
import com.sylvia.createbuttercat.datagen.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateButterCat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModDataGen {
    @SubscribeEvent
    public static void create(GatherDataEvent event) {
        String MODID = CreateButterCat.MODID;
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event.includeClient(), new ModLang(output, MODID, "en_us"));
        generator.addProvider(event.includeClient(), new ModLangZHCN(output, MODID, "zh_cn"));

        generator.addProvider(event.includeServer(), new ModCompactingRecipeGen(output, MODID));
        generator.addProvider(event.includeServer(), new ModMixingRecipeGen(output,MODID));
        generator.addProvider(event.includeServer(), new ModSequencedAssemblyRecipeGen(output,MODID));
        generator.addProvider(event.includeServer(), new ModFillingRecipeGen(output, MODID));
        generator.addProvider(event.includeServer(), new ModRecipeGen(output));

    }
}
