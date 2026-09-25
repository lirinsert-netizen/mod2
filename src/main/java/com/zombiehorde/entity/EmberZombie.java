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

public final class EmberZombie extends HordeZombie {
    public EmberZombie(EntityType<? extends EmberZombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            target.igniteForSeconds(4.0F);
            if (target instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 0));
            }
        }
        return hit;
    }

    @Override
    protected ParticleOptions ambientParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    protected int particleInterval() {
        return 5;
    }

    @Override
    public void prepareForWave() {
        setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
        setDropChance(EquipmentSlot.HEAD, 0.0F);
        setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }
}
