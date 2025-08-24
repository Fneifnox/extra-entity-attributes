package net.fneifnox.extraentityattributes.attributes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerAttributesMixin {

    @ModifyReturnValue(method = "createPlayerAttributes", at = @At("RETURN"))
    private static DefaultAttributeContainer.Builder addExtraEntityAttributes(DefaultAttributeContainer.Builder builder) {
        return builder
                .add(ExtraEntityAttributes.VILLAGER_DISCOUNT)
                .add(ExtraEntityAttributes.EATING_SPEED)
                .add(ExtraEntityAttributes.CREATIVE_FLYING_SPEED)
                .add(ExtraEntityAttributes.SPRINTING_SPEED)
                .add(ExtraEntityAttributes.FOOD_NUTRITION_MULTIPLIER)
                .add(ExtraEntityAttributes.FOOD_SATURATION_MULTIPLIER)
                .add(ExtraEntityAttributes.NAME_TAG_VISIBILITY_RANGE)
                .add(ExtraEntityAttributes.DRINKING_SPEED)
                .add(ExtraEntityAttributes.MAX_HUNGER);
    }
}
