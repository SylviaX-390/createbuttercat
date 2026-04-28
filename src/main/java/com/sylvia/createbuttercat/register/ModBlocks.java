package com.sylvia.createbuttercat.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.sylvia.createbuttercat.block.ButterBlock;
import com.sylvia.createbuttercat.block.ButterCatEngineBlock;
import com.sylvia.createbuttercat.block.SuperButterBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.sylvia.createbuttercat.CreateButterCat.REGISTRATE;

public class ModBlocks {
    public static final BlockEntry<ButterCatEngineBlock> BUTTER_CAT_ENGINE = REGISTRATE
            .block("butter_cat_engine", ButterCatEngineBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.WOOL).noOcclusion().mapColor(MapColor.TERRACOTTA_YELLOW))
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .loot((loot,block)->loot.dropOther(block, AllBlocks.SHAFT))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<ButterBlock> BUTTER_BLOCK = REGISTRATE
            .block("butter_block", ButterBlock::new)
            .initialProperties(()-> Blocks.SLIME_BLOCK)
            .properties(BlockBehaviour.Properties::randomTicks)
            .simpleItem()
            .register();
    public static final BlockEntry<ButterBlock> HONEY_BUTTER_BLOCK = REGISTRATE
            .block("honey_butter_block", ButterBlock::new)
            .initialProperties(()-> Blocks.HONEY_BLOCK)
            .properties(BlockBehaviour.Properties::randomTicks)
            .simpleItem()
            .register();
    public static final BlockEntry<SuperButterBlock> IN_SUPER_BUTTER_BLOCK = REGISTRATE
            .block("in_super_butter_block", SuperButterBlock::new)
            .initialProperties(()-> Blocks.HONEY_BLOCK)
            .properties(BlockBehaviour.Properties::randomTicks)
            .simpleItem()
            .register();
    public static final BlockEntry<SuperButterBlock> SUPER_BUTTER_BLOCK = REGISTRATE
            .block("super_butter_block", SuperButterBlock::new)
            .initialProperties(()-> Blocks.HONEY_BLOCK)
            .properties(BlockBehaviour.Properties::randomTicks)
            .simpleItem()
            .register();
    public static void register() {}
}
