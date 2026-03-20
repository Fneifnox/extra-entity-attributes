package net.fneifnox.extraentityattributes.attributeMixins.foodMultiplier;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntity.class)
public abstract class FoodMixin {

    @ModifyArg(method = "eatFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;eat(Lnet/minecraft/component/type/FoodComponent;)V"), index = 0)
    private FoodComponent changeNutritionAndSaturation(FoodComponent foodComponent) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        FoodComponent newFood = new FoodComponent.Builder()
                .nutrition((int) Math.round(foodComponent.nutrition() * player.getAttributeValue(ExtraEntityAttributes.FOOD_NUTRITION_MULTIPLIER)))
                .saturationModifier(((foodComponent.saturation() / (float) player.getAttributeValue(ExtraEntityAttributes.FOOD_NUTRITION_MULTIPLIER)) / (foodComponent.nutrition() * 2)) * (float) player.getAttributeValue(ExtraEntityAttributes.FOOD_SATURATION_MULTIPLIER)) // This is correct, isn't it?!?
                .alwaysEdible()
                .build();
        return newFood;
    }
}
