package net.fneifnox.extraentityattributes.attributeMixins.slipperiness;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fneifnox.extraentityattributes.ExtraEntityAttributes;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.BlockTags;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

// Code was copied from the mod Artifacts. Check out Artifacts!
@Mixin(LivingEntity.class)
public class SlipperinessMixin {

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    private float changeSlipperiness(Block block, Operation<Float> original) {
        return getModifiedSlipperiness(original.call(block), (LivingEntity) (Object) this, block);
    }

    @Unique
    private static float getModifiedSlipperiness(float friction, LivingEntity entity, Block block) {
        if (friction > 0.6F && block.getDefaultState().isIn(BlockTags.ICE)) {
            double slipperinessReduction = entity.getAttributeValue(ExtraEntityAttributes.SLIPPERINESS);
            return Math.lerp((float) slipperinessReduction, friction, 0.6F);
        }
        return friction;
    }
}
