package com.sylvia.createbuttercat.datagen.other;

import com.sylvia.createbuttercat.CreateButterCat;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLangZHCN extends LanguageProvider {

    public ModLangZHCN(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + CreateButterCat.MODID, "机械动力：黄油猫");
        add("block.createbuttercat.butter_cat_engine" , "黄油猫引擎");
        add("block.createbuttercat.cream" , "奶油");
        add("fluid.createbuttercat.cream" , "奶油");
        add("item.createbuttercat.cream_bucket" , "奶油桶");
        add("item.createbuttercat.butter" , "黄油");
        add("item.createbuttercat.honey_butter" , "蜂蜜黄油");
        add("item.createbuttercat.super_butter" , "超级黄油");
        add("item.createbuttercat.incomplete_super_butter" , "未完成的超级黄油");

        add("item.minecraft.potion.effect.rotation_potion" , "旋转药水");
        add("item.minecraft.potion.effect.super_rotation_potion" , "超级旋转药水");
        add("item.minecraft.splash_potion.effect.rotation_potion" , "喷溅型旋转药水");
        add("item.minecraft.splash_potion.effect.super_rotation_potion" , "喷溅型超级旋转药水");
        add("item.minecraft.lingering_potion.effect.rotation_potion" , "滞留型旋转药水");
        add("item.minecraft.lingering_potion.effect.super_rotation_potion" , "滞留型超级旋转药水");

        add("effect.createbuttercat.butter_rotation" , "旋转");

        add("string.createbuttercat.no_bread" , "§c面包呢？");
        add("string.createbuttercat.no_butter" , "§c黄油呢？");
        add("string.createbuttercat.infinite" , "§d无限供应");
        add("string.createbuttercat.full" , "§c黄油已经贴满了！");

        add("string.createbuttercat.remaining" , "剩余");

        add("createbuttercat.ponder.butter_cat_engine.header" , "让黄油猫转起来！");
        add("createbuttercat.ponder.butter_cat_engine.text_1" , "用栓绳把猫绑到传动杆上。");
        add("createbuttercat.ponder.butter_cat_engine.text_2" , "这就是黄油猫引擎，现在来放块面包和黄油在猫身上。");
        add("createbuttercat.ponder.butter_cat_engine.text_3" , "黄油越多，速度越快，应力越大");
        add("createbuttercat.ponder.butter_cat_engine.text_4" , "试试超级黄油？那就是永动机了");
        add("createbuttercat.ponder.butter_cat_engine.text_5" , "别担心，猫咪一直都在，只是他不认主了。。。");
    }
}
