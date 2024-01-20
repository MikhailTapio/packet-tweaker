package xyz.nucleoid.packettweaker.impl;

import net.minecraft.network.ClientConnection;

public interface ConnectionHolder {
    void packet_tweaker$setConnection(ClientConnection connection);
}
