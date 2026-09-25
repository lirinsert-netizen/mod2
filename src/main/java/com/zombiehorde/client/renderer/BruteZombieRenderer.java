package com.zombiehorde.client.renderer;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.client.model.BruteZombieModel;
import com.zombiehorde.client.model.ModModelLayers;
import com.zombiehorde.client.renderer.layer.GlowingEyesLayer;
import com.zombiehorde.entity.BruteZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class BruteZombieRenderer extends HumanoidMobRenderer<BruteZombie, BruteZombieModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ZombieHordeMod.MOD_ID, "textures/entity/brute_zombie.png");
    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(ZombieHordeMod.MOD_ID, "textures/entity/brute_zombie_eyes.png");

    public BruteZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new BruteZombieModel(context.bakeLayer(ModModelLayers.BRUTE_ZOMBIE)), 0.72F);
        this.addLayer(new GlowingEyesLayer<>(this, EYES));
    }

    @Override
    public ResourceLocation getTextureLocation(BruteZombie entity) {
        return TEXTURE;
    }
}
