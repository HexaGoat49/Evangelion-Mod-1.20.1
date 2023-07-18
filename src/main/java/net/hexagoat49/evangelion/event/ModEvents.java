package net.hexagoat49.evangelion.event;

import net.hexagoat49.evangelion.Evangelion;
import net.hexagoat49.evangelion.networking.ModMessages;
import net.hexagoat49.evangelion.networking.packet.PlayerJumpC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Evangelion.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onPlayerJump(InputEvent event) {
            if(Minecraft.getInstance().options.keyJump.isDown()) {
                ModMessages.sendToServer(new PlayerJumpC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Evangelion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        }
    }
}
