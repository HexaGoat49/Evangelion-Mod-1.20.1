package net.hexagoat49.evangelion.networking.packet;

import net.hexagoat49.evangelion.entity.custom.Eva01;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerJumpC2SPacket {
    public PlayerJumpC2SPacket() {}

    public PlayerJumpC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if(player.getVehicle() instanceof Eva01) {
                ((Eva01) player.getVehicle()).doJump();
            }
        });
        return true;
    }

}
