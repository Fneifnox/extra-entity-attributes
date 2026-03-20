package net.fneifnox.extraentityattributes.attributeMixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityAttributesMixin {

    @ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
    private static DefaultAttributeContainer.Builder addExtraEntityAttributes(DefaultAttributeContainer.Builder builder) {
        return builder
                .add(ExtraEntityAttributes.CLIMBING_SPEED)
                .add(ExtraEntityAttributes.STATUS_EFFECT_DURATION)
                .add(ExtraEntityAttributes.SLIPPERINESS)
                .add(ExtraEntityAttributes.DODGE_CHANCE);
    }
}
