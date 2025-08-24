package net.fneifnox.extraentityattributes.attributes.foodSpeed;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Code was copied from the mod Artifacts. Check out Artifacts!
@Mixin(LivingEntity.class)
public class EatingSpeedMixin {

    @ModifyExpressionValue(method = "setCurrentHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxUseTime(Lnet/minecraft/entity/LivingEntity;)I"))
    private int changeEatingSpeed(int original, Hand hand) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return changeEatDuration(original, entity.getStackInHand(hand), entity);
    }

    private static int changeEatDuration(int originalDuration, ItemStack item, LivingEntity entity) {

        if (item.getUseAction() == UseAction.EAT) {
            return (int) Math.max(1, Math.round(originalDuration / entity.getAttributeValue(ExtraEntityAttributes.EATING_SPEED)));
        } else if (item.getUseAction() == UseAction.DRINK) {
            return (int) Math.max(1, Math.round(originalDuration / entity.getAttributeValue(ExtraEntityAttributes.DRINKING_SPEED)));
        }
        return originalDuration;
    }
}
