package com.sylvia.createbuttercat;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

public class ModUtil {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final void debug(String message) {
        LOGGER.info("[Create Butter Cat] --------------------------------------------------" );
        LOGGER.info("                        " +message );
    }
    public static final void debugIsClientSide(Level level) {
        debug(level.isClientSide()?"Client Side" : "Server Side" );
    }

}
