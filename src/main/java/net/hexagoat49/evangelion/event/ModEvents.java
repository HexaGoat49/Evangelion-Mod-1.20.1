package net.hexagoat49.evangelion.event;

import net.hexagoat49.evangelion.Evangelion;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Evangelion.MOD_ID)
    public static class ForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = Evangelion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        }
    }
}
