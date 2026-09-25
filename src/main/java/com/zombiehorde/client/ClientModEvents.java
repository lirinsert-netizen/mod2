package com.zombiehorde.client;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.client.model.BruteZombieModel;
import com.zombiehorde.client.model.EmberZombieModel;
import com.zombiehorde.client.model.ModModelLayers;
import com.zombiehorde.client.model.PlagueZombieModel;
import com.zombiehorde.client.model.RunnerZombieModel;
import com.zombiehorde.client.renderer.BruteZombieRenderer;
import com.zombiehorde.client.renderer.EmberZombieRenderer;
import com.zombiehorde.client.renderer.PlagueZombieRenderer;
import com.zombiehorde.client.renderer.RunnerZombieRenderer;
import com.zombiehorde.registry.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ZombieHordeMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RUNNER_ZOMBIE, RunnerZombieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BRUTE_ZOMBIE, BruteZombieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.PLAGUE_ZOMBIE, PlagueZombieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.EMBER_ZOMBIE, EmberZombieModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.RUNNER_ZOMBIE.get(), RunnerZombieRenderer::new);
        event.registerEntityRenderer(ModEntities.BRUTE_ZOMBIE.get(), BruteZombieRenderer::new);
        event.registerEntityRenderer(ModEntities.PLAGUE_ZOMBIE.get(), PlagueZombieRenderer::new);
        event.registerEntityRenderer(ModEntities.EMBER_ZOMBIE.get(), EmberZombieRenderer::new);
    }

    private ClientModEvents() {
    }
}
