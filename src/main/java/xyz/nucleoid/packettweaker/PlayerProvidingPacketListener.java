package xyz.nucleoid.packettweaker;

import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public interface PlayerProvidingPacketListener {
    @Nullable
    default ServerPlayer packet_tweaker$getPlayerForPacketTweaker() {
        return null;
    }

    @Nullable
    static ServerPlayer getPlayer(PacketListener listener) {
        return ((PlayerProvidingPacketListener) listener).packet_tweaker$getPlayerForPacketTweaker();
    }
}
