package com.sylvia.createbuttercat.register;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.sylvia.createbuttercat.datagen.other.ModTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static com.sylvia.createbuttercat.CreateButterCat.REGISTRATE;

public class ModItems {
    public static final ItemEntry<Item> BUTTER = REGISTRATE
            .item("butter",Item::new)
            .tag(ModTags.BUTTER)
            .tag(ModTags.FOOD_BUTTER)
            .properties(p -> p.food(new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationModifier(0.5F)
                    .build())
                    .component(ModDataComponents.BUTTER_LEVEL, 1)
            )
            .register();
    public static final ItemEntry<Item> HONEY_BUTTER = REGISTRATE
            .item("honey_butter",Item::new)
            .tag(ModTags.BUTTER)
            .tag(ModTags.FOOD_BUTTER)
            .properties(p -> p.food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.5F)
                    .effect(()->new MobEffectInstance(ModEffects.BUTTER_ROTATION_EFFECT,60,0),1)
                    .build())
                    .component(ModDataComponents.BUTTER_LEVEL, 2)
            )
            .register();
    public static final ItemEntry<Item> SUPER_BUTTER = REGISTRATE
            .item("super_butter",Item::new)
            .properties(p -> p.food(new FoodProperties.Builder()
                    .nutrition(18)
                    .saturationModifier(0.5F)
                    .effect(()->new MobEffectInstance(ModEffects.BUTTER_ROTATION_EFFECT,90,1),1)
                            .effect(()->new MobEffectInstance(MobEffects.LEVITATION,90,0),1)
                    .build())
                    .component(ModDataComponents.BUTTER_LEVEL, 5)
            )
            .register();

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_SUPER_BUTTER =  REGISTRATE
            .item("incomplete_super_butter", SequencedAssemblyItem::new)
            .tag(ModTags.BUTTER)
            .tag(ModTags.FOOD_BUTTER)
            .properties(p -> p.food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.5F)
                    .effect(()->new MobEffectInstance(ModEffects.BUTTER_ROTATION_EFFECT,30,2),1)
                    .build())
                    .component(ModDataComponents.BUTTER_LEVEL, 3)
            )
            .register();

    public static void register() {}

}
