package xyz.nucleoid.packettweaker.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.nucleoid.packettweaker.PlayerProvidingPacketListener;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerPlayNetworkHandlerMixin implements PlayerProvidingPacketListener {
    @Shadow
    public ServerPlayer player;

    @Override
    public @Nullable ServerPlayer packet_tweaker$getPlayerForPacketTweaker() {
        return this.player;
    }
}
