package net.fneifnox.extraentityattributes.attributes.statusEffectDuration;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Environment(EnvType.CLIENT)
@Mixin(PotionItem.class)
public class PotionTooltipMixin {

    @ModifyConstant(method = "appendTooltip", constant = @Constant(floatValue = 1.0F))
    private float changeTooltipDuration(float constant) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player != null) {
            return (float) player.getAttributeValue(ExtraEntityAttributes.STATUS_EFFECT_DURATION);
        }
        else {
            return constant;
        }
    }
}
