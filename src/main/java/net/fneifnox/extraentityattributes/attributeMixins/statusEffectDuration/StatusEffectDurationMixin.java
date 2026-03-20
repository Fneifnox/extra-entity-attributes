package net.fneifnox.extraentityattributes.attributeMixins.statusEffectDuration;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class StatusEffectDurationMixin {

    @Inject(method = "addStatusEffect*", at = @At("HEAD"), cancellable = true)
    private void modifyEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        StatusEffectInstance modified = new StatusEffectInstance(
                effect.getEffectType(),
                (int) (effect.getDuration() * livingEntity.getAttributeValue(ExtraEntityAttributes.STATUS_EFFECT_DURATION)),
                effect.getAmplifier(),
                effect.isAmbient(),
                effect.shouldShowParticles(),
                effect.shouldShowIcon()
        );

        cir.setReturnValue(livingEntity.addStatusEffect(modified, null));
        cir.cancel();
    }
}
