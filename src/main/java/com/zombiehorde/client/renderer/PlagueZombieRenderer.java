package com.zombiehorde.client.renderer;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.client.model.ModModelLayers;
import com.zombiehorde.client.model.PlagueZombieModel;
import com.zombiehorde.entity.PlagueZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class PlagueZombieRenderer extends HumanoidMobRenderer<PlagueZombie, PlagueZombieModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ZombieHordeMod.MOD_ID, "textures/entity/plague_zombie.png");

    public PlagueZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new PlagueZombieModel(context.bakeLayer(ModModelLayers.PLAGUE_ZOMBIE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(PlagueZombie entity) {
        return TEXTURE;
    }
}
