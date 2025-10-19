package net.fneifnox.extraentityattributes.attributes.mountSpeedMultiplier;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MovementSpeedMixin {

    final Identifier MOUNT_SPEED_ID = Identifier.of("extra-entity-attributes", "mount_speed");

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void test(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        var attribute = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        if (!livingEntity.hasPlayerRider() && attribute.getModifier(MOUNT_SPEED_ID) != null) {
            attribute.removeModifier(MOUNT_SPEED_ID);
            return;
        }

        if (attribute.getModifier(MOUNT_SPEED_ID) != null) {
            attribute.removeModifier(MOUNT_SPEED_ID);
        }

        if (livingEntity.getControllingPassenger() instanceof PlayerEntity rider) {
            attribute.addTemporaryModifier(new EntityAttributeModifier(
                    MOUNT_SPEED_ID, rider.getAttributeValue(ExtraEntityAttributes.MOUNT_SPEED_MULTIPLIER), EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ));
        }
    }
}
