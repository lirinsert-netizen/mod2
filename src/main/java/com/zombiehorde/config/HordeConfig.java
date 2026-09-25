package com.zombiehorde.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class HordeConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue WAVE_INTERVAL_SECONDS;
    public static final ModConfigSpec.IntValue MIN_HORDE_SIZE;
    public static final ModConfigSpec.IntValue MAX_HORDE_SIZE;
    public static final ModConfigSpec.IntValue MIN_SPAWN_RADIUS;
    public static final ModConfigSpec.IntValue MAX_SPAWN_RADIUS;
    public static final ModConfigSpec.IntValue REWARD_BAG_CHANCE_PERCENT;
    public static final ModConfigSpec.BooleanValue SPAWN_FOR_CREATIVE_PLAYERS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("waves");
        WAVE_INTERVAL_SECONDS = builder
                .comment("Seconds between horde waves for each active player.")
                .defineInRange("waveIntervalSeconds", 60, 10, 3600);
        MIN_HORDE_SIZE = builder
                .comment("Minimum number of zombies in a normal wave.")
                .defineInRange("minHordeSize", 6, 1, 64);
        MAX_HORDE_SIZE = builder
                .comment("Maximum number of zombies in a normal wave. Every fifth wave gets +2 zombies.")
                .defineInRange("maxHordeSize", 10, 1, 96);
        MIN_SPAWN_RADIUS = builder
                .comment("Minimum horizontal spawn radius around the player.")
                .defineInRange("minSpawnRadius", 10, 4, 64);
        MAX_SPAWN_RADIUS = builder
                .comment("Maximum horizontal spawn radius around the player.")
                .defineInRange("maxSpawnRadius", 18, 5, 96);
        SPAWN_FOR_CREATIVE_PLAYERS = builder
                .comment("If false, creative players do not receive horde waves.")
                .define("spawnForCreativePlayers", false);
        builder.pop();

        builder.push("rewards");
        REWARD_BAG_CHANCE_PERCENT = builder
                .comment("Chance that a horde zombie killed by a player grants a reward bag.")
                .defineInRange("rewardBagChancePercent", 100, 0, 100);
        builder.pop();

        SPEC = builder.build();
    }

    private HordeConfig() {
    }
}
