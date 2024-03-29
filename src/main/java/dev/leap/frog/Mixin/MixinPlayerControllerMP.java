package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Movement.EventPlayerRightClick;
import dev.leap.frog.Event.Movement.EventPlayerStoppedUsingItem;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Inject(method = "onStoppedUsingItem", at = @At("HEAD"), cancellable = true)
    public void onItemUse(EntityPlayer playerIn, CallbackInfo ci) {
        EventPlayerStoppedUsingItem packet = new EventPlayerStoppedUsingItem();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }

    }

    @Inject(method = "processRightClick", at = @At("HEAD"), cancellable = true)
    public void onClick(EntityPlayer player, World worldIn, EnumHand hand, CallbackInfoReturnable<EnumActionResult> cir) {
        EventPlayerRightClick packet = new EventPlayerRightClick();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            cir.cancel();
        }

    }

}
