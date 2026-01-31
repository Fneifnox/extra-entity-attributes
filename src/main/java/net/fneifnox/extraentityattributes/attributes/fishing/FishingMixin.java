package net.fneifnox.extraentityattributes.attributes.fishing;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FishingBobberEntity.class)
public abstract class FishingMixin {

    @Shadow @Final private int waitTimeReductionTicks;

    @Shadow
    private int waitCountdown;

    @Shadow
    @Final
    private int luckBonus;

    @Definition(id = "waitTimeReductionTicks", field = "Lnet/minecraft/entity/projectile/FishingBobberEntity;waitTimeReductionTicks:I")
    @Definition(id = "waitCountdown", field = "Lnet/minecraft/entity/projectile/FishingBobberEntity;waitCountdown:I")
    @Expression("?.waitCountdown = ?.waitCountdown - this.waitTimeReductionTicks")
    @WrapOperation(method = "tickFishingLogic", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private void modifyFishingSpeed(FishingBobberEntity instance, int value, Operation<Void> original) {
        PlayerEntity player = instance.getPlayerOwner();
        if (player != null) {
            this.waitCountdown = (int) ((this.waitCountdown - this.waitTimeReductionTicks) * player.getAttributeValue(ExtraEntityAttributes.FISHING_DURATION_MULTIPLIER));
        }
    }

    @Definition(id = "luckBonus", field = "Lnet/minecraft/entity/projectile/FishingBobberEntity;luckBonus:I")
    @Definition(id = "getLuck", method = "Lnet/minecraft/entity/player/PlayerEntity;getLuck()F")
    @Expression("((float) ?.luckBonus + ?.getLuck())")
    @ModifyExpressionValue(method = "use", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private float modifyFishingLuck(float original) {
        FishingBobberEntity fishingBobber = (FishingBobberEntity)(Object)this;
        PlayerEntity player = fishingBobber.getPlayerOwner();
        if (player != null) {
            return (float) (this.luckBonus + player.getLuck() + player.getAttributeValue(ExtraEntityAttributes.ADDITIONAL_FISHING_LUCK));
        }
        return original;
    }
}
