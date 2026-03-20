package net.fneifnox.extraentityattributes.attributes.maxJumps;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.HashSet;
import java.util.UUID;

public class JumpAmountModifier {

    static boolean firstJumpDone = false;

    static int remainingJumps;

    private static final HashSet<UUID> hasJumpedLastTick = new HashSet<>();

    public static void modifyJumpAmount(int maxJumps) {
        if (MinecraftClient.getInstance().player == null) return;
        ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
        if (clientPlayer.getAttributeValue(ExtraEntityAttributes.MAX_JUMPS) <= 1) return;

        boolean isJumping = clientPlayer.input.jumping;

        if (isJumping && !hasJumpedLastTick.contains(clientPlayer.getUuid()) && remainingJumps != 0) {
            if (firstJumpDone) {
                clientPlayer.jump();
            }
            remainingJumps--;
            hasJumpedLastTick.add(clientPlayer.getUuid());
            firstJumpDone = true;
        }

        if (!isJumping) {
            hasJumpedLastTick.remove(clientPlayer.getUuid());
        }

        if (clientPlayer.isOnGround() && !isJumping) {
            remainingJumps = maxJumps;
            firstJumpDone = false;
        }
    }
}
