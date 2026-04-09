package net.mrlucky841.mlplanets.dimension;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.worldgen.biome.SpaceBiomes;

import java.util.List;
import java.util.OptionalLong;

public class PlanetDimensions {
    public static final ResourceKey<LevelStem> PHOBOS = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(MLPlanets.MODID,"phobos"));

    //public static final ResourceKey<Level> DIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
    //        new ResourceLocation(MLPlanets.MODID,"mrluckydim"));

    public static final ResourceKey<DimensionType> ASTEROID_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(MLPlanets.MODID,"asteroid_dim_type"));
    //public static final ResourceKey<DimensionType> COMPLEX_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
    //        new ResourceLocation(MLPlanets.MODID,"complex_type"));


    //Specify the dimension characteristics here
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(ASTEROID_DIM_TYPE, new DimensionType(
                OptionalLong.of(1200), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                0.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));

        //context.register(COMPLEX_DIM_TYPE, new DimensionType(
        //        OptionalLong.of(1200), // fixedTime
        //        false, // hasSkylight
        //        false, // hasCeiling
        //        false, // ultraWarm
        //        false, // natural
        //        1.0, // coordinateScale
        //        true, // bedWorks
        //        false, // respawnAnchorWorks
        //        0, // minY
        //        256, // height
        //        256, // logicalHeight
        //        BlockTags.INFINIBURN_OVERWORLD, // infiniburn
        //        BuiltinDimensionTypes.END_EFFECTS, // effectsLocation
        //        0.0f, // ambientLight
        //        new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    //actual generation
    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        //Only one biome for the whole dim
        NoiseBasedChunkGenerator phobosChunkGen = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(SpaceBiomes.PHOBOS)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.LARGE_BIOMES));

        //Mixed biome in the dim
        //NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
        //        MultiNoiseBiomeSource.createFromList(
        //                new Climate.ParameterList<>(List.of(Pair.of(
        //                                Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(SpaceBiomes.PHOBOS)),
        //                        Pair.of(
        //                                Climate.parameters(0.1F, 0.2F, 0.0F, 0.2F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(Biomes.BIRCH_FOREST)),
        //                        Pair.of(
        //                                Climate.parameters(0.3F, 0.6F, 0.1F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(Biomes.OCEAN)),
        //                        Pair.of(
        //                                Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(Biomes.DARK_FOREST))
        //                ))),
        //        noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD));

        //this is where we make new levelStems, to be used only in registering
        LevelStem asteroidStem = new LevelStem(dimTypes.getOrThrow(PlanetDimensions.ASTEROID_DIM_TYPE), phobosChunkGen); //<<change this last param to change gen type

        //this is where the biomes are registered
        context.register(PHOBOS, asteroidStem);
    }

    //TODO: make helper functions to automate chunk generators so I don't have to repeat that monstrosity over and over again


}
