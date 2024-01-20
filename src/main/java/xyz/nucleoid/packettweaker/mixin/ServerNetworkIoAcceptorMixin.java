package xyz.nucleoid.packettweaker.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.nucleoid.packettweaker.impl.ConnectionHolder;

@Mixin(targets = "net/minecraft/server/network/ServerConnectionListener$1")
public class ServerNetworkIoAcceptorMixin {
    @Inject(
            method = "initChannel(Lio/netty/channel/Channel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;setListener(Lnet/minecraft/network/PacketListener;)V"),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void packetTweaker_initChannel(Channel channel, CallbackInfo ci, ChannelPipeline channelPipeline, int rateLimit, Connection connection) {
        ConnectionHolder encoder = (ConnectionHolder) channel.pipeline().get("encoder");
        encoder.packet_tweaker$setConnection(connection);

        ConnectionHolder decoder = (ConnectionHolder) channel.pipeline().get("decoder");
        decoder.packet_tweaker$setConnection(connection);
    }
}
