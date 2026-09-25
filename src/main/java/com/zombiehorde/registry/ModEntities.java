package com.zombiehorde.registry;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.entity.BruteZombie;
import com.zombiehorde.entity.EmberZombie;
import com.zombiehorde.entity.PlagueZombie;
import com.zombiehorde.entity.RunnerZombie;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ZombieHordeMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<RunnerZombie>> RUNNER_ZOMBIE =
            ENTITY_TYPES.register("runner_zombie", () -> EntityType.Builder
                    .of(RunnerZombie::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .build("runner_zombie"));

    public static final DeferredHolder<EntityType<?>, EntityType<BruteZombie>> BRUTE_ZOMBIE =
            ENTITY_TYPES.register("brute_zombie", () -> EntityType.Builder
                    .of(BruteZombie::new, MobCategory.MONSTER)
                    .sized(0.72F, 2.15F)
                    .build("brute_zombie"));

    public static final DeferredHolder<EntityType<?>, EntityType<PlagueZombie>> PLAGUE_ZOMBIE =
            ENTITY_TYPES.register("plague_zombie", () -> EntityType.Builder
                    .of(PlagueZombie::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .build("plague_zombie"));

    public static final DeferredHolder<EntityType<?>, EntityType<EmberZombie>> EMBER_ZOMBIE =
            ENTITY_TYPES.register("ember_zombie", () -> EntityType.Builder
                    .of(EmberZombie::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .build("ember_zombie"));

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(RUNNER_ZOMBIE.get(), Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38D)
                .add(Attributes.SCALE, 0.90D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
                .build());

        event.put(BRUTE_ZOMBIE.get(), Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 48.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.19D)
                .add(Attributes.SCALE, 1.25D)
                .add(Attributes.ATTACK_DAMAGE, 7.5D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.65D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
                .build());

        event.put(PLAGUE_ZOMBIE.get(), Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.SCALE, 1.00D)
                .add(Attributes.ATTACK_DAMAGE, 4.5D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
                .build());

        event.put(EMBER_ZOMBIE.get(), Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 28.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.SCALE, 1.05D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
                .build());
    }

    private ModEntities() {
    }
}
