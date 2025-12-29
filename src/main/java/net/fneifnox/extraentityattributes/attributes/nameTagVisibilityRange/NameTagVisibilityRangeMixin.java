package net.fneifnox.extraentityattributes.attributes.nameTagVisibilityRange;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityRenderer.class)
public class NameTagVisibilityRangeMixin {

    @ModifyExpressionValue(method = "renderLabelIfPresent", at = @At(value = "CONSTANT", args = "doubleValue=4096.0"))
    private double changeNameTagVisibilityRange(double constant) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player != null) {
            return player.getAttributeValue(ExtraEntityAttributes.NAME_TAG_VISIBILITY_RANGE) * player.getAttributeValue(ExtraEntityAttributes.NAME_TAG_VISIBILITY_RANGE);
        }
        else {
            return constant;
        }
    }
}
