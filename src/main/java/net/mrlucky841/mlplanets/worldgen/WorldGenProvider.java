package net.mrlucky841.mlplanets.worldgen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.dimension.PlanetDimensions;
import net.mrlucky841.mlplanets.worldgen.biome.SpaceBiomes;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, PlanetDimensions::bootstrapType)
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap)
            .add(Registries.BIOME, SpaceBiomes::bootstrap)
            .add(Registries.LEVEL_STEM, PlanetDimensions::bootstrapStem);

    public WorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MLPlanets.MODID));
    }
}
