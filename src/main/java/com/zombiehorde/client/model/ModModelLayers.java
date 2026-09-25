package com.zombiehorde.client.model;

import com.zombiehorde.ZombieHordeMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public final class ModModelLayers {
    public static final ModelLayerLocation RUNNER_ZOMBIE = create("runner_zombie");
    public static final ModelLayerLocation BRUTE_ZOMBIE = create("brute_zombie");
    public static final ModelLayerLocation PLAGUE_ZOMBIE = create("plague_zombie");
    public static final ModelLayerLocation EMBER_ZOMBIE = create("ember_zombie");

    private static ModelLayerLocation create(String path) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ZombieHordeMod.MOD_ID, path), "main");
    }

    private ModModelLayers() {
    }
}
