package xyz.nucleoid.packettweaker.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nucleoid.packettweaker.ContextProvidingPacketListener;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerPlayNetworkHandlerMixin implements ContextProvidingPacketListener {
    @Shadow
    public ServerPlayer player;

    @Shadow
    protected abstract GameProfile playerProfile();

    @Unique
    private ClientInformation clientSettings;

    @Override
    public @Nullable ServerPlayer getPlayerForPacketTweaker() {
        return this.player;
    }

    @Override
    public GameProfile getGameProfileForPacketTweaker() {
        return this.playerProfile();
    }

    @Override
    public ClientInformation getClientOptionsForPacketTweaker() {
        if (this.clientSettings == null) {
            this.clientSettings = this.player.clientInformation();
        }
        return this.clientSettings;
    }

    @Inject(method = "handleClientInformation", at = @At("TAIL"))
    private void clearCachedClientSettings(ServerboundClientInformationPacket clientSettingsC2SPacket, CallbackInfo ci) {
        this.clientSettings = null;
    }

}
