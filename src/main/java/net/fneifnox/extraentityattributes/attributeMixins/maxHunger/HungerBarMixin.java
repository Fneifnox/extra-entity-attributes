package net.fneifnox.extraentityattributes.attributeMixins.maxHunger;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerBarMixin {

    @Unique
    PlayerEntity getPlayer;

    @Inject(method = "update", at = @At("HEAD"))
    private void getPlayer(PlayerEntity player, CallbackInfo ci) {
        if (getPlayer == null) {
            getPlayer = player;
        }

        // To decrease internal foodLevel to MAX_HUNGER
        HungerManager hungerManager = (HungerManager)(Object)this;
        if (getPlayer != null) {
            if (hungerManager.getFoodLevel() > getPlayer.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER)) {
                hungerManager.setFoodLevel((int) getPlayer.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER));
            }
        }
    }

    @ModifyExpressionValue(method = "addInternal", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int changeMaxValue(int constant) {
        if (getPlayer != null) {
            ExtraEntityAttributes.hasBeenCalled = false;
            return (int) getPlayer.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER);
        }
        else {
            return constant;
        }
    }

    @ModifyExpressionValue(method = "isNotFull", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int changeFullValue(int constant) {
        if (getPlayer != null) {
            ExtraEntityAttributes.hasBeenCalled = false;
            return (int) getPlayer.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER);
        }
        else {
            return constant;
        }
    }
}
