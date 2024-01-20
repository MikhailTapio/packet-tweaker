package xyz.nucleoid.packettweaker.mixin;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketDecoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nucleoid.packettweaker.PacketContext;
import xyz.nucleoid.packettweaker.PlayerProvidingPacketListener;
import xyz.nucleoid.packettweaker.impl.ConnectionHolder;

import java.util.List;

@Mixin(PacketDecoder.class)
public class PacketDecoderMixin implements ConnectionHolder {
    @Unique
    private Connection packet_tweaker$connection;

    @Override
    public void packet_tweaker$setConnection(Connection connection) {
        this.packet_tweaker$connection = connection;
    }

    @Inject(method = "decode", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ConnectionProtocol;createPacket(Lnet/minecraft/network/protocol/PacketFlow;ILnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/Packet;", shift = At.Shift.BEFORE))
    private void packetTweaker_setPacketContext(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci) {
        if (this.packet_tweaker$connection != null) {
            PacketContext.setContext(PlayerProvidingPacketListener.getPlayer(this.packet_tweaker$connection.getPacketListener()));
        }
    }

    @Inject(method = "decode", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ConnectionProtocol;createPacket(Lnet/minecraft/network/protocol/PacketFlow;ILnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/Packet;", shift = At.Shift.AFTER))
    private void packetTweaker_clearPacketContext(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci) {
        PacketContext.clearContext();
    }
}
