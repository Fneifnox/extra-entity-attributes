package net.fneifnox.extraentityattributes.attributeMixins.creativeFlyingSpeed;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class FlyingSpeedMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void changeCreativeFlyingSpeed(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getAbilities().getFlySpeed() != player.getAttributeValue(ExtraEntityAttributes.CREATIVE_FLYING_SPEED)) {
            player.getAbilities().setFlySpeed((float) player.getAttributeValue(ExtraEntityAttributes.CREATIVE_FLYING_SPEED));
        }
    }
}
