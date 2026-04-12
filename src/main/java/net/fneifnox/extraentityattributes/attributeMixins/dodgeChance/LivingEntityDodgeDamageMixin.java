package net.fneifnox.extraentityattributes.attributeMixins.dodgeChance;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.fneifnox.extraentityattributes.event.DodgeDamageEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityDodgeDamageMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void dodgeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity attackedLivingEntity = (LivingEntity)(Object)this;
        float dodgeChance = (float) attackedLivingEntity.getAttributeValue(ExtraEntityAttributes.DODGE_CHANCE);

        if (attackedLivingEntity.getRandom().nextFloat() <= dodgeChance) {
            DodgeDamageEvent.EVENT.invoker().onDodge(attackedLivingEntity, source, amount);
            cir.setReturnValue(false);
        }
    }
}
