package com.sylvia.createbuttercat.register;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class  ModConfigs {

    public static class Common {
        public final ModConfigSpec.IntValue maxButterCount;
        public final ModConfigSpec.IntValue maxInfiniteCapacity;
        public final static int MAX_VALUE = 1024;

        Common(ModConfigSpec.Builder builder) {
            builder.comment("ButterCat Mod Configuration")
                    .push("general");

            maxButterCount = builder
                    .comment(String.format("Max butter count [1-%d]",MAX_VALUE))
                    .comment("Default:64")
                    .defineInRange("maxButterCount", 64, 1, MAX_VALUE);

            maxInfiniteCapacity = builder
                    .comment(String.format("Max infinite capacity [2-%d] when 4 cats", MAX_VALUE))
                    .comment("Default:64")
                    .defineInRange("maxInfiniteCapacity", 64, 2, MAX_VALUE);

            builder.pop();
        }
    }

    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    static {
        final Pair<Common, ModConfigSpec> commonSpecPair =
                new ModConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }
}