package com.zombiehorde.client.renderer;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.client.model.EmberZombieModel;
import com.zombiehorde.client.model.ModModelLayers;
import com.zombiehorde.client.renderer.layer.GlowingEyesLayer;
import com.zombiehorde.entity.EmberZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class EmberZombieRenderer extends HumanoidMobRenderer<EmberZombie, EmberZombieModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ZombieHordeMod.MOD_ID, "textures/entity/ember_zombie.png");
    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(ZombieHordeMod.MOD_ID, "textures/entity/ember_zombie_eyes.png");

    public EmberZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new EmberZombieModel(context.bakeLayer(ModModelLayers.EMBER_ZOMBIE)), 0.55F);
        this.addLayer(new GlowingEyesLayer<>(this, EYES));
    }

    @Override
    public ResourceLocation getTextureLocation(EmberZombie entity) {
        return TEXTURE;
    }
}
