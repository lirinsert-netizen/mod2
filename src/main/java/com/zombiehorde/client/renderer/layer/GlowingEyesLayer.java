package com.zombiehorde.client.renderer.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public final class GlowingEyesLayer<T extends LivingEntity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private final RenderType renderType;

    public GlowingEyesLayer(RenderLayerParent<T, M> renderer, ResourceLocation texture) {
        super(renderer);
        this.renderType = RenderType.eyes(texture);
    }

    @Override
    public RenderType renderType() {
        return renderType;
    }
}
