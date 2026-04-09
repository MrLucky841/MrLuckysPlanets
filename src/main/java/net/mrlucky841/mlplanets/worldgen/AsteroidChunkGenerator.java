package net.mrlucky841.mlplanets.worldgen;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.apache.commons.lang3.mutable.MutableObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//TODO: Write custom Chunk Generator!
public class AsteroidChunkGenerator extends ChunkGenerator {
    private final Holder<NoiseGeneratorSettings> settings;
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private final Supplier<Aquifer.FluidPicker> globalFluidPicker;

    public static final Codec<AsteroidChunkGenerator> CODEC = RecordCodecBuilder.create((chunkGenInstance) -> {
        return chunkGenInstance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((p_255584_) -> {
            return p_255584_.biomeSource;
        }), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((p_224278_) -> {
            return p_224278_.settings;
        })).apply(chunkGenInstance, chunkGenInstance.stable(AsteroidChunkGenerator::new));
    });

    public AsteroidChunkGenerator(BiomeSource p_256133_, Holder<NoiseGeneratorSettings> settings) {
        super(p_256133_);
        this.settings = settings;
        this.globalFluidPicker = Suppliers.memoize(() -> {
            return createFluidPicker(settings.value());
        });
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return codec();
    }

    @Override
    public void applyCarvers(WorldGenRegion p_223043_, long p_223044_, RandomState p_223045_, BiomeManager p_223046_, StructureManager p_223047_, ChunkAccess p_223048_, GenerationStep.Carving p_223049_) {

    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {
        if (!SharedConstants.debugVoidTerrain(chunkAccess.getPos())) {
            WorldGenerationContext worldgenerationcontext = new WorldGenerationContext(this, region);
            this.buildSurface(chunkAccess, worldgenerationcontext, randomState, structureManager, region.getBiomeManager(), region.registryAccess().registryOrThrow(Registries.BIOME), Blender.of(region));
        }
    }
    //this inner buildSurface is a slave of the top one. I wonder if that's so I can preferentially override the inner?
    @VisibleForTesting
    public void buildSurface(ChunkAccess chunkAccess, WorldGenerationContext generationContext, RandomState randomState, StructureManager structureManager, BiomeManager biomeManager, Registry<Biome> biomes, Blender blender) {
        NoiseChunk noisechunk = chunkAccess.getOrCreateNoiseChunk((chunkAccess1) -> {
            return this.createNoiseChunk(chunkAccess1, structureManager, blender, randomState);
        });
        NoiseGeneratorSettings noisegeneratorsettings = this.settings.value();
        randomState.surfaceSystem().buildSurface(randomState, biomeManager, biomes, noisegeneratorsettings.useLegacyRandomSource(), generationContext, chunkAccess, noisechunk, noisegeneratorsettings.surfaceRule());
    }

    //what does Beardifier do?
    private NoiseChunk createNoiseChunk(ChunkAccess chunkAccess, StructureManager structureManager, Blender blender, RandomState randomState) {
        return NoiseChunk.forChunk(chunkAccess, randomState, Beardifier.forStructuresInChunk(structureManager, chunkAccess.getPos()), this.settings.value(), this.globalFluidPicker.get(), blender);
    }

    private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings settings) {
        int lavaLevel = DimensionType.MIN_Y + 10; //ie, the last 10 blocks of liquid are lava
        Aquifer.FluidStatus aquifer$fluidstatus = new Aquifer.FluidStatus(lavaLevel, Blocks.LAVA.defaultBlockState());
        int seaLevel = settings.seaLevel();
        Aquifer.FluidStatus aquifer$fluidstatus1 = new Aquifer.FluidStatus(seaLevel, Blocks.ICE.defaultBlockState());
        return (x, y, z) -> {
            return y < Math.min(lavaLevel, seaLevel) ? aquifer$fluidstatus : aquifer$fluidstatus1;
        };
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion p_62167_) {

    }

    @Override
    public int getGenDepth() {
        return 0;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor p_223209_, Blender p_223210_, RandomState p_223211_, StructureManager p_223212_, ChunkAccess p_223213_) {
        return null;
    }

    @Override
    public int getSeaLevel() {
        return 64;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int p_223032_, int p_223033_, Heightmap.Types p_223034_, LevelHeightAccessor p_223035_, RandomState p_223036_) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor p_224213_, RandomState p_224214_) {
        MutableObject<NoiseColumn> mutableobject = new MutableObject<>();
        this.iterateNoiseColumn(p_224213_, p_224214_, x, z, mutableobject, (Predicate<BlockState>) null);
        return mutableobject.getValue();
    }
    protected OptionalInt iterateNoiseColumn(LevelHeightAccessor levelHeight, RandomState randomState, int x, int z, @Nullable MutableObject<NoiseColumn> noiseColumn, @Nullable Predicate<BlockState> blockState) {
        NoiseSettings noisesettings = this.settings.value().noiseSettings().clampToHeightAccessor(levelHeight);
        int i = noisesettings.getCellHeight();
        int j = noisesettings.minY();
        int k = Mth.floorDiv(j, i);
        int l = Mth.floorDiv(noisesettings.height(), i);
        if (l <= 0) {
            return OptionalInt.empty();
        } else {
            BlockState[] ablockstate;
            if (noiseColumn == null) {
                ablockstate = null;
            } else {
                ablockstate = new BlockState[noisesettings.height()];
                noiseColumn.setValue(new NoiseColumn(j, ablockstate));
            }

            int i1 = noisesettings.getCellWidth();
            int j1 = Math.floorDiv(x, i1);
            int k1 = Math.floorDiv(z, i1);
            int l1 = Math.floorMod(x, i1);
            int i2 = Math.floorMod(z, i1);
            int j2 = j1 * i1;
            int k2 = k1 * i1;
            double d0 = (double)l1 / (double)i1;
            double d1 = (double)i2 / (double)i1;
            NoiseChunk noisechunk = new NoiseChunk(1, randomState, j2, k2, noisesettings, DensityFunctions.BeardifierMarker.INSTANCE, this.settings.value(), this.globalFluidPicker.get(), Blender.empty());
            noisechunk.initializeForFirstCellX();
            noisechunk.advanceCellX(0);

            for(int l2 = l - 1; l2 >= 0; --l2) {
                noisechunk.selectCellYZ(l2, 0);

                for(int i3 = i - 1; i3 >= 0; --i3) {
                    int j3 = (k + l2) * i + i3;
                    double d2 = (double)i3 / (double)i;
                    noisechunk.updateForY(j3, d2);
                    noisechunk.updateForX(x, d0);
                    noisechunk.updateForZ(z, d1);
                    BlockState blockstate = noisechunk.getInterpolatedState();
                    BlockState blockstate1 = blockstate == null ? this.settings.value().defaultBlock() : blockstate;
                    if (ablockstate != null) {
                        int k3 = l2 * i + i3;
                        ablockstate[k3] = blockstate1;
                    }

                    if (blockState != null && blockState.test(blockstate1)) {
                        noisechunk.stopInterpolation();
                        return OptionalInt.of(j3 + 1);
                    }
                }
            }

            noisechunk.stopInterpolation();
            return OptionalInt.empty();
        }
    }

    @Override
    public void addDebugScreenInfo(List<String> p_223175_, RandomState p_223176_, BlockPos p_223177_) {

    }
}
