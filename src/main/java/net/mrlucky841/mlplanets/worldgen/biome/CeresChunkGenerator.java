package net.mrlucky841.mlplanets.worldgen.biome;

import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import java.util.function.Supplier;

public class CeresChunkGenerator extends NoiseBasedChunkGenerator {
    private final Holder<NoiseGeneratorSettings> settings;
    private final Supplier<Aquifer.FluidPicker> globalFluidPicker;

    public CeresChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> noiseGeneratorSettingsHolder) {
        super(biomeSource, noiseGeneratorSettingsHolder);
        this.settings = noiseGeneratorSettingsHolder;
        this.globalFluidPicker = Suppliers.memoize(() -> createFluidPicker(noiseGeneratorSettingsHolder.value()));

    }

    @Override
    public void buildSurface(ChunkAccess chunkAccess, WorldGenerationContext generationContext, RandomState randomState, StructureManager structureManager, BiomeManager biomeManager, Registry<Biome> biomes, Blender blender) {
        NoiseChunk noisechunk = chunkAccess.getOrCreateNoiseChunk((lambdaAccess) -> {
            return NoiseChunk.forChunk(lambdaAccess, randomState, Beardifier.forStructuresInChunk(structureManager, lambdaAccess.getPos()), this.generatorSettings().value(), globalFluidPicker.get(), blender);
        });
        NoiseGeneratorSettings noisegeneratorsettings = this.generatorSettings().value();
        randomState.surfaceSystem().buildSurface(randomState, biomeManager, biomes, noisegeneratorsettings.useLegacyRandomSource(), generationContext, chunkAccess, noisechunk, noisegeneratorsettings.surfaceRule());
        //TODO: This lobotomy did not change issue. Figure out what this does
    }

    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long p_224225_, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess, GenerationStep.Carving carvingStep) {
        //no fiddling with carvers just yet
    }



    private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings settings) {
        Aquifer.FluidStatus lavaStatus = new Aquifer.FluidStatus(-54, Blocks.LAVA.defaultBlockState());
        int seaLevel = settings.seaLevel();
        Aquifer.FluidStatus defaultFluidStatus = new Aquifer.FluidStatus(seaLevel, settings.defaultFluid());
        Aquifer.FluidStatus airFluidStatus = new Aquifer.FluidStatus(DimensionType.MIN_Y * 2, Blocks.AIR.defaultBlockState());
        return (x, y, z) -> {
            return y < Math.min(-54, seaLevel) ? lavaStatus : defaultFluidStatus;
        };
    }

}
