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

public final class PlagueZombie extends HordeZombie {
    public PlagueZombie(EntityType<? extends PlagueZombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
            living.addEffect(new MobEffectInstance(MobEffects.HUNGER, 160, 0));
        }
        return hit;
    }

    @Override
    protected ParticleOptions ambientParticle() {
        return ParticleTypes.SPORE_BLOSSOM_AIR;
    }

    @Override
    public void prepareForWave() {
        setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        setDropChance(EquipmentSlot.CHEST, 0.0F);
    }
}
