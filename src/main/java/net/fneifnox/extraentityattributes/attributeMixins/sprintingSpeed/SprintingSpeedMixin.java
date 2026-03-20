package net.fneifnox.extraentityattributes.attributeMixins.sprintingSpeed;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class SprintingSpeedMixin {

    @ModifyReturnValue(method = "getMovementSpeed()F", at = @At("RETURN"))
    private float changeSprintingSpeed(float original) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if (player.isSprinting()) {
            return (float) player.getAttributeValue(ExtraEntityAttributes.SPRINTING_SPEED);
        }

        return original;
    }
}
