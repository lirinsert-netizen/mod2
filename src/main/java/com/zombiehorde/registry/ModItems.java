package com.zombiehorde.registry;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.item.RewardBagItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ZombieHordeMod.MOD_ID);

    public static final DeferredItem<RewardBagItem> REWARD_BAG = ITEMS.registerItem(
            "reward_bag",
            RewardBagItem::new,
            new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)
    );

    private ModItems() {
    }
}
