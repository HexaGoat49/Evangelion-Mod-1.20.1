package net.hexagoat49.evangelion.datagen;

import net.hexagoat49.evangelion.Evangelion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    // ADD CONFIGURED AND PLACED FEATURES
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder();

    public ModWorldGenProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Evangelion.MOD_ID));
    }
}
