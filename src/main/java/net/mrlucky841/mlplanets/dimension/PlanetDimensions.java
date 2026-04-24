package net.mrlucky841.mlplanets.dimension;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.block.ModBlocks;
import net.mrlucky841.mlplanets.worldgen.biome.CeresChunkGenerator;
import net.mrlucky841.mlplanets.worldgen.biome.SpaceBiomes;
import net.mrlucky841.mlplanets.worldgen.surface.ModSurfaceRules;

import java.util.List;
import java.util.OptionalLong;

public class PlanetDimensions {
    public static final ResourceKey<LevelStem> CERES_STEM = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(MLPlanets.MODID,"ceres"));

    public static final ResourceKey<Level> DIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(MLPlanets.MODID,"mrluckydim"));

    public static final ResourceKey<DimensionType> AIRLESS_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(MLPlanets.MODID,"airless_dim_type"));

    public static final ResourceKey<NoiseGeneratorSettings> CERES_NOISE_GEN = ResourceKey.create(Registries.NOISE_SETTINGS,
            new ResourceLocation(MLPlanets.MODID, "ceres_noise_gen"));

    public static final ResourceKey<DensityFunction> CERES_DENSITY_FUNCTION = ResourceKey.create(Registries.DENSITY_FUNCTION,
            new ResourceLocation(MLPlanets.MODID,"ceres_density_function"));

    //Specify the dimension types here
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(AIRLESS_DIM_TYPE, new DimensionType(
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
        //register other dim types here...
        //context.register(MARS_DIM_TYPE, new DimensionType( ...
    }

    public static NoiseGeneratorSettings makeNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        //params: minY, maxY, noiseSizeHorz, noiseSizeVert
        NoiseSettings noiseDims = NoiseSettings.create(-32,256,2,2);
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        DensityFunction finalDensity = //DensityFunctions.yClampedGradient(-32,64,1,-1);
            new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(CERES_DENSITY_FUNCTION));
        //VV where do I put this??
        return new NoiseGeneratorSettings(
            noiseDims,
            ModBlocks.CHONDRITE.get().defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            new NoiseRouter(
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),    //initialDensityWithoutJaggedness   //does this even matter?..
                    finalDensity,               //finalDensity
                    DensityFunctions.zero(),
                    DensityFunctions.zero(),
                    DensityFunctions.zero()
            ),
            //VV could also ues: SurfaceRules.sequence(put all sequences in here)
            ModSurfaceRules.makeRockRules(ModSurfaceRules.REGOLITH,ModSurfaceRules.CHONDRITE,ModSurfaceRules.CHONDRITE),
            List.of(), //list of biome climate parameterPoints "spawnTarget"
            0,
            true,
            false,
            true,
            false
        );
    }

    public static void bootstrapNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(CERES_NOISE_GEN, makeNoiseSettings(context));
        //...
    }

    public static void bootstrapDensityFunction(BootstapContext<DensityFunction> context) {
        context.register(CERES_DENSITY_FUNCTION, new CeresDensityFunction(10,50,1,1));
        //TODO, ^^^ why does MC give me an "unbounded values in registry" error?
        //...
    }

    //actual generation
    public static void bootstrapStem(BootstapContext<LevelStem> context) {

        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        //Template for only one biome for the whole dim:
        CeresChunkGenerator ceresChunkGen = new CeresChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(SpaceBiomes.CERES)),
                noiseGenSettings.getOrThrow(CERES_NOISE_GEN));

        //For multiple biomes, see what DeeperAndDarker does in their OthersideGeneration.java

        //this is where we make new levelStems, to be used only in registering
        LevelStem asteroidStem = new LevelStem(dimTypes.getOrThrow(PlanetDimensions.AIRLESS_DIM_TYPE), ceresChunkGen); //<<change this last param to change gen type

        //this is where the biomes are registered
        context.register(CERES_STEM, asteroidStem);
        //...
    }
}
