package net.fneifnox.extraentityattributes.attributes.sprintingFood;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public class FoodNeededForSprintingMixin {

    @ModifyExpressionValue(method = "canSprint", at = @At(value = "CONSTANT", args = "floatValue=6.0F"))
    private float changeSprintingFood(float original) {
        ClientPlayerEntity clientPlayer = (ClientPlayerEntity)(Object)this;
        return (float) clientPlayer.getAttributeValue(ExtraEntityAttributes.SPRINTING_FOOD);
    }
}
