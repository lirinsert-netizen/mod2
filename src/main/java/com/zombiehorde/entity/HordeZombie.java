package com.zombiehorde.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public abstract class HordeZombie extends Zombie {
    protected HordeZombie(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide() && tickCount % particleInterval() == 0) {
            double x = getX() + (getRandom().nextDouble() - 0.5D) * 0.7D;
            double y = getY() + 0.2D + getRandom().nextDouble() * getBbHeight();
            double z = getZ() + (getRandom().nextDouble() - 0.5D) * 0.7D;
            level().addParticle(ambientParticle(), x, y, z, 0.0D, 0.015D, 0.0D);
        }
    }

    protected int particleInterval() {
        return 8;
    }

    protected abstract ParticleOptions ambientParticle();

    public abstract void prepareForWave();
}
