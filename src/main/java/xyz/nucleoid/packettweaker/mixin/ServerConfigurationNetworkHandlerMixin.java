package xyz.nucleoid.packettweaker.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.nucleoid.packettweaker.ContextProvidingPacketListener;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(ServerConfigurationPacketListenerImpl.class)
public abstract class ServerConfigurationNetworkHandlerMixin implements ContextProvidingPacketListener {

    @Shadow
    protected abstract GameProfile playerProfile();

    @Shadow
    private ClientInformation clientInformation;

    @Override
    public GameProfile getGameProfileForPacketTweaker() {
        return this.playerProfile();
    }

    @Override
    public ClientInformation getClientOptionsForPacketTweaker() {
        return this.clientInformation;
    }
}
