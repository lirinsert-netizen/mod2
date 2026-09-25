package com.zombiehorde;

import com.zombiehorde.config.HordeConfig;
import com.zombiehorde.registry.ModEntities;
import com.zombiehorde.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ZombieHordeMod.MOD_ID)
public final class ZombieHordeMod {
    public static final String MOD_ID = "zombiehorde";

    public ZombieHordeMod(IEventBus modBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modBus);
        ModEntities.ENTITY_TYPES.register(modBus);
        modBus.addListener(ModEntities::registerAttributes);
        modContainer.registerConfig(ModConfig.Type.COMMON, HordeConfig.SPEC);
    }
}
