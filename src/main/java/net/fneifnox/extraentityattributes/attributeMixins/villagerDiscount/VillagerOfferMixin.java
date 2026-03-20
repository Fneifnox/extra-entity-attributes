package net.fneifnox.extraentityattributes.attributeMixins.villagerDiscount;

import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerOfferMixin {

    @Inject(method = "prepareOffersFor(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("RETURN"))
    private void onPrepareOffersFor(PlayerEntity player, CallbackInfo ci) {
        VillagerEntity villager = (VillagerEntity)(Object)this;

        for (TradeOffer finalTradeOffer : villager.getOffers()) {
            int price = (int)Math.floor((player.getAttributeValue(ExtraEntityAttributes.VILLAGER_DISCOUNT))
                    * (double)finalTradeOffer.getOriginalFirstBuyItem().getCount());
            finalTradeOffer.increaseSpecialPrice(-price);
        }
    }
}
