package net.mrlucky841.mlplanets.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;

public class AsteroidChunkGenerator2 extends NoiseBasedChunkGenerator {
    public AsteroidChunkGenerator2(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settingsHolder) {
        super(biomeSource, settingsHolder);
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
}
