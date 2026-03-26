package com.sylvia.createbuttercat.register;

import com.sylvia.createbuttercat.block.ButterCatEngineBlockEntity;
import com.sylvia.createbuttercat.block.ButterCatEngineRenderer;
import com.sylvia.createbuttercat.block.ButterCatEngineVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.sylvia.createbuttercat.CreateButterCat.REGISTRATE;

public class ModBlockEnetities {

    public static final BlockEntityEntry<ButterCatEngineBlockEntity> BUTTER_CAT_ENGINE_BE= REGISTRATE
            .blockEntity("butter_cat_engine_be", ButterCatEngineBlockEntity::new)
            .visual(()-> ButterCatEngineVisual::new,false )
            .validBlocks(ModBlocks.BUTTER_CAT_ENGINE)
            .renderer(() -> ButterCatEngineRenderer::new)
            .register();

    public static void register() {}

}
