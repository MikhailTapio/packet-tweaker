package xyz.nucleoid.packettweaker;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public interface ContextProvidingPacketListener {
    ContextProvidingPacketListener EMPTY = new ContextProvidingPacketListener() {
    };

    @Nullable
    default ServerPlayer getPlayerForPacketTweaker() {
        return null;
    }

    @Nullable
    default ClientInformation getClientOptionsForPacketTweaker() {
        return null;
    }

    @Nullable
    default GameProfile getGameProfileForPacketTweaker() {
        return null;
    }

    @Nullable
    static ServerPlayer getPlayer(PacketListener listener) {
        return ((ContextProvidingPacketListener) listener).getPlayerForPacketTweaker();
    }

    @Nullable
    static ClientInformation getClientOptions(PacketListener listener) {
        return ((ContextProvidingPacketListener) listener).getClientOptionsForPacketTweaker();
    }

    @Nullable
    static GameProfile getGameProfile(PacketListener listener) {
        return ((ContextProvidingPacketListener) listener).getGameProfileForPacketTweaker();
    }
}
