package net.hexagoat49.evangelion.entity;

import net.hexagoat49.evangelion.Evangelion;
import net.hexagoat49.evangelion.entity.custom.Eva01;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Evangelion.MOD_ID);

    public static final RegistryObject<EntityType<Eva01>> EVA_01 =
            ENTITY_TYPES.register("eva_01",
                    () -> EntityType.Builder.of(Eva01::new, MobCategory.MISC)
                            .sized(8f, 56.0f)
                            .build(new ResourceLocation(Evangelion.MOD_ID, "eva_01").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
