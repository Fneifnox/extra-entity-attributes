package net.fneifnox.extraentityattributes.attributes.steal;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityDamageMixin {

    @Inject(method = "damage", at = @At("HEAD"))
    private void getStealAndHeal(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getAttacker();

        if (attacker instanceof PlayerEntity player) {
            float lifestealPercent = (float) (player.getAttributeValue(ExtraEntityAttributes.LIFESTEAL));
            float hungerstealPercent = (float) (player.getAttributeValue(ExtraEntityAttributes.HUNGERSTEAL));
            LivingEntity livingEntity = (LivingEntity)(Object)(this);
            if (amount > livingEntity.getHealth()) {
                player.heal(livingEntity.getHealth() * lifestealPercent);
                player.getHungerManager().setFoodLevel((int) (player.getHungerManager().getPrevFoodLevel() + livingEntity.getHealth() * hungerstealPercent));
            }
            else {
                player.heal(amount * lifestealPercent);
                player.getHungerManager().setFoodLevel((int) (player.getHungerManager().getPrevFoodLevel() + amount * hungerstealPercent));
            }
        }
    }
}
