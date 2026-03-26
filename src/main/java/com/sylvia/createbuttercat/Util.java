package com.sylvia.createbuttercat;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

public class Util {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final void debug(String message) {
        LOGGER.info("[Create Butter Cat] --------------------------------------------------" );
        LOGGER.info("                        " +message );
    }
    public static final void debugIsClientSide(Level level) {
        debug(level.isClientSide()?"Client Side" : "Server Side" );
    }

}
