package net.fneifnox.extraentityattributes.attributes.maxHunger;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerBarMixin {

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

    @ModifyConstant(method = "addInternal", constant = @Constant(intValue = 20))
    private int changeMaxValue(int constant) {
        if (getPlayer != null) {
            return (int) getPlayer.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER);
        }
        else {
            return constant;
        }
    }

    @ModifyConstant(method = "isNotFull", constant = @Constant(intValue = 20))
    private int changeFullValue(int constant) {
        if (getPlayer != null) {
            return (int) getPlayer.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER);
        }
        else {
            return 20;
        }
    }
}
