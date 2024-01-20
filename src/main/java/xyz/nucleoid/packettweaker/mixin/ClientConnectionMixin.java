package xyz.nucleoid.packettweaker.mixin;

import io.netty.channel.ChannelPipeline;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nucleoid.packettweaker.impl.ConnectionHolder;

@Mixin(Connection.class)
public class ClientConnectionMixin {
    @Inject(
            method = "configurePacketHandler",
            at = @At("TAIL")
    )
    private void packetTweaker_initChannel(ChannelPipeline pipeline, CallbackInfo ci) {
        var self = (Connection) (Object) this;
        ConnectionHolder encoder = (ConnectionHolder) pipeline.get("encoder");
        if (encoder != null) {
            encoder.packet_tweaker$setConnection(self);
        }
        ConnectionHolder decoder = (ConnectionHolder) pipeline.get("decoder");
        if (decoder != null) {
            decoder.packet_tweaker$setConnection(self);
        }
    }
}
