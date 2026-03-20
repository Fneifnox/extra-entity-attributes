package net.fneifnox.extraentityattributes.attributeMixins.maxHunger;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.fneifnox.extraentityattributes.ExtraEntityAttributes.hasBeenCalled;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Unique private static final Identifier FOOD_EMPTY_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_empty_hunger");
    @Unique private static final Identifier FOOD_HALF_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_half_hunger");
    @Unique private static final Identifier FOOD_FULL_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_full_hunger");
    @Unique private static final Identifier FOOD_EMPTY_TEXTURE = Identifier.ofVanilla("hud/food_empty");
    @Unique private static final Identifier FOOD_HALF_TEXTURE = Identifier.ofVanilla("hud/food_half");
    @Unique private static final Identifier FOOD_FULL_TEXTURE = Identifier.ofVanilla("hud/food_full");

    @Unique
    private final Random random = Random.create();

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void changeFoodRendering(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        ci.cancel();
        InGameHud inGameHud = (InGameHud)(Object)this;
        int maxFood = (int) Math.ceil(player.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER) / 2);

        if (!hasBeenCalled) {
            hasBeenCalled = true;
            // To avoid issues with eating food that is alwaysEdible when you're already full
            player.getHungerManager().update(player);
        }

        HungerManager hungerManager = player.getHungerManager();
        int foodLevel = hungerManager.getFoodLevel();
        RenderSystem.enableBlend();

        for (int j = 0; j < maxFood; ++j) {
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

    // Dynamically change the position of air bubbles to match the MAX_HUNGER value
    @ModifyArg(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"), index = 2)
    private int changeAirPosition(int original) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER) > 20.0) {
            int hungerRows = (int) Math.ceil(player.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER) / 20);
            return original - (hungerRows - 1) * 8;
        }
        return original;
    }
}
