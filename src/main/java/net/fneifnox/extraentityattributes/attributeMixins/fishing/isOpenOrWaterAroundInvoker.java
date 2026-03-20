package net.fneifnox.extraentityattributes.attributeMixins.fishing;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FishingBobberEntity.class)
public interface isOpenOrWaterAroundInvoker {

    // FishingMixin
    @Invoker("isOpenOrWaterAround")
    boolean getIsOpenOrWaterAround(BlockPos pos);
}
