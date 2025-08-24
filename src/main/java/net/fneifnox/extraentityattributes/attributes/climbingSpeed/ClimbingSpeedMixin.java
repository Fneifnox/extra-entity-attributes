package net.fneifnox.extraentityattributes.attributes.climbingSpeed;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class ClimbingSpeedMixin {

    @Inject(method = "travel", at = @At("TAIL"))
    private void changeClimbingSpeed(Vec3d movementInput, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;

        if (livingEntity.horizontalCollision && livingEntity.isClimbing()) {
            livingEntity.setVelocity(livingEntity.getVelocity().x, livingEntity.getAttributeValue(ExtraEntityAttributes.CLIMBING_SPEED), livingEntity.getVelocity().z);
        }
    }
}
