package com.zombiehorde.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public final class RunnerZombie extends HordeZombie {
    public RunnerZombie(EntityType<? extends RunnerZombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
        }
        return hit;
    }

    @Override
    protected ParticleOptions ambientParticle() {
        return ParticleTypes.CLOUD;
    }

    @Override
    protected int particleInterval() {
        return 6;
    }

    @Override
    public void prepareForWave() {
        setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        setDropChance(EquipmentSlot.HEAD, 0.0F);
    }
}
