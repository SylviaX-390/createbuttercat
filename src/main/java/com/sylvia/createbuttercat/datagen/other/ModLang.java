package com.sylvia.createbuttercat.datagen.other;

import com.sylvia.createbuttercat.CreateButterCat;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLang extends LanguageProvider {

    public ModLang(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + CreateButterCat.MODID, "Create:Butter Cat");
        add("block.createbuttercat.butter_cat_engine" , "Butter Cat Engine");
        add("block.createbuttercat.cream" , "Cream");
        add("fluid.createbuttercat.cream" , "Cream");
        add("item.createbuttercat.cream_bucket" , "Cream Bucket");
        add("item.createbuttercat.butter" , "Butter");
        add("item.createbuttercat.honey_butter" , "Honey Butter");
        add("item.createbuttercat.super_butter" , "Super Butter");
        add("item.createbuttercat.incomplete_super_butter" , "Incomplete Super Butter");

        add("item.minecraft.potion.effect.rotation_potion" , "Rotation Potion");
        add("item.minecraft.potion.effect.super_rotation_potion" , "Super Rotation Potion");
        add("item.minecraft.splash_potion.effect.rotation_potion" , "Rotation Splash Potion");
        add("item.minecraft.splash_potion.effect.super_rotation_potion" , "Super Rotation Splash Potion");
        add("item.minecraft.lingering_potion.effect.rotation_potion" , "Rotation Lingering Potion");
        add("item.minecraft.lingering_potion.effect.super_rotation_potion" , "Super Rotation Lingering Potion");

        add("effect.createbuttercat.rotation" , "Rotation");

        add("string.createbuttercat.no_bread" , "Where is bread?");
        add("string.createbuttercat.no_butter" , "Where is butter?");
        add("string.createbuttercat.infinite" , "Infinite Supply");
        add("string.createbuttercat.full" , "Butter is full!");

        add("string.createbuttercat.remaining" , "Remaining");

        add("createbuttercat.ponder.butter_cat_engine.header" , "Let the Butter Cat spin around!");
        add("createbuttercat.ponder.butter_cat_engine.text_1" , "Secure the cat to the Shafts with a lead.");
        add("createbuttercat.ponder.butter_cat_engine.text_2" , "This is the Butter Cat engine, Let’s put a slice of bread and some butter on it.");
        add("createbuttercat.ponder.butter_cat_engine.text_3" , "The more butter you add, the greater its kinetic output.");
        add("createbuttercat.ponder.butter_cat_engine.text_4" , "And with super butter? Perpetual motion machine.");
        add("createbuttercat.ponder.butter_cat_engine.text_5" , "Don't worry, the cat is still here,it just doesn't recognize its owner anymore...");


    }
}
