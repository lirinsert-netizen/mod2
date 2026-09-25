package com.zombiehorde.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public final class RewardBagItem extends Item {
    public RewardBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            if (!player.isCreative()) {
                heldStack.shrink(1);
            }

            RandomSource random = player.getRandom();
            grantLoot(serverPlayer, random);
            grantBonusEffect(serverPlayer, random);
            serverPlayer.giveExperiencePoints(4 + random.nextInt(9));

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        24, 0.55D, 0.75D, 0.55D, 0.05D
                );
            }

            serverPlayer.playNotifySound(SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.8F, 1.15F);
            serverPlayer.displayClientMessage(Component.translatable("message.zombiehorde.reward").withStyle(ChatFormatting.GOLD), true);
        }

        return InteractionResultHolder.success(heldStack);
    }

    private static void grantLoot(ServerPlayer player, RandomSource random) {
        int roll = random.nextInt(100);

        if (roll < 35) {
            give(player, new ItemStack(Items.IRON_INGOT, 4 + random.nextInt(7)));
            give(player, new ItemStack(Items.COAL, 6 + random.nextInt(11)));
        } else if (roll < 60) {
            give(player, new ItemStack(Items.GOLD_INGOT, 3 + random.nextInt(5)));
            give(player, new ItemStack(Items.EMERALD, 1 + random.nextInt(3)));
        } else if (roll < 80) {
            give(player, new ItemStack(Items.DIAMOND, 1 + random.nextInt(2)));
            give(player, new ItemStack(Items.LAPIS_LAZULI, 4 + random.nextInt(9)));
        } else if (roll < 92) {
            give(player, new ItemStack(Items.GOLDEN_APPLE, 1));
            give(player, new ItemStack(Items.EXPERIENCE_BOTTLE, 4 + random.nextInt(5)));
        } else if (roll < 98) {
            give(player, new ItemStack(Items.ENDER_PEARL, 2 + random.nextInt(4)));
            give(player, new ItemStack(Items.DIAMOND, 1));
        } else {
            give(player, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1));
        }
    }

    private static void grantBonusEffect(ServerPlayer player, RandomSource random) {
        MobEffectInstance effect = switch (random.nextInt(4)) {
            case 0 -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 20, 0);
            case 1 -> new MobEffectInstance(MobEffects.REGENERATION, 12 * 20, 0);
            case 2 -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 16 * 20, 0);
            default -> new MobEffectInstance(MobEffects.ABSORPTION, 20 * 20, 0);
        };
        player.addEffect(effect);
    }

    private static void give(ServerPlayer player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.zombiehorde.reward_bag").withStyle(ChatFormatting.GRAY));
    }
}
