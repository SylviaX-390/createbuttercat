package com.sylvia.createbuttercat.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.block.ButterCatEngineBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static com.sylvia.createbuttercat.CreateButterCat.REGISTRATE;

public class ModBlocks {
    static {
        CreateButterCat.REGISTRATE.setCreativeTab(ModCreativeModeTabs.CBC_TAB);
    }
    public static final BlockEntry<ButterCatEngineBlock> BUTTER_CAT_ENGINE = REGISTRATE
            .block("butter_cat_engine", ButterCatEngineBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.WOOL).noOcclusion().mapColor(MapColor.TERRACOTTA_YELLOW))
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .loot((loot,block)->loot.dropOther(block, AllBlocks.SHAFT))
            .item()
            .model((c, p) -> p.blockItem(c, "/item"))
            .build()
            .register();

    public static void register() {}
}
