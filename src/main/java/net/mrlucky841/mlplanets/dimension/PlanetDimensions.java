package net.mrlucky841.mlplanets.dimension;

import net.minecraft.core.Holder;
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
    public static final ResourceKey<DensityFunction> CRATER_DENSITY_FUNCTION = ResourceKey.create(Registries.DENSITY_FUNCTION,
            new ResourceLocation(MLPlanets.MODID,"crater_density_function"));

    public static final ResourceKey<LevelStem> CERES_STEM = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(MLPlanets.MODID,"ceres"));

    public static final ResourceKey<Level> DIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(MLPlanets.MODID,"mrluckydim"));

    public static final ResourceKey<DimensionType> AIRLESS_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(MLPlanets.MODID,"airless_dim_type"));

    public static final ResourceKey<NoiseGeneratorSettings> CERES_NOISE_GEN = ResourceKey.create(Registries.NOISE_SETTINGS,
            new ResourceLocation(MLPlanets.MODID, "ceres_noise_gen"));



    //Specify the dimension types here
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(AIRLESS_DIM_TYPE, new DimensionType(
                OptionalLong.of(1), // fixedTime
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
                10.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
        //register other dim types here...
        //context.register(MARS_DIM_TYPE, new DimensionType( ...
    }

    public static NoiseGeneratorSettings makeNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        //params: minY, maxY, noiseSizeHorz, noiseSizeVert
        NoiseSettings noiseDims = NoiseSettings.create(0,256,2,2);
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        DensityFunction finalDensity = new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(CRATER_DENSITY_FUNCTION));
        return new NoiseGeneratorSettings(
            noiseDims,
            ModBlocks.CHONDRITE.get().defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            new NoiseRouter(
                    DensityFunctions.zero(),    //barrierNoise
                    DensityFunctions.zero(),    //fluidLevelFloodedNoise
                    DensityFunctions.zero(),    //fluidLevelSpreadNoise
                    DensityFunctions.zero(),    //lavaNoise
                    DensityFunctions.zero(),    //temperature
                    DensityFunctions.zero(),    //vegetation
                    DensityFunctions.zero(),    //continents
                    DensityFunctions.zero(),    //erosion
                    DensityFunctions.zero(),    //depth
                    DensityFunctions.zero(),    //ridges
                    DensityFunctions.zero(),    //initialDensityWithoutJaggedness
                    finalDensity,               //finalDensity
                    DensityFunctions.zero(),    //veinToggle
                    DensityFunctions.zero(),    //veinRidged
                    DensityFunctions.zero()     //veinGap
            ),
            //VV could also ues: SurfaceRules.sequence(put all sequences in here)
            ModSurfaceRules.makeRockRules(ModSurfaceRules.REGOLITH,ModSurfaceRules.CHONDRITE,ModSurfaceRules.VENUSIAN_OBSIDIAN),
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

    //called by .add(Registries.DENSITY_FUNCTION, PlanetDimensions::bootstrapDensityFunction) in the DataProvider
    public static void bootstrapDensityFunction(BootstapContext<DensityFunction> context) {
        //context.register(CERES_DENSITY_FUNCTION, new CeresDensityFunction(50,10));
        context.register(CRATER_DENSITY_FUNCTION, buildCeres(context));
        //context.register(GAS_GIANT_DENSITY_FUNCTION, buildGasGiant(context));
        //...
    }

    private static DensityFunction buildGasGiant(BootstapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noiseLookup = context.lookup(Registries.NOISE);
        Holder.Reference<NormalNoise.NoiseParameters> params = noiseLookup.getOrThrow(Noises.SURFACE);
        return DensityFunctions.add(
                DensityFunctions.noise(params),
                DensityFunctions.constant(-0.2));
    }

    private static DensityFunction buildCeres(BootstapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noiseLookup = context.lookup(Registries.NOISE);
        Holder.Reference<NormalNoise.NoiseParameters> bigContinentLookup = noiseLookup.getOrThrow(Noises.CONTINENTALNESS_LARGE);
        Holder.Reference<NormalNoise.NoiseParameters> continentLookup = noiseLookup.getOrThrow(Noises.CONTINENTALNESS);
        Holder.Reference<NormalNoise.NoiseParameters> erosionLookup = noiseLookup.getOrThrow(Noises.EROSION);
        Holder.Reference<NormalNoise.NoiseParameters> ridgeLookup = noiseLookup.getOrThrow(Noises.RIDGE);

        //flat noise for main ridges and plateaus
        //large basin craters
        //mild noise for bumps
        //pockmark craters

        //return new CraterDensityFunction(1); //Radius of bigg ceres craters is 70-100km

        return DensityFunctions.add(DensityFunctions.yClampedGradient(0,220,1,-4),
            DensityFunctions.add(
            DensityFunctions.add(
            DensityFunctions.add(
                DensityFunctions.mul(DensityFunctions.constant(0.22),
                    DensityFunctions.noise(continentLookup,0.12,0)),
                DensityFunctions.mul(DensityFunctions.constant(0.22),
                    DensityFunctions.noise(erosionLookup,0.18,0))),
                DensityFunctions.mul(DensityFunctions.constant(0.22),
                    DensityFunctions.noise(ridgeLookup,0.35,0))),
                DensityFunctions.mul(DensityFunctions.constant(0.5),
                        DensityFunctions.noise(bigContinentLookup,1,5)))
        );

        //return DensityFunctions.mul(
        //        new CeresDensityFunction(0.5),
        //        DensityFunctions.yClampedGradient(0,64,1,-1)
        //);
    }
    //0 is air, 1 is solid


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