package com.zombiehorde.client.renderer;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.client.model.ModModelLayers;
import com.zombiehorde.client.model.RunnerZombieModel;
import com.zombiehorde.entity.RunnerZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class RunnerZombieRenderer extends HumanoidMobRenderer<RunnerZombie, RunnerZombieModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ZombieHordeMod.MOD_ID, "textures/entity/runner_zombie.png");

    public RunnerZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new RunnerZombieModel(context.bakeLayer(ModModelLayers.RUNNER_ZOMBIE)), 0.42F);
    }

    @Override
    public ResourceLocation getTextureLocation(RunnerZombie entity) {
        return TEXTURE;
    }
}
