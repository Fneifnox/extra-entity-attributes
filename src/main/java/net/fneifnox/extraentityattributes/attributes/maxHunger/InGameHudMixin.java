package net.fneifnox.extraentityattributes.attributes.maxHunger;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    private static final Identifier FOOD_EMPTY_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_empty_hunger");
    private static final Identifier FOOD_HALF_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_half_hunger");
    private static final Identifier FOOD_FULL_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_full_hunger");
    private static final Identifier FOOD_EMPTY_TEXTURE = Identifier.ofVanilla("hud/food_empty");
    private static final Identifier FOOD_HALF_TEXTURE = Identifier.ofVanilla("hud/food_half");
    private static final Identifier FOOD_FULL_TEXTURE = Identifier.ofVanilla("hud/food_full");

    private final Random random = Random.create();

    // I should probably rewrite this for compat reasons...
    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void resetFoodIndex(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        ci.cancel();
        InGameHud inGameHud = (InGameHud)(Object)this;
        int maxFood = (int) Math.ceil(player.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER) / 2);

        HungerManager hungerManager = player.getHungerManager();
        int foodLevel = hungerManager.getFoodLevel();
        RenderSystem.enableBlend();

        for(int j = 0; j < maxFood; ++j) {
            int row = j / 10;
            int col = j % 10;
            int x = right - col * 8 - 9;
            int y = top - row * 8;
            Identifier identifier;
            Identifier identifier2;
            Identifier identifier3;
            if (player.hasStatusEffect(StatusEffects.HUNGER)) {
                identifier = FOOD_EMPTY_HUNGER_TEXTURE;
                identifier2 = FOOD_HALF_HUNGER_TEXTURE;
                identifier3 = FOOD_FULL_HUNGER_TEXTURE;
            } else {
                identifier = FOOD_EMPTY_TEXTURE;
                identifier2 = FOOD_HALF_TEXTURE;
                identifier3 = FOOD_FULL_TEXTURE;
            }

            if (player.getHungerManager().getSaturationLevel() <= 0.0F && inGameHud.getTicks() % (foodLevel * 3 + 1) == 0) {
                y += random.nextInt(3) - 1;
            }

            context.drawGuiTexture(identifier, x, y, 9, 9);
            if (j * 2 + 1 < foodLevel) {
                context.drawGuiTexture(identifier3, x, y, 9, 9);
            }

            if (j * 2 + 1 == foodLevel) {
                context.drawGuiTexture(identifier2, x, y, 9, 9);
            }
        }

        RenderSystem.disableBlend();
    }
}
