package com.zombiehorde.event;

import com.zombiehorde.ZombieHordeMod;
import com.zombiehorde.config.HordeConfig;
import com.zombiehorde.entity.HordeZombie;
import com.zombiehorde.registry.ModEntities;
import com.zombiehorde.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = ZombieHordeMod.MOD_ID)
public final class HordeEvents {
    private static final Map<UUID, Long> NEXT_WAVE_TICK = new HashMap<>();
    private static final Map<UUID, Integer> WAVE_NUMBER = new HashMap<>();
    private static long serverTicks;

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        serverTicks++;
        if (serverTicks % 20L != 0L) {
            return;
        }

        long intervalTicks = HordeConfig.WAVE_INTERVAL_SECONDS.get() * 20L;

        Set<UUID> onlinePlayers = new HashSet<>();
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            onlinePlayers.add(player.getUUID());
        }
        NEXT_WAVE_TICK.keySet().retainAll(onlinePlayers);
        WAVE_NUMBER.keySet().retainAll(onlinePlayers);

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            UUID playerId = player.getUUID();

            if (player.isSpectator() || (player.isCreative() && !HordeConfig.SPAWN_FOR_CREATIVE_PLAYERS.get())) {
                NEXT_WAVE_TICK.put(playerId, serverTicks + intervalTicks);
                continue;
            }

            long nextWave = NEXT_WAVE_TICK.computeIfAbsent(playerId, ignored -> serverTicks + intervalTicks);
            if (serverTicks < nextWave) {
                continue;
            }

            spawnWave(player);
            NEXT_WAVE_TICK.put(playerId, serverTicks + intervalTicks);
        }
    }

    @SubscribeEvent
    public static void onHordeZombieDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof HordeZombie)) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }

        int chance = HordeConfig.REWARD_BAG_CHANCE_PERCENT.get();
        if (chance <= 0 || player.getRandom().nextInt(100) >= chance) {
            return;
        }

        ItemStack bag = new ItemStack(ModItems.REWARD_BAG.get());
        if (!player.getInventory().add(bag)) {
            player.drop(bag, false);
        }
    }

    private static void spawnWave(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            return;
        }

        RandomSource random = player.getRandom();
        int wave = WAVE_NUMBER.merge(player.getUUID(), 1, Integer::sum);

        int configuredMin = Math.min(HordeConfig.MIN_HORDE_SIZE.get(), HordeConfig.MAX_HORDE_SIZE.get());
        int configuredMax = Math.max(HordeConfig.MIN_HORDE_SIZE.get(), HordeConfig.MAX_HORDE_SIZE.get());
        int targetCount = configuredMin + random.nextInt(configuredMax - configuredMin + 1);
        if (wave % 5 == 0) {
            targetCount += 2;
        }

        int spawned = 0;
        for (int i = 0; i < targetCount; i++) {
            Optional<BlockPos> spawnPos = findSpawnPosition(level, player, random);
            if (spawnPos.isEmpty()) {
                continue;
            }

            BlockPos pos = spawnPos.get();
            HordeZombie zombie = spawnWaveZombie(level, pos, random, wave);
            if (zombie == null) {
                continue;
            }

            zombie.prepareForWave();
            zombie.setTarget(player);
            spawned++;
            level.sendParticles(ParticleTypes.SOUL, zombie.getX(), zombie.getY() + 0.7D, zombie.getZ(), 10, 0.35D, 0.6D, 0.35D, 0.02D);
        }

        if (spawned > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 0, false, false));
            player.playNotifySound(SoundEvents.ZOMBIE_AMBIENT, SoundSource.HOSTILE, 1.0F, 0.65F);
            player.displayClientMessage(
                    Component.translatable("message.zombiehorde.wave", wave, spawned).withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD),
                    true
            );
            level.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY() + 0.5D, player.getZ(), 28, 2.0D, 0.7D, 2.0D, 0.03D);
        }
    }

    private static HordeZombie spawnWaveZombie(ServerLevel level, BlockPos pos, RandomSource random, int wave) {
        int roll = random.nextInt(100);

        if (wave % 5 == 0 && roll >= 70) {
            return ModEntities.BRUTE_ZOMBIE.get().spawn(level, pos, MobSpawnType.EVENT);
        }
        if (roll < 36) {
            return ModEntities.RUNNER_ZOMBIE.get().spawn(level, pos, MobSpawnType.EVENT);
        }
        if (roll < 64) {
            return ModEntities.PLAGUE_ZOMBIE.get().spawn(level, pos, MobSpawnType.EVENT);
        }
        if (roll < 88) {
            return ModEntities.EMBER_ZOMBIE.get().spawn(level, pos, MobSpawnType.EVENT);
        }
        return ModEntities.BRUTE_ZOMBIE.get().spawn(level, pos, MobSpawnType.EVENT);
    }

    private static Optional<BlockPos> findSpawnPosition(ServerLevel level, ServerPlayer player, RandomSource random) {
        int minRadius = Math.min(HordeConfig.MIN_SPAWN_RADIUS.get(), HordeConfig.MAX_SPAWN_RADIUS.get());
        int maxRadius = Math.max(HordeConfig.MIN_SPAWN_RADIUS.get(), HordeConfig.MAX_SPAWN_RADIUS.get());

        for (int attempt = 0; attempt < 24; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double radius = minRadius == maxRadius
                    ? minRadius
                    : minRadius + random.nextDouble() * (maxRadius - minRadius);
            int x = Mth.floor(player.getX() + Math.cos(angle) * radius);
            int z = Mth.floor(player.getZ() + Math.sin(angle) * radius);

            int centerY = player.blockPosition().getY();
            int topY = Math.min(level.getMaxBuildHeight() - 3, centerY + 8);
            int bottomY = Math.max(level.getMinBuildHeight() + 1, centerY - 10);

            for (int y = topY; y >= bottomY; y--) {
                BlockPos feetPos = new BlockPos(x, y, z);
                BlockPos headPos = feetPos.above();
                BlockPos groundPos = feetPos.below();

                BlockState feet = level.getBlockState(feetPos);
                BlockState head = level.getBlockState(headPos);
                BlockState ground = level.getBlockState(groundPos);

                boolean solidGround = !ground.getCollisionShape(level, groundPos).isEmpty();
                boolean freeFeet = feet.getCollisionShape(level, feetPos).isEmpty() && level.getFluidState(feetPos).isEmpty();
                boolean freeHead = head.getCollisionShape(level, headPos).isEmpty() && level.getFluidState(headPos).isEmpty();

                if (solidGround && freeFeet && freeHead) {
                    return Optional.of(feetPos);
                }
            }
        }

        return Optional.empty();
    }

    private HordeEvents() {
    }
}
